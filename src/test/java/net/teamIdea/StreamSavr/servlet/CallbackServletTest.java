package net.teamIdea.StreamSavr.servlet;

import net.teamIdea.StreamSavr.TwitterUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static net.teamIdea.StreamSavr.TwitterUtils.AUTH_URI;
import static net.teamIdea.StreamSavr.TwitterUtils.setAccessToken;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * Created by IntelliJ IDEA.
 * User: clemendr
 * Date: Dec 4, 2010
 * Time: 1:17:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class CallbackServletTest {
    //To test this guy we actually need to define alot of statics
    public static final String TWITTER_ATTRIBUTE = "twitter";
    public static final String CALLBACK_VIEW = "/WEB-INF/jsp/callback.jsp";
    public static final String HITS_REMAINING_ATTRIBUTE = "hitsRemaining";
    public static final String USER_NAME = "userName";
    public static final String TOTAL_TWEETS = "totalTweets";
    public static final String ACCESS_TOKEN_ATTRIBUTE = "accessToken";

    @Test
    public void doGetShouldFlowProperly() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestdispatcher = mock(RequestDispatcher.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        RateLimitStatus ratelimitstatus = mock(RateLimitStatus.class);
        AccessToken accesstoken = mock(AccessToken.class);
        //Mock the request token stuff by making a fake one
        RequestToken requestToken = new RequestToken("key", "token");


        //Deal with session stuff
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(TWITTER_ATTRIBUTE)).thenReturn(twitter);
        when(session.getAttribute("requestToken")).thenReturn(requestToken);
        when(request.getParameter("oauth_verifier")).thenReturn("fake_oauth_identifier");
        //Deal with twitter requests
        when(twitter.getRateLimitStatus()).thenReturn(ratelimitstatus);
        when(twitter.verifyCredentials()).thenReturn(user);
        when(twitter.getOAuthAccessToken(requestToken, "fake_oauth_identifier")).thenReturn(accesstoken);
        when(ratelimitstatus.getRemainingHits()).thenReturn(300);
        when(user.getStatusesCount()).thenReturn(300);
        //Deal with forwarding to another jsp
        when(request.getRequestDispatcher(CALLBACK_VIEW)).thenReturn(requestdispatcher);

        //Make the new callback servlet
        CallbackServlet toTest = new CallbackServlet();
        //Call the doGet method on the new callback servlet
        toTest.doGet(request, response);
        //Verify that what should have run did run
        verify(session).setAttribute(ACCESS_TOKEN_ATTRIBUTE, accesstoken);
        verify(session).setAttribute(TWITTER_ATTRIBUTE, twitter);
        verify(session).removeAttribute("requestToken");
        verify(request).setAttribute(HITS_REMAINING_ATTRIBUTE, twitter.getRateLimitStatus().getRemainingHits());
        verify(request).setAttribute(USER_NAME, twitter.getScreenName());
        verify(request).setAttribute(TOTAL_TWEETS, twitter.verifyCredentials().getStatusesCount());

    }
    @Test
    public void doGetShouldHandleException() throws Exception {
        //Setup everything that you are going to mock
        //Mock the HTTP stuff
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestdispatcher = mock(RequestDispatcher.class);
        //Mock the Twitter stuff
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        RateLimitStatus ratelimitstatus = mock(RateLimitStatus.class);
        TwitterException twitterException = mock(TwitterException.class);
        //Mock the request token stuff by making a fake one
        RequestToken requestToken = new RequestToken("key", "token");

        //Deal with session stuff
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(TWITTER_ATTRIBUTE)).thenReturn(twitter);
        when(session.getAttribute("requestToken")).thenReturn(requestToken);
        //Deal with twitter requests
        when(twitter.getRateLimitStatus()).thenReturn(ratelimitstatus);
        when(twitter.verifyCredentials()).thenThrow(twitterException);
        when(ratelimitstatus.getRemainingHits()).thenReturn(300);
        when(user.getStatusesCount()).thenReturn(300);
        //Deal with forwarding to another jsp
        when(request.getRequestDispatcher(CALLBACK_VIEW)).thenReturn(requestdispatcher);

        //Make the new callback servlet
        CallbackServlet toTest = new CallbackServlet();
        //Call the doGet method on the new callback servlet
        toTest.doGet(request, response);
        //Verify that the exception was caught
        verify(response).sendRedirect(AUTH_URI);

    }
}
