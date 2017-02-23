<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="exam" uri="/WEB-INF/tlds/checkSession.tld" %>
<%@ page import="cn.constants.Constants" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.entity.ScoreEntity" %>
<%@ page import="java.util.ArrayList" %>
<%--
  User: Qiang
  Date: 03/01/2017
  Time: 11:38
--%>
<!DOCTYPE html>
<html lang="zh" >
<head>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

    <meta charset="UTF-8">
    <title>Exams</title>
</head>
<body>
<exam:checkSession/>
<%
    List<ScoreEntity> normalScores = new ArrayList<>();
    List<ScoreEntity> unattendedScores = new ArrayList<>();
    if (request.getAttribute(Constants.normalScores) != null) {
        normalScores = (List<ScoreEntity>) request.getAttribute(Constants.normalScores);
        unattendedScores = (List<ScoreEntity>) request.getAttribute(Constants.unattendedScores);
    } else {
        out.println("抱歉，请从正常界面进入，直接访问此jsp页面没有数据。。。。。。");
    }

//    System.out.println(normalScores.size());
//    System.out.println(unattendedScores.size());


%>
<div class="row">
    <div class="col-md-10">
        <div class="panel panel-success">
            <!-- Default panel contents -->
            <div class="panel-heading"><span style="font-size: 20px; ">考试成绩</span></div>
            <div class="panel-body">
                <!-- Table -->
                <table class="table">
                    <thead>
                    <tr>
                        <th>学生Id</th>
                        <th>课程ID</th>
                        <th>分数</th>
                    </tr>
                    </thead>
                    <tbody>
                        <%for (ScoreEntity score : normalScores) {%>
                    <tr>
                        <td><%= score.getStudentId() %></td>
                        <td><%= score.getCourseId() %></td>
                        <td><%= score.getScore() %></td>
                    </tr>
                        <%}%>
                        <%for (ScoreEntity score : unattendedScores) {%>
                        <tr style="color: red">
                            <td><%= score.getStudentId() %></td>
                            <td><%= score.getCourseId() %></td>
                            <td><%= score.getScore() %></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="OnlineStatus.jsp"/>

</body>
</html>