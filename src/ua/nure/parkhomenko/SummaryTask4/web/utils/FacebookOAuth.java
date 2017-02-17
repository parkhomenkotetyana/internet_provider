package ua.nure.parkhomenko.SummaryTask4.web.utils;

/**
 * Class for facebook OAuth.
 * 
 * @author Tetiana Parkhomenko
 *
 */ 
public class FacebookOAuth {

    private static final String API_KEY = "961653767268799";     
    private static final String SECRET = "4f2902232f828d62b32361ddf28bbb61";
    private static final String CLIENT_ID = "961653767268799";  

    private static final String REDIRECT_URL = "http://localhost:8080/SummaryTask4/controller?command=fboauth"; 

    public static String getAPIKey() {
        return API_KEY;
    }

    public static String getSecret() {
        return SECRET;
    }

    public static String getLoginRedirectURL() {
        return "https://graph.facebook.com/oauth/authorize?client_id=" + 
            CLIENT_ID + "&display=page&redirect_uri=" + 
            REDIRECT_URL+"&scope="+"email";
    }

    public static String getAuthURL(String authCode) {
        return "https://graph.facebook.com/oauth/access_token?client_id=" + 
            CLIENT_ID+"&redirect_uri=" + 
            REDIRECT_URL+"&client_secret="+SECRET+"&code="+authCode;
    }
}