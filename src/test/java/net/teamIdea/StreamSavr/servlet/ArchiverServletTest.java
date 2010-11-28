package net.teamIdea.StreamSavr.servlet;


import net.teamIdea.StreamSavr.CreateCSV;
import net.teamIdea.StreamSavr.tweetGetter;
import org.testng.annotations.Test;
import twitter4j.Status;
import twitter4j.Twitter;

import static net.teamIdea.StreamSavr.TwitterUtils.getTwitter;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Nov 23, 2010
 * Time: 4:00:11 PM
 * To change this template use File | Settings | File Templates.
 */

@Test
public class ArchiverServletTest {

    public void doGetShouldArchiveTweetsAndSendOk() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("c")).thenReturn("");

        HttpSession session = mock(HttpSession.class);
        Twitter twitter = mock(Twitter.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("twitter")).thenReturn(twitter);
        

        ServletOutputStream out = mock(ServletOutputStream.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(out);

        tweetGetter tweetGet = mock(tweetGetter.class);
        List<Status> test = mock(ArrayList.class);
        when(tweetGet.getAllTweets(twitter, request)).thenReturn(test);


        ArchiverServlet toTest = new ArchiverServlet();
        toTest.setTweetGet(tweetGet);
        toTest.doGet(request, response);

        verify(tweetGet).getAllTweets(twitter, request);
        verify(request.getSession()).setAttribute("TWEETS",test);
        verify(out).write("okay".getBytes());
        verify(out).flush();
        verify(out).close();

    }

    public void doGetShouldCreateCsvThenSendCsv() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("c")).thenReturn(null);

        HttpSession session = mock(HttpSession.class);
        List<Status> tweetList = mock(List.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("TWEETS")).thenReturn(tweetList);

        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(out);

        CreateCSV csv = mock(CreateCSV.class);

        ArchiverServlet toTest = new ArchiverServlet();
        byte[] csvToSend = null;
        toTest.setCSV(csv);
        when(csv.createCSV(tweetList)).thenReturn(csvToSend);
        
        toTest.doGet(request, response);

        verify(csv).createCSV(tweetList);
        verify(response).setHeader("Content-disposition",
                      "attachment; filename=" +
                      "tweets.csv" );
        verify(response).setContentType("application/csv");
        verify(out).write(csvToSend);
        verify(out).flush();
        verify(out).close();
    }

}
