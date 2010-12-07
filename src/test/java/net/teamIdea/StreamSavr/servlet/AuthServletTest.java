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
import twitter4j.http.RequestToken;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class AuthServletTest {

    public static final String TWITTER_ATTRIBUTE = "twitter";
    public static final String AUTH_FORM_VIEW = "/WEB-INF/jsp/auth.jsp";

    public void doGetShouldSetTheCallbackAndSaveTheTwitterObjectAndConstructTheCallbackURLCorrectly() throws Exception {
        // mock http
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        // mock twitter
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        RequestToken requestToken = new RequestToken("lol", "lawlerskates");

        when(request.getSession()).thenReturn(session);
        StringBuffer callback = new StringBuffer("http://localhost:8080/auth");
        when(request.getRequestURL()).thenReturn(callback);
        when(twitter.getOAuthRequestToken("http://localhost:8080/callback")).thenReturn(requestToken);
        when(request.getRequestDispatcher(AUTH_FORM_VIEW)).thenReturn(requestDispatcher);


        AuthServlet toTest = new AuthServlet();
        toTest.setTwitterLocal(twitter);
        toTest.doGet(request, response);

        verify(session).setAttribute(TWITTER_ATTRIBUTE, twitter); //verify the twit object is saved.
        verify(twitter).getOAuthRequestToken("http://localhost:8080/callback"); //verify the callback gets sets correctly
        verify(requestDispatcher).forward(request, response); //verify that we're forwarded.
        
    }
}