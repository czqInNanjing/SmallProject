package cn.action;

import org.springframework.stereotype.Controller;

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
@Controller
public class VisitorAction extends BaseAction {
    @Override
    public String execute() throws Exception {
        request.getSession(true);
        request.getRequestDispatcher("/jsp/visitor.jsp").forward(request,response);
        return super.execute();
    }
}
