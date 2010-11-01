package net.teamIdea.StreamSavr;

import twitter4j.*;
import twitter4j.http.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        return (Twitter) request.getSession().getAttribute(TWITTER_ATTRIBUTE);
    }

    public static void setTwitter(HttpServletRequest request, Twitter twitter) {
        request.getSession().setAttribute(TWITTER_ATTRIBUTE, twitter);
    }

    /* Description: gets all of the users tweets and returns them as a tweet list
     * Arguments: twitter, the twitter object of the current user.
     * Return: A TweetList containing all of the user's tweets (up to 3200).
     */
    public static List getTweets(Twitter twitter)
    {
        List<Status> toArchive = new ArrayList();

        try {
            Paging page = new Paging(1, 200);
            ResponseList<Status> tweets; //This is where we store the tweets before moving to TweetList object.

            tweets = twitter.getUserTimeline(page); //get the first set (page) of tweets.

            /* Debug: */
            //System.out.println("Tweets Size: " + tweets.size());
            //System.out.println("toArchive size: " + toArchive.getSize());

            int currentPage = 1; //For paging variable. Start at page 1, then increment with each loop (get the next 200 tweets).
            while(tweets.size() > 0) //quit loop if there are no more tweets to get.
            {
                //loop through tweet and add each status object to toArchive
                for( Status i : tweets)
                    toArchive.add(i);

                /* Debug: */
                //for(int i = 0; i < toArchive.size(); ++i)
                //    System.out.println(toArchive.get(i).getText());

                //Increment page variable to fetch next page on getUserTimeline call.
                currentPage++;
                page.setPage(currentPage);

                //Get the next page.
                tweets = twitter.getUserTimeline(page);
            }


        } catch (TwitterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return toArchive; //return the full archive.
    }

}
