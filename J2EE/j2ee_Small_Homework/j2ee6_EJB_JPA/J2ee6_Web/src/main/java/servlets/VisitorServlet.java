package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Qiang
 * @since 03/01/2017
 */
@WebServlet(urlPatterns = "/visitor")
public class VisitorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(true);
        req.getRequestDispatcher("/jsp/visitor.jsp").forward(req,resp);




    }
}
