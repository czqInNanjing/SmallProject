package cn.action;

import cn.constants.Constants;
import cn.constants.OnlineNumberCounter;
import cn.entity.ScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.service.StudentScoreService;
import cn.service.UserService;
import org.springframework.stereotype.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
@Controller
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private static OnlineNumberCounter counter = OnlineNumberCounter.getCounter();

    @Autowired
    private UserService userService;
    @Autowired
    private StudentScoreService studentScoreService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAction() {
        super();


    }


//    /**
//     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            request.getRequestDispatcher("/html/login.jsp").forward(request,response);
//        } else { // session has been created
//            if (session.getAttribute(Constants.userIDStr) == null) { // a visitor
//                request.getRequestDispatcher("/jsp/visitor.jsp").forward(request, response);
//            }else { // has logged in
//                int studentID = (int) session.getAttribute(Constants.userIDStr);
//                List<ScoreEntity> scores = studentScoreService.getStudentsScores(studentID);
//                List<ScoreEntity> normalScores = new ArrayList<>();
//                List<ScoreEntity> notAttendedScores = new ArrayList<>();
//
//                for (ScoreEntity score : scores) {
//                    if (score.getScore() >= 60) {
//                        normalScores.add(score);
//                    } else {
//                        notAttendedScores.add(score);
//                    }
//                }
//
//                request.setAttribute(Constants.normalScores , normalScores);
//                request.setAttribute(Constants.unattendedScores , notAttendedScores);
//                request.getRequestDispatcher("/jsp/scores.jsp").forward(request, response);
//            }
//        }
//
//    }


    @Override
    public String execute() throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + password + "is login");
        int studentID = userService.userExists(username , password);
        if (studentID == -1) { // user not exists
            RequestDispatcher dispatcher
                    =request.getRequestDispatcher("/html/login.jsp");

            dispatcher.forward(request,response);


        } else {
            HttpSession session = request.getSession(true);
            if ( session.getAttribute(Constants.userIDStr) == null) {
                // did not login
                session.setAttribute(Constants.userIDStr , studentID);
            }
            List<ScoreEntity> scores = studentScoreService.getStudentsScores(studentID);
            List<ScoreEntity> normalScores = new ArrayList<>();
            List<ScoreEntity> notAttendedScores = new ArrayList<>();

            for (ScoreEntity score : scores) {
                if (score.getScore() >= 60) {
                    normalScores.add(score);
                } else {
                    notAttendedScores.add(score);
                }
            }

            request.setAttribute(Constants.normalScores , normalScores);
            request.setAttribute(Constants.unattendedScores , notAttendedScores);
            request.getRequestDispatcher("/jsp/scores.jsp").forward(request, response);


        }
        return super.execute();
    }

//    /**
//     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        System.out.println(username + password + "is login");
//        int studentID = userService.userExists(username , password);
//        if (studentID == -1) { // user not exists
//            RequestDispatcher dispatcher
//                    =req.getRequestDispatcher("/html/login.jsp");
//
//            dispatcher.forward(req,resp);
//
//
//        } else {
//            HttpSession session = req.getSession(true);
//            if ( session.getAttribute(Constants.userIDStr) == null) {
//                // did not login
//                session.setAttribute(Constants.userIDStr , studentID);
//            }
//            List<ScoreEntity> scores = studentScoreService.getStudentsScores(studentID);
//            List<ScoreEntity> normalScores = new ArrayList<>();
//            List<ScoreEntity> notAttendedScores = new ArrayList<>();
//
//            for (ScoreEntity score : scores) {
//                if (score.getScore() >= 60) {
//                    normalScores.add(score);
//                } else {
//                    notAttendedScores.add(score);
//                }
//            }
//
//            req.setAttribute(Constants.normalScores , normalScores);
//            req.setAttribute(Constants.unattendedScores , notAttendedScores);
//            req.getRequestDispatcher("/jsp/scores.jsp").forward(req, resp);
//
//
//        }
//
//    }




}
