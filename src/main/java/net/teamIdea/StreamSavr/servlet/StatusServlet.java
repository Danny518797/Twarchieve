package net.teamIdea.StreamSavr.servlet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.teamIdea.StreamSavr.TwitterUtils.getTwitter;
import static net.teamIdea.StreamSavr.TwitterUtils.setAccessToken;
import static net.teamIdea.StreamSavr.TwitterUtils.setTwitter;

/**
 * Created by IntelliJ IDEA.
 * User: clemendr
 * Date: Nov 13, 2010
 * Time: 1:30:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatusServlet extends HttpServlet {
    public static final String STATUS_VIEW = "/WEB-INF/jsp/status.jsp";
    public static final String HITS_REMAINING_ATTRIBUTE = "hitsRemaining";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        request.getRequestDispatcher(STATUS_VIEW).forward(request, response); //Parse and send JSP.
    }





}



