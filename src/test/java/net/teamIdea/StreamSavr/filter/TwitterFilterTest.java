package net.teamIdea.StreamSavr.filter;

import org.testng.annotations.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.http.AccessToken;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Dec 4, 2010
 * Time: 11:55:28 AM
 * To change this template use File | Settings | File Templates.
 */

@Test
public class TwitterFilterTest {

    public static final String TOKEN_PROPERTY = "token";
    public static final String TOKEN_SECRET_PROPERTY = "tokenSecret";
    private static final String CONSUMER_KEY = "S1KhK1GX3KFneNEWd3AClA";
    private static final String CONSUMER_SECRET = "G0dLbBSM3zaS6CpBkZlicqRzD1PE1UiV89GsBBl784";
    public static final String ACCESS_TOKEN_ATTRIBUTE = "accessToken";
    public static final String TWITTER_ATTRIBUTE = "twitter";
    public static final String USER_ATTRIBUTE = "user";
    public static final String AUTH_URI = "auth";
    public static final String DOWNLOADED_TWEETS = "downloaded";


    public void shouldThowExceptionAndRedirectToAuth() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);
        AccessToken accessToken = mock(AccessToken.class);
        Twitter twitter = mock(Twitter.class);
        TwitterException e = mock(TwitterException.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(ACCESS_TOKEN_ATTRIBUTE)).thenReturn(accessToken);
        when(twitter.verifyCredentials()).thenThrow(e);


        TwitterFilter toTest = new TwitterFilter();
        toTest.setTwitterLocal(twitter);
        toTest.doFilter(request, response, chain);

        verify(response).sendRedirect(AUTH_URI);
    }

    public void shouldDoFilter() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);
        AccessToken accessToken = mock(AccessToken.class);
        Twitter twitter = mock(Twitter.class);
        TwitterException e = mock(TwitterException.class);
        User twitterUser = mock(User.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(ACCESS_TOKEN_ATTRIBUTE)).thenReturn(accessToken);
        when(twitter.verifyCredentials()).thenReturn(twitterUser);


        TwitterFilter toTest = new TwitterFilter();
        toTest.setTwitterLocal(twitter);
        toTest.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
