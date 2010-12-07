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

    @Test
    public void doGetShouldReturnCorrectTweetValuesLowerBound() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        //Mock the JSON stuff
        JSONObject progressJSON = new JSONObject();
        //Mock the Utilities
        TwitterUtils twitterUtils = mock(TwitterUtils.class);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("totalTweets")).thenReturn(0);
        when(twitterUtils.getTweetsDownloaded(request)).thenReturn(0);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(out);

        //Setup the JSON object that I am expecting to create
        JSONObject expected = new JSONObject();
        expected.put("currentProgess", 0);
        expected.put("totalTweets", 0);

        //Make the new progress servlet
        ProgressServlet toTest = new ProgressServlet();
        //Setup the servlet and all the stuff it will need
        toTest.setJSON(progressJSON);
        toTest.setTwitter(twitter);

        toTest.doGet(request, response);
        //Make sure everything ran that should have

        //This should have compared the values
        Assert.assertEquals(progressJSON.get("currentProgess"),expected.get("currentProgess"));
        Assert.assertEquals(progressJSON.get("totalTweets"),expected.get("totalTweets"));


    }
    @Test
    public void doGetShouldReturnCorrectTweetValuesUpperBound() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        //Mock the JSON stuff
        JSONObject progressJSON = new JSONObject();
        //Mock the Utilities
        TwitterUtils twitterUtils = mock(TwitterUtils.class);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("totalTweets")).thenReturn(3200);
        when(twitterUtils.getTweetsDownloaded(request)).thenReturn(3200);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(out);

        //Setup the JSON object that I am expecting to create
        JSONObject expected = new JSONObject();
        expected.put("currentProgess", 3200);
        expected.put("totalTweets", 3200);


        //Make the new progress servlet
        ProgressServlet toTest = new ProgressServlet();
        //Setup the servlet and all the stuff it will need
        toTest.setJSON(progressJSON);
        toTest.setTwitter(twitter);


        toTest.doGet(request, response);
        //Make sure everything ran that should have

        //This should have compared the values
        Assert.assertEquals(progressJSON.get("currentProgess"),expected.get("currentProgess"));
        Assert.assertEquals(progressJSON.get("totalTweets"),expected.get("totalTweets"));

    }
    @Test
    public void doGetShouldWriteToStreamCorrectly() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        //Mock the JSON stuff
        JSONObject progressJSON = new JSONObject();
        //Mock the Utilities
        TwitterUtils twitterUtils = mock(TwitterUtils.class);


        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("totalTweets")).thenReturn(300);
        when(twitterUtils.getTweetsDownloaded(request)).thenReturn(300);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(out);

        //Make the new progress servlet
        ProgressServlet toTest = new ProgressServlet();
        //Setup the servlet and all the stuff it will need
        toTest.setJSON(progressJSON);
        toTest.setTwitter(twitter);

        toTest.doGet(request, response);
        //Make sure everything ran that should have

        //Make sure it wrote to the output stream
        verify(out).write(progressJSON.toString().getBytes());
        verify(out).flush();
        verify(out).close();

    }
//    @Test
//    public void doGetShouldPassTwitterExceptionCases() throws Exception {
//        //Setup everything that you are going to mock
//        //Mock the HTTP stuff
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        HttpSession session = mock(HttpSession.class);
//        //Mock the Twitter stuff
//        Twitter twitter = mock(Twitter.class);
//        User user = mock(User.class);
//        TwitterException twitterException = mock(TwitterException.class);
//        //Mock the JSON stuff
//        JSONObject progressJSON = mock(JSONObject.class);
//
//
//        when(request.getSession()).thenReturn(session);
//        //Here is where you throw the exception
//        when(twitter.verifyCredentials()).thenThrow(twitterException);
//        //Return what you need to return
//        when(user.getStatusesCount()).thenReturn(300);
//        ServletOutputStream out = mock(ServletOutputStream.class);
//        when(response.getOutputStream()).thenReturn(out);
//
//        //Make the new progress servlet
//        ProgressServlet toTest = new ProgressServlet();
//        //Setup the servlet and all the stuff it will need
//        toTest.setJSON(progressJSON);
//        toTest.setTwitter(twitter);
//        TwitterUtils.setTweetsDownloaded(request, 300);
//        toTest.doGet(request, response);
//        //Make sure it caught the exception that you wanted it to catch
//        verify(twitterException).printStackTrace();
//
//    }
    
//    @Test
//    public void doGetShouldPassJSONExceptionCases() throws Exception {
//           //Setup everything that you are going to mock
//           //Mock the HTTP stuff
//           HttpServletRequest request = mock(HttpServletRequest.class);
//           HttpServletResponse response = mock(HttpServletResponse.class);
//           HttpSession session = mock(HttpSession.class);
//           //Mock the Twitter stuff
//           Twitter twitter = mock(Twitter.class);
//           User user = mock(User.class);
//           //Mock the JSON stuff
//           JSONObject progressJSON = new JSONObject();
//           JSONException jsonexception = mock(JSONException.class);
//           //Mock the utility stuff
//           TwitterUtils twitterUtils = new TwitterUtils();
//
//           when(request.getSession()).thenReturn(session);
//           when(twitter.verifyCredentials()).thenReturn(user);
//           //Throw the JSON exception when this line is hit but make everything else act the same
//           //when(user.getStatusesCount()).thenThrow(jsonexception);
//           //when(progressJSON.put("currentProgess", twitterUtils.getTweetsDownloaded(request))).thenThrow(jsonexception);
//           when(progressJSON.put("totalTweets", user.getStatusesCount())).thenThrow(jsonexception);
//           when(twitterUtils.getTweetsDownloaded(request)).thenReturn(300);
//           //when(user.getStatusesCount()).thenReturn(300);
//           ServletOutputStream out = mock(ServletOutputStream.class);
//           when(response.getOutputStream()).thenReturn(out);
//
//           //Make the new progress servlet
//           ProgressServlet toTest = new ProgressServlet();
//           //Setup the servlet and all the stuff it will need
//           toTest.setJSON(progressJSON);
//           toTest.setTwitter(twitter);
//           toTest.doGet(request, response);
//
//           //Verify that everything that was supposed to run did run
//           verify(jsonexception).printStackTrace();
//       }
}
