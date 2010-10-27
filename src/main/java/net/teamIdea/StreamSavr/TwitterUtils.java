package net.teamIdea.StreamSavr;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TwitterUtils {
    private static final String CONSUMER_KEY = "S1KhK1GX3KFneNEWd3AClA";
    private static final String CONSUMER_SECRET = "G0dLbBSM3zaS6CpBkZlicqRzD1PE1UiV89GsBBl784";
    public static final String ACCESS_TOKEN_ATTRIBUTE = "accessToken";
    public static final String TWITTER_ATTRIBUTE = "twitter";

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
        return (Twitter) request.getAttribute(TWITTER_ATTRIBUTE);
    }

    public static void setTwitter(HttpServletRequest request, Twitter twitter) {
        request.setAttribute(TWITTER_ATTRIBUTE, twitter);
    }
}
