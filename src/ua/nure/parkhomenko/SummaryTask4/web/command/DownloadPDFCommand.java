package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.ServicesTariffs;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.exception.DBException;

/**
 * Dowload PDF command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class DownloadPDFCommand extends Command {

	private static final long serialVersionUID = 3050611808652711525L;

	private static final Logger LOG = Logger.getLogger(DownloadPDFCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, AppException {
		LOG.debug("Command starts");

		try {
			makePDF();
		} catch (IOException | DocumentException e) {
			LOG.error("Cannot make the file.");
		}

		LOG.info("File was made.");

		String filename = "Tariffs.pdf";
		String filePath = "D:\\EclipseWorkspace\\SummaryTask4\\WebContent\\" + filename;

		response.setHeader("Content-Disposition", "attachment; filename=" + filename);

		byte[] buffer = new byte[4096];

		try (ServletOutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(new File(filePath))) {
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		} catch (IOException ioe) {
			LOG.error("Cannot find the file.");
		}

		LOG.info("File is ready for downloading.");

		LOG.debug("Command finished");
		return null;
	}

	/**
	 * Method for making a PDF file.
	 * 
	 * @throws DocumentException
	 * @throws DBException
	 * @throws IOException
	 */
	public void makePDF() throws DocumentException, DBException, IOException {

		List<ServicesTariffs> servicesTariffs = DBManager.getInstance().findServicesAndTariffs();

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		PdfWriter.getInstance(document,
				new FileOutputStream(new File("D:\\EclipseWorkspace\\SummaryTask4\\WebContent\\Tariffs.pdf")));

		document.open();

		BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\ARIAL.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf);

		Paragraph title = new Paragraph("Tariffs&Services", font);
		document.add(title);

		Chapter chapter = new Chapter(title, 1);
		chapter.setNumberDepth(0);

		Section section = chapter.addSection(title);

		PdfPTable t = new PdfPTable(5);

		t.setSpacingBefore(25);
		t.setSpacingAfter(25);

		PdfPCell c1 = new PdfPCell(new Phrase("№", font));
		t.addCell(c1);
		PdfPCell c2 = new PdfPCell(new Phrase("Tariff"));
		t.addCell(c2);
		PdfPCell c3 = new PdfPCell(new Phrase("Service"));
		t.addCell(c3);
		PdfPCell c4 = new PdfPCell(new Phrase("Price"));
		t.addCell(c4);
		PdfPCell c5 = new PdfPCell(new Phrase("Description"));
		t.addCell(c5);

		for (int i = 0; i < servicesTariffs.size(); i++) {
			t.addCell(i + 1 + "");
			t.addCell(new PdfPCell(new Phrase(servicesTariffs.get(i).getTariff().getName(), font)));
			t.addCell(new PdfPCell(new Phrase(servicesTariffs.get(i).getService().getName(), font)));
			t.addCell(new PdfPCell(new Phrase(servicesTariffs.get(i).getPrice() + "", font)));
			t.addCell(new PdfPCell(new Phrase(servicesTariffs.get(i).getDescription(), font)));
		}
		section.add(t);

		document.add(section);

		document.close();
	}
}
