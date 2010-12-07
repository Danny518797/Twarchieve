package net.teamIdea.StreamSavr;

import org.testng.Assert;
import org.testng.annotations.Test;
import twitter4j.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Dec 5, 2010
 * Time: 12:32:38 PM
 * To change this template use File | Settings | File Templates.
 */

@Test
public class TweetGetterTest {
    public static final String DOWNLOADED_TWEETS = "downloaded";
    public static final String TWITTER_ERROR = "twitError";

    public void getAllTweetsShouldQuitAfter2ndTryReturnsNoTweets() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        List<Status> toArchive = mock(ArrayList.class);
        ResponseList<Status> tweets = mock(ResponseList.class);
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);
        TwitterException e = mock(TwitterException.class);

        when(twitter.getUserTimeline(new Paging(1,200))).thenReturn(tweets);
        when(twitter.getUserTimeline(new Paging(2,200))).thenReturn(tweets);
        when(tweets.isEmpty()).thenReturn(false, true); //Pretend no tweets are returned.
        when(tweets.size()).thenReturn(200,0); //Tweets contains 200 elements
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1); //Hits always remaining
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(DOWNLOADED_TWEETS)).thenReturn(0); //Always say 200 tweets have been retrieved.

        TweetGetter toTest = new TweetGetter();
        toTest.setToArchive(toArchive);
        toTest.getAllTweets(twitter, request);


        verify(toArchive, times(1)).addAll(tweets); //Make sure it's called only 16 (and not 200).
    }

    public void getAllTweetsShouldQuitIfNoTweetsAreReturned() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        List<Status> toArchive = mock(ArrayList.class);
        ResponseList<Status> tweets = mock(ResponseList.class);
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);
        TwitterException e = mock(TwitterException.class);

        when(twitter.getUserTimeline(new Paging(1,200))).thenReturn(tweets);
        when(tweets.isEmpty()).thenReturn(true); //Pretend no tweets are returned.
        when(tweets.size()).thenReturn(0); //Tweets contains 200 elements
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1); //Hits always remaining
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(DOWNLOADED_TWEETS)).thenReturn(0); //Always say 200 tweets have been retrieved.

        TweetGetter toTest = new TweetGetter();
        toTest.setToArchive(toArchive);
        toTest.getAllTweets(twitter, request);


        verify(toArchive, times(0)).addAll(tweets); //Make sure it's called only 16 (and not 200).
    }

    public void getAllTweetsShouldDownloadNoMoreThan16Pages() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        List<Status> toArchive = mock(ArrayList.class);
        ResponseList<Status> tweets = mock(ResponseList.class);
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);
        TwitterException e = mock(TwitterException.class);

        //Allow getTimeline to be called up to 200 times. Hopefully it will be called only 16 times.
        for(int i = 1; i < 201;)
            when(twitter.getUserTimeline(new Paging(i++,200))).thenReturn(tweets);
        when(tweets.isEmpty()).thenReturn(false); //Pretend tweets contains something
        when(tweets.size()).thenReturn(200); //Tweets contains 200 elements
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1); //Hits always remaining
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(DOWNLOADED_TWEETS)).thenReturn(200); //Always say 200 tweets have been retrieved.

        TweetGetter toTest = new TweetGetter();
        toTest.setToArchive(toArchive);
        toTest.getAllTweets(twitter, request);


        verify(toArchive, times(16)).addAll(tweets); //Make sure it's called only 16 (and not 200).
    }

    public void getAllTweetsShouldThrowExceptionThenQuitAfterNoHitsRemaining() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        List<Status> toArchive = mock(ArrayList.class);
        ResponseList<Status> tweets = mock(ResponseList.class);
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);
        TwitterException e = mock(TwitterException.class);

        //First act normal, then throw exception
        when(twitter.getUserTimeline(new Paging(1,200))).thenReturn(tweets);
        when(twitter.getUserTimeline(new Paging(2,200))).thenThrow(e);
        when(tweets.isEmpty()).thenReturn(false); //Pretend tweets contains something, then nothing.
        when(tweets.size()).thenReturn(200, 0); //Tweets contains 200 elements, then nothing.
        when(e.getStatusCode()).thenReturn(502); //throw 502 error.
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(2,1,0); //No hits remaining. should quit.
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(DOWNLOADED_TWEETS)).thenReturn(0,200);

        TweetGetter toTest = new TweetGetter();
        toTest.setToArchive(toArchive);
        toTest.getAllTweets(twitter, request);


        verify(toArchive, times(1)).addAll(tweets);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 200); //Should download 200 tweets
        verify(session).setAttribute(TWITTER_ERROR, 502); //Then record error to session, then quit.
    }

    public void getAllTweetsShouldSetErrorCodeToNegativeOneInTheSessionThenQuitIfZeroHitsRemain() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        ResponseList<Status> tweets = mock(ResponseList.class);
        List<Status> toArchive;
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);

        when(twitter.getUserTimeline(new Paging(1, 200))).thenReturn(tweets);
        when(request.getSession()).thenReturn(session);
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(0);
        when(tweets.isEmpty()).thenReturn(true);

        TweetGetter toTest = new TweetGetter();
        toArchive = toTest.getAllTweets(twitter, request);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 0);
        verify(session, times(1)).setAttribute(TWITTER_ERROR, -1);
        Assert.assertTrue(toArchive.isEmpty());
    }

    public void getAllTweetsShouldQuitOnError499ThenSet499ErrorInSessionThenQuit() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TwitterException e = mock(TwitterException.class);
        List<Status> toArchive;
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);

        when(twitter.getUserTimeline(new Paging(1, 200))).thenThrow(e);
        when(e.getStatusCode()).thenReturn(499);
        when(request.getSession()).thenReturn(session);
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1);

        TweetGetter toTest = new TweetGetter();
        toArchive = toTest.getAllTweets(twitter, request);
        verify(session, times(1)).setAttribute(TWITTER_ERROR, 499);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 0);
        Assert.assertTrue(toArchive.isEmpty());
    }

    /*
     * Desc: The 4 tests below all make sure that error codes 502,503,401,and 500 cause getTweets to retry exactly 4 times.
     */
    public void getAllTweetsShouldRetryFourTimesSet502ErrorInSessionThenQuit() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TwitterException e = mock(TwitterException.class);
        List<Status> toArchive;
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);

        when(twitter.getUserTimeline(new Paging(1, 200))).thenThrow(e);
        when(e.getStatusCode()).thenReturn(502);
        when(request.getSession()).thenReturn(session);
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1);

        TweetGetter toTest = new TweetGetter();
        toArchive = toTest.getAllTweets(twitter, request);
        verify(session, times(4)).setAttribute(TWITTER_ERROR, 502);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 0);
        Assert.assertTrue(toArchive.isEmpty());
    }

    public void getAllTweetsShouldRetryFourTimesSet503ErrorInSessionThenQuit() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TwitterException e = mock(TwitterException.class);
        List<Status> toArchive;
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);

        when(twitter.getUserTimeline(new Paging(1, 200))).thenThrow(e);
        when(e.getStatusCode()).thenReturn(503);
        when(request.getSession()).thenReturn(session);
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1);

        TweetGetter toTest = new TweetGetter();
        toArchive = toTest.getAllTweets(twitter, request);
        verify(session, times(4)).setAttribute(TWITTER_ERROR, 503);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 0);
        Assert.assertTrue(toArchive.isEmpty());
    }

    public void getAllTweetsShouldRetryFourTimesSet401ErrorInSessionThenQuit() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TwitterException e = mock(TwitterException.class);
        List<Status> toArchive;
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);

        when(twitter.getUserTimeline(new Paging(1, 200))).thenThrow(e);
        when(e.getStatusCode()).thenReturn(401);
        when(request.getSession()).thenReturn(session);
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1);

        TweetGetter toTest = new TweetGetter();
        toArchive = toTest.getAllTweets(twitter, request);
        verify(session, times(4)).setAttribute(TWITTER_ERROR, 401);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 0);
        Assert.assertTrue(toArchive.isEmpty());
    }

    public void getAllTweetsShouldRetryFourTimesSet500ErrorInSessionThenQuit() throws Exception {
        Twitter twitter = mock(Twitter.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TwitterException e = mock(TwitterException.class);
        List<Status> toArchive;
        RateLimitStatus rateLimit = mock(RateLimitStatus.class);

        when(twitter.getUserTimeline(new Paging(1, 200))).thenThrow(e);
        when(e.getStatusCode()).thenReturn(500);
        when(request.getSession()).thenReturn(session);
        when(twitter.getRateLimitStatus()).thenReturn(rateLimit);
        when(rateLimit.getRemainingHits()).thenReturn(1);

        TweetGetter toTest = new TweetGetter();
        toArchive = toTest.getAllTweets(twitter, request);
        verify(session, times(4)).setAttribute(TWITTER_ERROR, 500);
        verify(session).setAttribute(DOWNLOADED_TWEETS, 0);
        Assert.assertTrue(toArchive.isEmpty());
    }
}
