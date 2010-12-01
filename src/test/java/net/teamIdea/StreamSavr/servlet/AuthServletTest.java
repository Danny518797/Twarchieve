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
    public void doGetShouldDoSomething () throws Exception {
        // mock http
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        // mock twitter
        Twitter twitter = mock(Twitter.class);
        User user = mock(User.class);
        
    }
}