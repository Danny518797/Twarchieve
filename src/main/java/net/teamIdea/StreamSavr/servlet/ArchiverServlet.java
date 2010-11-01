package net.teamIdea.StreamSavr.servlet;

import net.teamIdea.StreamSavr.TweetList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.teamIdea.StreamSavr.TwitterUtils.*;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 31, 2010
 * Time: 3:24:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArchiverServlet extends HttpServlet {

    public static final String CALLBACK_FORM_VIEW = "/WEB-INF/jsp/archiver.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Twitter twitter = getTwitter(request);

        TweetList test = getTweets(twitter);

        System.out.println(test.getTweet(0).getText());

        request.getRequestDispatcher(CALLBACK_FORM_VIEW).forward(request, response);


        /* Old code:
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        try {
            AccessToken aToken = twitter.getOAuthAccessToken(requestToken, verifier);
            System.out.println(twitter.getUserTimeline().get(1).getText());
            setAccessToken(request.getSession(), aToken);
            setTwitter(request, twitter);
            request.getSession().removeAttribute("requestToken");
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("/archiver");*/
    }

}
