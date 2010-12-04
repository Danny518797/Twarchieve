package net.teamIdea.StreamSavr;

import twitter4j.*;
import twitter4j.http.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Description: this class contains some static methods that are used often. Mostly they are methods that allow you to
 * store important objects to the session.
 */
public class TwitterUtils {
    private static final String CONSUMER_KEY = "S1KhK1GX3KFneNEWd3AClA";
    private static final String CONSUMER_SECRET = "G0dLbBSM3zaS6CpBkZlicqRzD1PE1UiV89GsBBl784";
    public static final String ACCESS_TOKEN_ATTRIBUTE = "accessToken";
    public static final String TWITTER_ATTRIBUTE = "twitter";
    public static final String USER_ATTRIBUTE = "user";
    public static final String AUTH_URI = "auth";
    public static final String DOWNLOADED_TWEETS = "downloaded";



    //Desc: Get a new twitter object based on the consumer key and secret.
    public static Twitter newTwitter() {
        return new TwitterFactory().getOAuthAuthorizedInstance(CONSUMER_KEY, CONSUMER_SECRET);
    }

    //Desc: get a new twitter based on the access token
    public static Twitter newTwitter(AccessToken accessToken) {
        return new TwitterFactory().getOAuthAuthorizedInstance(CONSUMER_KEY, CONSUMER_SECRET, accessToken);
    }

    //Desc: Get the access token off the session.
    public static AccessToken getAccessToken(HttpSession session) {
        return (AccessToken) session.getAttribute(ACCESS_TOKEN_ATTRIBUTE);
    }

    //Desc: Set the acccess token on the session.
    public static void setAccessToken(HttpSession session, AccessToken accessToken) {
        session.setAttribute(ACCESS_TOKEN_ATTRIBUTE, accessToken);
    }

    //Desc: get the twitter object off the session.
    public static Twitter getTwitter(HttpServletRequest request) {
        return (Twitter) request.getSession().getAttribute(TWITTER_ATTRIBUTE);
    }

    //Desc: Set the twitter on the session.
    public static void setTwitter(HttpServletRequest request, Twitter twitter) {
        request.getSession().setAttribute(TWITTER_ATTRIBUTE, twitter);
    }

    //Desc: Set the number of tweets downloaded on the session.
    public static void setTweetsDownloaded(HttpServletRequest request, Integer downloaded) {
        request.getSession().setAttribute(DOWNLOADED_TWEETS, downloaded);
    }

    //Desc: Get the number of tweets downloaded off the session.
    public static Integer getTweetsDownloaded(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute(DOWNLOADED_TWEETS);
    }

    //Desc; Set the user on the session.
    public static void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(USER_ATTRIBUTE, user);
    }
}
