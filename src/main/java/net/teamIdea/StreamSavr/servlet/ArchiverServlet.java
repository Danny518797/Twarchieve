package net.teamIdea.StreamSavr.servlet;

import net.teamIdea.StreamSavr.CreateCSV;
import net.teamIdea.StreamSavr.TweetGetter;
import twitter4j.Status;
import twitter4j.Twitter;

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
 
/*Description: This servlet does two things depending on if the URL parameter 'c' is set. If 'c' is not set, then the user's
 *              tweets are downloaded and stored to the session. If 'c' is set then those tweets are pulled out of the
 *              session and turned into a CSV and sent to the browser.
 */
public class ArchiverServlet extends HttpServlet {

    private TweetGetter tweetGet = null;
    private CreateCSV csv = null;

    /* Setters for testing purposes
    *  CSV and tweetget should both be unset unless the function is being tested.
    */
    public void setTweetGet(TweetGetter tweetGet) { this.tweetGet = tweetGet; }
    public void setCSV( CreateCSV csv) { this.csv = csv; }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* Get parameter 'c' off the request.
         * IF 'c' is blank, then that means the servlet needs to archive the tweets and store them to the session.
         * ELSE the servlet needs to get the tweets out of the session, create a CSV, and then send them to the browser.
         */
        String c = request.getParameter("c");
        if(c != null && "".equals(c)) {
            Twitter twitter = getTwitter(request);

            //List of tweets (each 'status' object contains a tweet).
            List<Status> tweets = null;
            try {
                //For testing purposes.
                if(tweetGet == null)
                    tweetGet = new TweetGetter();

                //Set error to 0 (no error). tweetGet will set a different code if something goes wrong.
                setTwitterError(request, 0);
                //Call tweetGet which passes back a list full of all the user's tweets.
                tweets = tweetGet.getAllTweets(twitter, request);
                //Store the list to the session. will be pulled out of the session and turned into a CSV later.
                request.getSession().setAttribute("TWEETS", tweets);
                //Once everything is done, send back an "okay" message to the browser.
                ServletOutputStream out = response.getOutputStream();
                out.write("okay".getBytes());
                out.flush();
                out.close();
            } catch ( IllegalStateException e) {
                //If an exception is thrown, the user probably isn't logged in. send them back to the auth screen
                response.sendRedirect("/auth");
            }
        } else {
            //for testing purposes
            if(csv == null)
                csv = new CreateCSV();

            //Get the list of tweets off the session. This was created and stored by the code above.
            List<Status> tweets = (List<Status>) request.getSession().getAttribute("TWEETS");

            //Pass tweets to createCSV which passes back a CSV file in byte array form.
            csv.createCSV(tweets);
            byte[] csvZipToSend = csv.zipCSV();

            //Set the response header so the browser will pop up a download dialog when it recieves the CSV.
            response.setHeader("Content-disposition",
                      "attachment; filename=" +
                      "tweets.zip" );
            response.setContentType("application/zip");
            ServletOutputStream out = response.getOutputStream();
            //send the csv file.
            out.write(csvZipToSend);
            out.flush();
            out.close();
        }
    }
}