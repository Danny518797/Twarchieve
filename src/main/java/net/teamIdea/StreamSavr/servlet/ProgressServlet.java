package net.teamIdea.StreamSavr.servlet;

import net.teamIdea.StreamSavr.TwitterUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import twitter4j.*;

import static net.teamIdea.StreamSavr.TwitterUtils.setTwitter;

/**
 * Created by IntelliJ IDEA.
 * User: clemendr
 * Date: Nov 3, 2010
 * Time: 7:44:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProgressServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setAttribute("downloaded_tweets", TwitterUtils.getTweetsDownloaded(request));
            Twitter twitter = TwitterUtils.getTwitter(request);
            try {
                User twitterUser = twitter.verifyCredentials();
                request.setAttribute("total_tweets", twitterUser.getStatusesCount());//((TwitterUtils.getTwitter(request)).verifyCredentials()).getStatusesCount());
        } catch (TwitterException e) {
        }

        }

}
