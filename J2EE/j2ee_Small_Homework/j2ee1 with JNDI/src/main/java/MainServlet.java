import constants.Constants;
import constants.OnlineNumberCounter;
import entity.Score;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;
    private static OnlineNumberCounter counter = OnlineNumberCounter.getCounter();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
    }

    @Override
    public void init(){

        //使用外部数据库连接池 HikariSQLPool
        InitialContext jndiContext ;

        try {
            jndiContext = new InitialContext();
            datasource = (DataSource) jndiContext
                    .lookup("java:comp/env/jdbc/j2ee1");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        this.getServletContext().setAttribute("webCounter" , "0");
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        if (request.getSession(false) != null && request.getSession(false).getAttribute(Constants.userIDStr) != null) {
            HttpSession session = request.getSession();
            session.invalidate();

        }


        String studentID="";

        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();


        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (Cookie cooky : cookies) {
                cookie = cooky;
                if (cookie.getName().equals("LoginCookie")) {
                    studentID = cookie.getValue();
                    break;
                }
            }
        }


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println(
                "<form method='POST' action='"
                        + response.encodeURL(request.getContextPath()+"/login")
                        + "'>");
        out.println(
                "Student ID: <input type='text' name='StudentID' value='" + studentID +"'>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("name: <input type='text' name='name' value=''>");
        out.println("<input type='submit' name='Submit' value='Submit'>");



        HttpSession session = request.getSession(true);
        if (session.getAttribute(Constants.userIDStr) != null) {
            System.out.println("Has login !!! user id is " + session.getAttribute(Constants.userIDStr) );
//            response.sendRedirect("doPost");
        }
        int[] online = counter.getOnlineStatus();
        out.println("</form>");

        out.println("<p>Temporary online number : "  + online[2] + " Login User : " + online[0] + " Visitors : " + online[1] + "</p>");



        out.println("</body></html>");

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIDStr = "StudentID";
        String passwordStr = "password";
        String nameStr = "name";
        HttpSession session = req.getSession(false);
        boolean cookieFound = false;

        System.out.println(req.getParameter(studentIDStr) +"is requesting!!!!");
        System.out.println(req.getParameter(passwordStr) +"is password requesting!!!!");
        System.out.println(req.getParameter(nameStr) +"is name requesting!!!!");


        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("LoginCookie")) {
                    cookieFound = true;
                    break;
                }
            }
        }

        if (session == null) {
            String studentID = req.getParameter(studentIDStr);
            boolean isLoginAction = (null != studentID);

            System.out.println(studentID +"session null");
            if (isLoginAction) { // User is logging in
                if (cookieFound) { // If the cookie exists update the value only
                    // if changed
                    if (!studentID.equals(cookie.getValue())) {
                        cookie.setValue(studentID);
                        resp.addCookie(cookie);
                    }
                } else {
                    // If the cookie does not exist, create it and set value
                    cookie = new Cookie("LoginCookie", studentID);
                    cookie.setMaxAge(60);
                    System.out.println("Add login cookie");
                    resp.addCookie(cookie);
                }

                // create a session to show that we are logged in
                session = req.getSession(true);
                session.setAttribute("studentID", studentID);

                List<Score> scores = getStudentScores(req ,resp, Integer.parseInt(studentID));

                PrintWriter out = resp.getWriter();
                out.println("<html><body>");

                if (scores == null || scores.size() == 0) {

                    out.println("Warning: the student ID does not exist!!!!" );



                } else {


                    if ( session.getAttribute("HasNoAttendExams")!= null  && (boolean)session.getAttribute("HasNoAttendExams")){
                        out.println("Warning: the student ID has exam that he or she does not attend!!!!" );
                        out.println("the score that is zero means the student did not attend the exam" );
                    }

                    out.println("<ol>");
                    for (Score score : scores) {
                        out.println("<li>" + " studentID : " + score.studentID + " courseID : " + score.courseID + "score"  + score.score + "</li>");
                    }
                    out.println("</ol>");

                    int[] online = counter.getOnlineStatus();
                    out.println("</form>");

                    out.println("<p>Temporary online number : "  + online[2] + " Login User : " + online[0] + " Visitors : " + online[1] + "</p>");

                }


            } else {   // if session is null && is not from login action, which means that the user type the link directly
                System.out.println("session is  null, redirect to the login page");
                resp.sendRedirect(req.getContextPath() + "/login");
            }


        } else {


            String studentID = (String) session.getAttribute(studentIDStr);
            System.out.println(studentID +"session");
            if(studentID==null  || !Objects.equals(studentID, req.getParameter(studentIDStr))){
                System.out.println(studentID +" null or old login");
                studentID = req.getParameter(studentIDStr);
                System.out.println(studentID +" request");
                session.setAttribute(Constants.userIDStr,studentID  );

            }

            session.setAttribute("studentID", studentID);

            List<Score> scores = getStudentScores(req ,resp, Integer.parseInt(studentID));

            PrintWriter out = resp.getWriter();
            out.println("<html><body>");

            if (scores == null || scores.size() == 0) {

                out.println("Warning: the student ID does not exist!!!!" );



            } else {


                if ( session.getAttribute("HasNoAttendExams")!= null  && (boolean)session.getAttribute("HasNoAttendExams")){

                    out.println("Warning: the student ID has exam that he or she does not attend!!!!" );
                    out.println("the score that is zero means the student did not attend the exam" );
                }

                out.println("<ol>");
                for (Score score : scores) {
                    out.println("<li>" + " studentID : " + score.studentID + " courseID : " + score.courseID + "score"  + score.score + "</li>");
                }
                out.println("</ol>");
                int[] online = counter.getOnlineStatus();

                out.println("</form>");
                out.println("<p>Temporary online number no : "  + online[2] + " Login User : " + online[0] + " Visitors : " + online[1] + "</p>");

            }

        }
        PrintWriter out = resp.getWriter();

        out.println("<button><a href = " + resp.encodeURL(req.getContextPath()+"/login") + ">logout</button>");


    }


    public List<Score> getStudentScores(HttpServletRequest req, HttpServletResponse resp,int studentID) {
        List<Score> scores = new ArrayList<>();
        try {
            String sql = "SELECT * FROM score WHERE studentID = ?";
            Connection conn = datasource.getConnection();
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, studentID);
            ResultSet resultSet = prep.executeQuery();
            Score score;
            while (resultSet.next()){
                int courseId = resultSet.getInt(2);
                int examScore = resultSet.getInt(3);
                if (examScore == 0)
                {
                    HttpSession session = req.getSession(false);
                    if (session != null) {
                        session.setAttribute("HasNoAttendExams" , true);
                    }
                }
                scores.add(new Score(studentID, courseId , examScore));

            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }


}
