package net.teamIdea.StreamSavr.servlet;

import net.teamIdea.StreamSavr.TwitterUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import net.teamIdea.StreamSavr.tweetGetter;
import org.json.JSONException;
import org.json.JSONObject;
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

    private JSONObject progressJSON = null;
    private Twitter twitter = null;

    public void setJSON(JSONObject json) {this.progressJSON = json;}
    public void setTwitter(Twitter twit) {this.twitter = twit;}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(twitter == null){
            twitter = TwitterUtils.getTwitter(request);
        }

        if(progressJSON == null){
                    progressJSON = new JSONObject();
        }

        //Add the total number of tweets the user has to the JSON object
        //Then add the total number of tweets downloaded to the JSON object
        try {
            progressJSON.put("currentProgess", TwitterUtils.getTweetsDownloaded(request));
            progressJSON.put("totalTweets", twitter.verifyCredentials().getStatusesCount());
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //send the JSON object down the line.
        response.getOutputStream().write( progressJSON.toString().getBytes() );
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
