package servlets;

import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.internal.jxc.ap.Const;
import constants.Constants;
import constants.OnlineNumberCounter;
import dao.StudentScoreDAO;
import dao.UserDAO;
import entity.Score;
import factory.DAOFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * 学生登录，跟据学生的ID，查询所选课程测验情况
    并根据具体情况，返回不同结果：
     有未参加的测验：警示页面
     正常结果：标准页面
     未知的学生ID：错误页面
     ……
     Servlet+Session+JDBC

 * @author Qiang
 * @date 07/12/2016
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static OnlineNumberCounter counter = OnlineNumberCounter.getCounter();
    private UserDAO userDAO;
    private StudentScoreDAO studentScoreDAO;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();

        userDAO = DAOFactory.getUserDAO();
        studentScoreDAO = DAOFactory.getStudentDAO();

    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.getRequestDispatcher("/html/login.html").forward(request,response);
        } else { // session has been created
            if (session.getAttribute(Constants.userIDStr) == null) { // a visitor
                request.getRequestDispatcher("/jsp/visitor.jsp").forward(request, response);
            }else { // has logged in
                int studentID = (int) session.getAttribute(Constants.userIDStr);
                List<Score> scores = studentScoreDAO.getStudentsScores(studentID);
                List<Score> normalScores = new ArrayList<>();
                List<Score> notAttendedScores = new ArrayList<>();

                for (Score score : scores) {
                    if (score.score >= 60) {
                        normalScores.add(score);
                    } else {
                        notAttendedScores.add(score);
                    }
                }

                request.setAttribute(Constants.normalScores , normalScores);
                request.setAttribute(Constants.unattendedScores , notAttendedScores);
                request.getRequestDispatcher("/jsp/scores.jsp").forward(request, response);
            }
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username + password + "is login");
        int studentID = userDAO.userExists(username , password);
        if (studentID == -1) { // user not exists
            RequestDispatcher dispatcher
                    =req.getRequestDispatcher("/html/login.html");

            dispatcher.forward(req,resp);


        } else {
            HttpSession session = req.getSession(true);
            if ( session.getAttribute(Constants.userIDStr) == null) {
                // did not login
                session.setAttribute(Constants.userIDStr , studentID);
            }
            List<Score> scores = studentScoreDAO.getStudentsScores(studentID);
            List<Score> normalScores = new ArrayList<>();
            List<Score> notAttendedScores = new ArrayList<>();

            for (Score score : scores) {
                if (score.score >= 60) {
                    normalScores.add(score);
                } else {
                    notAttendedScores.add(score);
                }
            }

            req.setAttribute(Constants.normalScores , normalScores);
            req.setAttribute(Constants.unattendedScores , notAttendedScores);
            req.getRequestDispatcher("/jsp/scores.jsp").forward(req, resp);


        }

    }




}
