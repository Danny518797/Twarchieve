package net.teamIdea.StreamSavr.filter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.http.AccessToken;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.lang.System.getProperty;
import static net.teamIdea.StreamSavr.TwitterUtils.getAccessToken;
import static net.teamIdea.StreamSavr.TwitterUtils.newTwitter;
import static net.teamIdea.StreamSavr.TwitterUtils.setTwitter;

public class TwitterFilter implements Filter {
    public static final String TOKEN_PROPERTY = "token";
    public static final String TOKEN_SECRET_PROPERTY = "tokenSecret";
    public static final String AUTH_URI = "auth";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException,
            ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        /*
        * This section signs you into twitter
        */

        AccessToken accessToken = getAccessToken(session);
        if (accessToken == null) {
            accessToken = getSystemPropertyBasedAccessToken();
        }
        Twitter twitter = newTwitter(accessToken);

        try {
            User twitterUser = twitter.verifyCredentials();
            setTwitter(req, twitter);
            // setUser(req, twitterUser);
            chain.doFilter(request, response);
        } catch (TwitterException e) {
            resp.sendRedirect(AUTH_URI);    
        }
   }

    @Override
    public void destroy() {
    }

    private static AccessToken getSystemPropertyBasedAccessToken() {
        AccessToken accessToken = null;
        String token = getProperty(TOKEN_PROPERTY);
        String tokenSecret = getProperty(TOKEN_SECRET_PROPERTY);
        if (token != null && tokenSecret != null) {
            accessToken = new AccessToken(token, tokenSecret);
        }
        return accessToken;
    }
}
