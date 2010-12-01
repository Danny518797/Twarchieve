package net.teamIdea.StreamSavr.servlet;


import net.teamIdea.StreamSavr.TwitterUtils;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: clemendr
 * Date: Nov 29, 2010
 * Time: 5:39:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class ProgressServletTest {


    public void doGetShouldReturnCorrectTweetValues() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        //Mock the JSON stuff
        //JSONObject progressJSON = mock(JSONObject.class);
        JSONObject progressJSON = new JSONObject();


        when(request.getSession()).thenReturn(session);
        when(twitter.verifyCredentials()).thenReturn(user);
        when(user.getStatusesCount()).thenReturn(300);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(out);

        //Setup the JSON object that I am expecting to create
        JSONObject expected = new JSONObject();
        expected.put("currentProgess", 3000);
        expected.put("totalTweets", 0);

        //Make the new progress servlet
        ProgressServlet toTest = new ProgressServlet();
        //Setup the servlet and all the stuff it will need
        toTest.setJSON(progressJSON);
        toTest.setTwitter(twitter);
        TwitterUtils.setTweetsDownloaded(request, 300);
        toTest.doGet(request, response);
        //Make sure everything ran that should have
        verify(progressJSON).put("currentProgess", TwitterUtils.getTweetsDownloaded(request));
        verify(progressJSON).put("totalTweets", twitter.verifyCredentials().getStatusesCount());
        //This should have compared the values
        Assert.assertEquals(progressJSON.get("currentProgess"),expected.get("currentProgess"));
        verify(out).write(progressJSON.toString().getBytes());
        verify(out).flush();
        verify(out).close();

    }

    public void doGetShouldPassTwitterExceptionCases() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        TwitterException twitterException = mock(TwitterException.class);
        //Mock the JSON stuff
        JSONObject progressJSON = mock(JSONObject.class);

        when(request.getSession()).thenReturn(session);
        //Here is where you throw the exception
        when(twitter.verifyCredentials()).thenThrow(twitterException);
        //Return what you need to return
        when(user.getStatusesCount()).thenReturn(300);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(out);

        //Make the new progress servlet
        ProgressServlet toTest = new ProgressServlet();
        //Setup the servlet and all the stuff it will need
        toTest.setJSON(progressJSON);
        toTest.setTwitter(twitter);
        TwitterUtils.setTweetsDownloaded(request, 300);
        toTest.doGet(request, response);
        //Make sure it caught the exception that you wanted it to catch
        verify(twitterException).printStackTrace();
        verify(progressJSON).put("currentProgess", TwitterUtils.getTweetsDownloaded(request));
        //The line below will not run since twitter will throw an exception
        //verify(progressJSON).put("totalTweets", twitter.verifyCredentials().getStatusesCount());
        verify(out).write(progressJSON.toString().getBytes());
        verify(out).flush();
        verify(out).close();

    }
    public void doGetShouldPassJSONExceptionCases() throws Exception {
           //Setup everything that you are going to mock
           //Mock the HTTP stuff
           HttpServletRequest request = mock(HttpServletRequest.class);
           HttpServletResponse response = mock(HttpServletResponse.class);
           HttpSession session = mock(HttpSession.class);
           //Mock the Twitter stuff
           Twitter twitter = mock(Twitter.class);
           User user = mock(User.class);
           //Mock the JSON stuff
           JSONObject progressJSON = mock(JSONObject.class);
           JSONException jsonexception = mock(JSONException.class);


           when(request.getSession()).thenReturn(session);
           when(twitter.verifyCredentials()).thenReturn(user);
           //Throw the JSON exception when this line is hit but make everything else act the same
           when(progressJSON.put("totalTweets", twitter.verifyCredentials().getStatusesCount())).thenThrow(jsonexception);
           when(user.getStatusesCount()).thenReturn(300);
           ServletOutputStream out = mock(ServletOutputStream.class);
           when(response.getOutputStream()).thenReturn(out);

           //Make the new progress servlet
           ProgressServlet toTest = new ProgressServlet();
           //Setup the servlet and all the stuff it will need
           toTest.setJSON(progressJSON);
           toTest.setTwitter(twitter);
           TwitterUtils.setTweetsDownloaded(request, 300);
           toTest.doGet(request, response);

           //Verify that everything that was supposed to run did run
           verify(progressJSON).put("currentProgess", TwitterUtils.getTweetsDownloaded(request));
           verify(progressJSON).put("totalTweets", twitter.verifyCredentials().getStatusesCount());
           verify(jsonexception).printStackTrace();
           verify(out).write(progressJSON.toString().getBytes());
           verify(out).flush();
           verify(out).close();
       }
}
