package net.teamIdea.StreamSavr.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 26, 2010
 * Time: 7:56:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallbackServlet extends HttpServlet {

    public static final String CALLBACK_FORM_VIEW = "/WEB-INF/jsp/callback.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(CALLBACK_FORM_VIEW).forward(req, resp);
    }
}
