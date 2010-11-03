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

    public static final String CALLBACK_VIEW = "/WEB-INF/jsp/callback.jsp";
    public static final String HITS_REMAINING_ATTRIBUTE = "hitsRemaining";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Twitter twitter = getTwitter(request); //Get the twitter object off the session.

        //checkLoggedIn(response, twitter);

        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");

        //Send to /auth if we're missing the requestToken or oauth_verifier.
        if (requestToken == null || verifier == null || twitter == null || verifier.isEmpty() ) {
            System.out.println("Now redirecting...");
            response.sendRedirect("/auth");
            return;
        }

        try {
            AccessToken aToken = twitter.getOAuthAccessToken(requestToken, verifier);
            setAccessToken(request.getSession(), aToken);
            setTwitter(request, twitter);
            request.getSession().removeAttribute("requestToken");
            request.setAttribute(HITS_REMAINING_ATTRIBUTE, twitter.getRateLimitStatus().getRemainingHits());
        } catch (TwitterException e) {
            throw new ServletException(e);
        }

        request.getRequestDispatcher(CALLBACK_VIEW).forward(request, response); //Parse and send JSP.
    }

}
