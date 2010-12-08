package net.teamIdea.StreamSavr.servlet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.teamIdea.StreamSavr.TwitterUtils.*;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 26, 2010
 * Time: 7:56:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallbackServlet extends HttpServlet {
    //Create a bunch of statics that will be used for setting variables in the session
    public static final String CALLBACK_VIEW = "/WEB-INF/jsp/callback.jsp";
    public static final String HITS_REMAINING_ATTRIBUTE = "hitsRemaining";
    public static final String USER_NAME = "userName";
    public static final String TOTAL_TWEETS = "totalTweets";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
        try {
             //Get Twitter from the session
            Twitter twitter = getTwitter(request);
            //Get the required verification parameters from the session and request
            RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
            String verifier = request.getParameter("oauth_verifier");
            //Get and set the access token
            AccessToken aToken = twitter.getOAuthAccessToken(requestToken, verifier);
            setAccessToken(request.getSession(), aToken);
            //Stores twitter to the session
            setTwitter(request, twitter);
            //Remove the request token just in case there already is one
            request.getSession().removeAttribute("requestToken");
            //Set several needed twitter attributes to the request
            request.setAttribute(HITS_REMAINING_ATTRIBUTE, twitter.getRateLimitStatus().getRemainingHits());
            request.setAttribute(USER_NAME, twitter.getScreenName());
            request.setAttribute(TOTAL_TWEETS, twitter.verifyCredentials().getStatusesCount());
        }
        //Catch any exceptions thrown
        catch (Exception e) {
            response.sendRedirect(AUTH_URI);
            //throw new ServletException(e);
        }
        request.getRequestDispatcher(CALLBACK_VIEW).forward(request, response); //Parse and send JSP.
    }

}
