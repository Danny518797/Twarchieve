package net.teamIdea.StreamSavr.servlet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static net.teamIdea.StreamSavr.TwitterUtils.newTwitter;
import static net.teamIdea.StreamSavr.TwitterUtils.setAccessToken;
import static net.teamIdea.StreamSavr.TwitterUtils.setTwitter;

public class AuthServlet extends HttpServlet {
    public static final String AUTH_FORM_VIEW = "/WEB-INF/jsp/authForm.jsp";
    public static final String AUTH_RESULTS_VIEW = "/WEB-INF/jsp/authResults.jsp";
    public static final String AUTH_URL_ATTRIBUTE = "authUrl";
    public static final String REQUEST_TOKEN_ATTRIBUTE = "requestToken";
    public static final String PIN_PARAM = "pin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Twitter twitter = newTwitter();
        setTwitter(req, twitter); //Add the twitter object to the session.
        try {
            StringBuffer callbackURL = req.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");

            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
            req.getSession().setAttribute("requestToken", requestToken);
            req.setAttribute(AUTH_URL_ATTRIBUTE, requestToken.getAuthorizationURL());
            req.getRequestDispatcher(AUTH_FORM_VIEW).forward(req, resp);

        } catch (TwitterException e) {
            throw new ServletException(e);
        }
    }

    /*
        Twitter twitter = newTwitter();
        try {
            StringBuffer callbackURL = req.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");

            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
            req.getSession().setAttribute("requestToken", requestToken);
            resp.sendRedirect(requestToken.getAuthenticationURL());

            RequestToken requestToken = twitter.getOAuthRequestToken();
            req.getSession().setAttribute(REQUEST_TOKEN_ATTRIBUTE, requestToken);
            req.setAttribute(AUTH_URL_ATTRIBUTE, requestToken.getAuthorizationURL());
            req.getRequestDispatcher(AUTH_FORM_VIEW).forward(req, resp);
        } catch (TwitterException e) {
            resp.sendError(e.getStatusCode(), e.getMessage());
        }
    }
    */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Twitter twitter = newTwitter();
        try {
            HttpSession session = req.getSession();
            RequestToken requestToken = (RequestToken) session.getAttribute(REQUEST_TOKEN_ATTRIBUTE);
            session.removeAttribute(REQUEST_TOKEN_ATTRIBUTE);
            setAccessToken(session, twitter.getOAuthAccessToken(requestToken, req.getParameter(PIN_PARAM)));
            req.getRequestDispatcher(AUTH_RESULTS_VIEW).forward(req, resp);
        } catch (TwitterException e) {
            resp.sendError(e.getStatusCode(), e.getMessage());
        }
    }
}
