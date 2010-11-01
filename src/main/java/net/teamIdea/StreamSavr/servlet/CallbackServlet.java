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

    public static final String CALLBACK_FORM_VIEW = "/WEB-INF/jsp/callback.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Twitter twitter = getTwitter(request); //Get the twitter object off the session.
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        try {
            AccessToken aToken = twitter.getOAuthAccessToken(requestToken, verifier);
            setAccessToken(request.getSession(), aToken);
            setTwitter(request, twitter);
            request.getSession().removeAttribute("requestToken");
        } catch (TwitterException e) {
            throw new ServletException(e);
        }

        request.getRequestDispatcher(CALLBACK_FORM_VIEW).forward(request, response); //Parse and send JSP.
    }

}
