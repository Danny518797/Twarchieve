package net.teamIdea.StreamSavr;

import twitter4j.*;
import twitter4j.http.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwitterUtils {
    private static final String CONSUMER_KEY = "S1KhK1GX3KFneNEWd3AClA";
    private static final String CONSUMER_SECRET = "G0dLbBSM3zaS6CpBkZlicqRzD1PE1UiV89GsBBl784";
    public static final String ACCESS_TOKEN_ATTRIBUTE = "accessToken";
    public static final String TWITTER_ATTRIBUTE = "twitter";
    public static final String USER_ATTRIBUTE = "user";
    public static final String AUTH_URI = "auth";
    public static final String DOWNLOADED_TWEETS = "downloaded";



    public static Twitter newTwitter() {
        return new TwitterFactory().getOAuthAuthorizedInstance(CONSUMER_KEY, CONSUMER_SECRET);
    }

    public static Twitter newTwitter(AccessToken accessToken) {
        return new TwitterFactory().getOAuthAuthorizedInstance(CONSUMER_KEY, CONSUMER_SECRET, accessToken);
    }

    public static AccessToken getAccessToken(HttpSession session) {
        return (AccessToken) session.getAttribute(ACCESS_TOKEN_ATTRIBUTE);
    }

    public static void setAccessToken(HttpSession session, AccessToken accessToken) {
        session.setAttribute(ACCESS_TOKEN_ATTRIBUTE, accessToken);
    }

    public static Twitter getTwitter(HttpServletRequest request) {
        return (Twitter) request.getSession().getAttribute(TWITTER_ATTRIBUTE);
    }

    public static void setTwitter(HttpServletRequest request, Twitter twitter) {
        request.getSession().setAttribute(TWITTER_ATTRIBUTE, twitter);
    }

    public static void setTweetsDownloaded(HttpServletRequest request, Integer downloaded) {
        request.getSession().setAttribute(DOWNLOADED_TWEETS, downloaded);
    }

    public static Integer getTweetsDownloaded(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute(DOWNLOADED_TWEETS);
    }

    public static void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(USER_ATTRIBUTE, user);
    }

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(USER_ATTRIBUTE);
    }


    public static void checkLoggedIn(HttpServletResponse response, Twitter twitter) {
        if( twitter == null ) {
            try {
                response.sendRedirect(AUTH_URI);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return;
        }
        try {
            if( !twitter.verifyCredentials().isVerified() )
                response.sendRedirect(AUTH_URI);
        } catch (TwitterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
