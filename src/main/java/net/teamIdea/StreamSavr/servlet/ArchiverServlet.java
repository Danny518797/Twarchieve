package net.teamIdea.StreamSavr.servlet;

import net.teamIdea.StreamSavr.CreateCSV;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static net.teamIdea.StreamSavr.TwitterUtils.*;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 31, 2010
 * Time: 3:24:49 PM
 * To change this template use File | Settings | File Templates.
 */
 
public class ArchiverServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String c = request.getParameter("c");
        if(c != null && "".equals(c)) {
            Twitter twitter = getTwitter(request);

            List<Status> test = null;
            try {
                test = getAllTweets(twitter, request);
                request.getSession().setAttribute("TWEETS", test);
                ServletOutputStream out = response.getOutputStream();
                out.write("okay".getBytes());
                out.flush();
                out.close();
            } catch ( IllegalStateException e) {
                response.sendRedirect("/auth");
            }
        } else {
            CreateCSV csv = new CreateCSV();
            List<Status> test = (List<Status>) request.getSession().getAttribute("TWEETS");

            // null out the session at somepoint

            byte[] csvToSend = csv.createCSV(test);
            //System.out.println("Location: " + csv.getLocation() + " Filename: " + csv.getFilename());

            response.setHeader("Content-disposition",
                      "attachment; filename=" +
                      "tweets.csv" );
            response.setContentType("application/csv");
            ServletOutputStream out = response.getOutputStream();
            out.write(csvToSend);
            out.flush();
            out.close();
        }
        //request.getRequestDispatcher(ARCHIVER_VIEW).forward(request, response);
    }
}