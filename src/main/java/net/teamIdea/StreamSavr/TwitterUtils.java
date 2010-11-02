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
    public static final String AUTH_URI = "auth";
    public static final int MAX_TRIES = 4; //Max number of times we hit twitter before giving up.

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


    /* Description: gets all of the users tweets and returns them as a tweet list
     * Arguments: twitter, the twitter object of the current user.
     * Return: A List<Status> containing all of the user's tweets (up to 3200).
     */
    public static List<Status> getAllTweets(Twitter twitter) {
        List<Status> toArchive = new ArrayList<Status>(); //Where all the tweets are copied too.
        ResponseList<Status> tweets; //Set of 0-200 tweets returned by Twitter.

        int currentPage = 0;
        while(currentPage < 17) {
            //Gets a single page of tweets.
            tweets = getPage(twitter, new Paging(++currentPage, 200), 0);

            //If twitter retuns no tweets, you're done.
            if(tweets == null || tweets.isEmpty()) break;

            toArchive.addAll(tweets);

        }

        return toArchive;
    }

    /* Description: Recursive function that gets a single page worth of tweets (usually 200).
     *              The function will stop recursing once we've gotten to MAX_TRIES.
     */
    private static ResponseList<Status> getPage(Twitter twitter, Paging page, int currentTry) {
        ResponseList<Status> tweets = null;
        if(currentTry < MAX_TRIES) {
            try {
                tweets = twitter.getUserTimeline(page);
            }
            catch(TwitterException e) {
                //If we get an error 502, try hitting twitter again (ie recurse).
                if ( isRecoverable(e.getExceptionCode()) ) {
                    System.out.println("Error 502, trying again.");
                    tweets = getPage(twitter, page, ++currentTry);
                }
                else {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Hit Max Tries: " + MAX_TRIES);
        }

        return tweets;
    }

    /* Description: Returns true if the error code supplied is recoverable from. */
    private static boolean isRecoverable(String errorCode) {

        /* 401 Unauthorized: Authentication credentials were missing or incorrect.
         * 500 Internal Server Error: Something is broken.  Please post to the group so the Twitter team can investigate.
         * 502 Bad Gateway: Twitter is down or being upgraded.
         * 503 Service Unavailable: The Twitter servers are up, but overloaded with requests. Try again later.
         */

        if(errorCode == "502" || errorCode == "503" || errorCode == "401" || errorCode == "500")
            return true;

        return false;
    }

}
