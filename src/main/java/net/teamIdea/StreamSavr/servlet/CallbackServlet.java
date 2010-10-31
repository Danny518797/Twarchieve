package net.teamIdea.StreamSavr.servlet;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.teamIdea.StreamSavr.TwitterUtils.getTwitter;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 26, 2010
 * Time: 7:56:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallbackServlet extends HttpServlet {

    public static final String CALLBACK_FORM_VIEW = "/WEB-INF/jsp/callback.jsp";
    public static final String TIMELINE_ATTRIBUTE = "timeline";
    public static final String TIMELINE_VIEW = "/WEB-INF/jsp/timeline.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
