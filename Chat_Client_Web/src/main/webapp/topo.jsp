<%-- 
    Document   : topo.jsp
    Created on : Oct 15, 2026, 7:47:54 AM
    Author     : LuanLuL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            //out.print("<img src=\"images/"+ session.getAttribute("avatarImgName") + "\" width=\"50\" height=\"50\"> ");
        %>
        <img src="images/<%=session.getAttribute("avatarImgName")%>" width="50" height="50">
        <font size="+2" color="<%=session.getAttribute("cor")%>"><%=session.getAttribute("nickName")%></font>
    </body>
</html>
