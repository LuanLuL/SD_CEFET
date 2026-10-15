<%-- 
    Document   : chat
    Created on : Oct 15, 2026, 7:41:04 AM
    Author     : LuanLuL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sala de bate papo</title>
    </head>
    <body>
        <%
//            out.print(
//                "Nickname = " + request.getParameter("txtNickname") + 
//                "<br>radCor = " + request.getParameter("radCor") + 
//                "<br>radAvatar = " + request.getParameter("radAvatar")
//            );
//            
            session.setAttribute("nickName", request.getParameter("txtNickname"));
            session.setAttribute("cor", request.getParameter("radCor"));
            session.setAttribute("avatarImgName", request.getParameter("radAvatar"));

        %>
        <iframe src="topo.jsp" name="topo" width="100%" height="100px"></iframe>
        <iframe src="conversa.jsp" name="conversa" width="100%" height="375px"></iframe>
        <iframe src="mensagem.jsp" name="mensagem" width="100%" height="150px"></iframe>
    </body>
</html>
