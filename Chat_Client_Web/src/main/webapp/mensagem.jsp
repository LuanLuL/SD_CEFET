<%-- 
    Document   : mensagem
    Created on : Oct 15, 2026, 7:48:26 AM
    Author     : LuanLuL
--%>

<%@page import="java.io.ObjectOutputStream"%>
<%@page import="java.net.Socket"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat</title>
    </head>
    <body>
        <%
            if(request.getParameter("btnEnviar") != null){
                String msg = "";
                msg += "<img src=\"" + session.getAttribute("avatarImgName") +"\" width=\"24\" height=\"24\">";
                msg += "<font size=\"+1\" color=\""+ session.getAttribute("cor") +"\"><b>"+ session.getAttribute("nickName") + "</b></font>";
                msg += " diz: " + request.getParameter("txtMsg");
                
                if(!request.getParameter("cbEmoji").equals("")){
                    msg += "<img src=\"" + request.getParameter("cdEmoji") + "\" width=\"24\" height=\"24\" />";
                }
                
                msg += "<br>";
                
                Socket socket = new Socket("127.0.0.1", 6662);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                output.writeUTF(msg);
                output.close();
                socket.close();
            }
        %>
        <form name="frmMsg" method="post">
            <Label for="txtMsg">Mensagem:</label>
            <input type="text" name="txtMsg" size="100" maxlength="200"/>
            <Label for="cbEmoji">Emoji:</label>
            <select name="cbEmoji">
                <option value="">Selecione</option>
                <option value="images/Beijo.png">Beijo</option>
                <option value="images/Coração.png">Coração</option>
                <option value="images/Dinheiro.png">Dinheiro</option>
                <option value="images/Bandeira.gif">Bandeira</option>
                <option value="images/Danca.gif">Dança</option>
                <option value="images/Joinha.gif">Joinha</option>
            </select>
            <input type="submit" name="btnEnviar" value="enviar"/>
        </form>
    </body>
</html>
