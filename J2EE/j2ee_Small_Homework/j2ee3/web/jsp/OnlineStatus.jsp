<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="constants.OnlineNumberCounter" %><%--
  User: Qiang
  Date: 03/01/2017
  Time: 14:47
--%>
<%
    int[] onlineStatus = OnlineNumberCounter.getCounter().getOnlineStatus();
%>
<div class = "row">
    <div class="col-md-12">
        <p>总人数: <%= onlineStatus[2] %></p>
        <p>已登陆人数:<%= onlineStatus[0] %> </p>
        <p>游客人数: <%= onlineStatus[1] %></p>
    </div>
</div>
<a href="${pageContext.request.contextPath}/logout">注销</a>