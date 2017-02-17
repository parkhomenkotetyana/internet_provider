package ua.nure.parkhomenko.SummaryTask4;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Custom tag. Displays current date and time.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class DatetimeTag extends SimpleTagSupport {
	
	private static final String DATE_FORMAT_RU = "dd.MM.yyyy    HH:mm";
	private static final String DATE_FORMAT_EN = "MM/dd/yyyy    HH:mm";
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

	     Object lang = getJspContext().getAttribute("language", PageContext.SESSION_SCOPE);	     
    
	     if ("en".equals(lang)) {
	    	 out.print(new SimpleDateFormat(DATE_FORMAT_EN).format(Calendar.getInstance().getTime()));
	     } else {
	    	 out.print(new SimpleDateFormat(DATE_FORMAT_RU).format(Calendar.getInstance().getTime()));
	     }
	}
}