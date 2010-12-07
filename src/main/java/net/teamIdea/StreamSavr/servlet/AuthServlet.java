package net.teamIdea.StreamSavr.servlet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.teamIdea.StreamSavr.TwitterUtils.getTwitter;
import static net.teamIdea.StreamSavr.TwitterUtils.newTwitter;
import static net.teamIdea.StreamSavr.TwitterUtils.setTwitter;

/*Description: This servlet gets the twitter URL that allows the user to authorize this application on his twitter
*              account, so auth.jsp can display it to the user.
*/
public class AuthServlet extends HttpServlet {
    public static final String AUTH_FORM_VIEW = "/WEB-INF/jsp/auth.jsp"; //view for this servlet
    public static final String AUTH_URL_ATTRIBUTE = "authUrl";           //Where the twit authURL is stored for the jsp.
    public static final String REQUEST_TOKEN_ATTRIBUTE = "requestToken";

    private Twitter twitter = null;

    //Setter: should only be called for testing purposes
    public void setTwitterLocal( Twitter twitter ){ this.twitter = twitter; }

    /* Description: */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //for testing purposes
        if( twitter == null )
            twitter = newTwitter();

        setTwitter(req, twitter); //Add the twitter object to the session.
        try {
            //Set the callback URL (ie where twitter sends you after you've authorized this app) to /callback
            StringBuffer callbackURL = req.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");

            //Get the authorization URL (ie the twitter URL where you authorize this app) and set it in the request
            //so the view can display the URL.
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
            req.getSession().setAttribute(REQUEST_TOKEN_ATTRIBUTE, requestToken);
            req.setAttribute(AUTH_URL_ATTRIBUTE, requestToken.getAuthorizationURL());
            req.getRequestDispatcher(AUTH_FORM_VIEW).forward(req, resp);

            //Set twitter back to null for when this page is loaded again
            twitter = null;

        } catch (TwitterException e) {
            throw new ServletException(e);
        }
    }


}
