<%-- 
    Document   : index
    Created on : Oct 14, 2026, 9:37:56 AM
    Author     : LuanLuL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat Distribuído-WEB</title>
    </head>
    <body>
        <br>
        <br>
        <br>
        <br>
        <br>
        <center>
            <form action="chat.jsp" method="post">
                <table border="0">
                    <tr>
                        <td colspan="2" bgcolor="tomato" align="center">
                            <font size="+2" color="white">Chat WEB 1.0</font>
                        </td>
                    </tr>                
                    <tr>
                        <td align="right">
                            <b>Nickname:</b>
                        </td>
                        <td>
                            <input type="text" name="txtNickname" maxlength="20" size="100">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <b>Cor:</b>
                        </td>
                        <td>
                            <input type="radio" name="radCor" value="navy">
                            <font color="navy"><b>Azul</b></font>
                            <input type="radio" name="radCor" value="forestgreen">
                            <font color="forestgreen"><b>Verde</b></font>
                            <input type="radio" name="radCor" value="hotpink" checked>
                            <font color="hotpink"><b>Rosa</b></font>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                           <b>Avatar:</b>
                        </td>
                        <td>
                            <input type="radio" name="radAvatar" value="AvatarHomem.png">
                            <img src="images/AvatarHomem.png" alt="AvatarHomem.png" width="24" height="24"/>
                            <input type="radio" name="radAvatar" value="AvatarMulher.png">
                            <img src="images/AvatarMulher.png" alt="AvatarMulher.png" width="24" height="24"/>
                            <input type="radio" name="radAvatar" value="AvatarIndeterminado.png" checked>
                            <img src="images/AvatarIndeterminado.png" alt="AvatarIndeterminado.png" width="24" height="24"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" bgcolor="tomato" align="center">
                            <input type="submit" name="btnEntrar" value="Entrar">
                        </td>
                    </tr>   
                </table>
            </form>
        </center>
    </body>
</html>
