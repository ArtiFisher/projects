<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<fmt:setLocale value="ru_RU" scope="session"/>
<c:if test="${flag==1}">
    <fmt:setLocale value="${loc}" scope="session"/>
</c:if>
<fmt:setBundle basename="resources.pagecontent_en_US" var="rb"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="label.title" bundle="${rb}"/></title>
    </head>
    <body>
        <table>
            <tr>
                <td><form action="ServletController?method=ru" method="POST">
                        <input type="submit" value=RU>
                    </form></td>
                <td>
                    <form action="ServletController?method=en" method="POST">
                        <input type="submit" value=EN>
                    </form></td>
            </tr>
        </table>
        <h1><fmt:message key="label.page_name" bundle="${rb}"/></h1>
            <c:out value="${successfulRegistration}"/>
        </font>
        <form action="ServletController?method=authorization" method="POST">
            <table>
                <tr>
                    <td>
                        <font size=2 color=red>
                            <c:out value="${error}"/>
                        </font>
                    </td>
                </tr>
            </table>   
            <table>
                <tr>
                    <td><fmt:message key="field.login" bundle="${rb}"/></td>
                    <td><input type="text" name="login"></td>
                    <td></td>
                </tr>
                <tr>
                    <td><fmt:message key="field.password" bundle="${rb}"/></td>
                    <td><input type="password" name="password"></td>
                </tr>                
                <tr>
                    <td><input type="submit" value=<fmt:message key="field.log_ig" bundle="${rb}"/>></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </form>
        <form action="ServletController?method=registration" method="POST">
            <input type="submit" value=<fmt:message key="field.registration" bundle="${rb}"/>>
        </form>
    </body>
</html>
