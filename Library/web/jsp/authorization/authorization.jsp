<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="tag"%>
<link rel="stylesheet" type="text/css" href="/AdmissionSystem/css/style.css"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tag:getresource key="jsp.title.authorization"/></title>
</head>
<%--background="/AdmissionSystem/image/bg1.bmp"--%>
<body class="body">
<div class="languages"><tag:languagemenu forwardString="jsp/authorization/authorization.jsp"/></div>
<div class="authorization">
    <h3><tag:getresource key="jsp.title.authorization"/></h3>
    <form action="controller" method="POST">
        <table>
            <tr>
                <td><tag:getresource key="jsp.title.login"/></td>
                <td><input type="text" name="login" value="${lastLogin}"/></td>
            </tr>
            <tr>
                <td><tag:getresource key="jsp.title.password"/></td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="hidden" name="commandName" value="AUTHORIZATION"/>
                    <input type="submit" value="<tag:getresource key="jsp.button.enter"/>"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <a href="controller?commandName=TO_PAGE&forwardPage=<tag:getresource key="forward.registration"/>">
                        <tag:getresource key="jsp.button.registration"/></a>
                </td>
            </tr>

        </table>
    </form>
    <div class="messages">
        <tag:messages/>
    </div>
</div>
</body>
</html>
