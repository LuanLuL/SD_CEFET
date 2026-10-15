<%-- 
    Document   : mensagem
    Created on : Oct 15, 2026, 7:48:26 AM
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
            out.print(
                "Nickname = " + session.getAttribute("nickName") + 
                "<br>radCor = " + session.getAttribute("cor") + 
                "<br>radAvatar = " + session.getAttribute("avatarImgName")
            );
        %>
        <h1>Mensagem</h1>
    </body>
</html>
