package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.visural.common.IOUtil;
import com.visural.common.StringUtil;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.exception.DBException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.FacebookOAuth;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.Validator;

/**
 * OAuth command.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class OAuthCommand extends Command {

	private static final long serialVersionUID = -5049344826635625478L;

	private static final Logger LOG = Logger.getLogger(OAuthCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String code = request.getParameter("code");
		LOG.trace("Request parameter: code --> " + code);

		String forward = null;
		if (StringUtil.isNotBlankStr(code)) {
			String authURL = FacebookOAuth.getAuthURL(code);
			URL url = new URL(authURL);
			try {
				String result = readURL(url);
				String accessToken = null;
				Integer expires = null;

				String[] pairs = result.split("&");
				for (String pair : pairs) {
					String[] kv = pair.split("=");
					if (kv.length != 2) {
						LOG.error("RuntimeException: Unexpected auth response");
						throw new RuntimeException("Unexpected auth response");
					} else {
						if (kv[0].equals("access_token")) {
							accessToken = kv[1];
						}
						if (kv[0].equals("expires")) {
							expires = Integer.valueOf(kv[1]);
						}
					}
				}
				if (accessToken != null && expires != null) {
					forward = authFacebookLogin(accessToken, expires, request, response);
				} else {
					LOG.error("RuntimeException: Access token and expires not found");
					throw new RuntimeException("Access token and expires not found");
				}
			} catch (IOException e) {
				LOG.error("RuntimeException:" + e);
				throw new RuntimeException(e);
			}
		}
		LOG.debug("Command finished");
		return forward;
	}

	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		return new String(baos.toByteArray());
	}

	private String authFacebookLogin(String accessToken, int expires, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject resp = new JSONObject(IOUtil.urlToString(
					new URL("https://graph.facebook.com/me?access_token=" + accessToken + "&fields=email")));

			String email = null;
			try {
				email = resp.getString("email");
				LOG.trace("Get email from JSONObject: email --> " + email);
			} catch (JSONException je) {
				request.setAttribute("error",
						"You can't log in using Facebook (maybe you registered in Facebook with phone number)");
				LOG.error("No email");
				return Path.PAGE_LOGIN;
			}

			if (Validator.validateEmail(email)) {
				User user = DBManager.getInstance().findUserByLogin(email);
				LOG.trace("Found in DB: user --> " + user);

				if (user != null) {
					request.setAttribute("login", email);
					LOG.trace("Set the request attribute: login --> " + email);
					request.setAttribute("password", user.getPassword());
					LOG.trace("Set the request attribute: password --> " + user.getPassword());
					request.setAttribute("loginWithFB", "true");
					LOG.trace("Set the request attribute: loginWithFB --> " + "true");
					return Path.COMMAND_LOGIN;
				} else {
					request.setAttribute("error", "Your account is not connected with Facebook (different emails).");
					LOG.error("User's account is not connected with Facebook.");
					return Path.PAGE_LOGIN;
				}
			}

		} catch (JSONException | IOException | DBException ex) {
			LOG.error("Error while login" + ex);
			throw new RuntimeException("failed login", ex);
		}
		return Path.PAGE_LOGIN;
	}
}