<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${loc}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="label.title_reg" bundle="${rb}"/></title>
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
        <h1><fmt:message key="label.page_name_reg" bundle="${rb}"/></h1>
        <form action="ServletController?method=register" method="POST">
            <table>
                <tr>
                    <td><fmt:message key="field.name" bundle="${rb}"/></td>
                    <td><input type="text" name="name"></td>
                    <td></td>
                </tr>
                <tr>
                    <td><fmt:message key="field.surname" bundle="${rb}"/></td>
                    <td><input type="text" name="surname"></td>
                    <td></td>
                </tr>
                <tr>
                    <td><fmt:message key="field.login" bundle="${rb}"/></td>
                    <td> <input type="text" name="login"></td>
                    <td><font size=2 color=red>
                            <c:out value="${errorLogin}"/>
                        </font></td>
                </tr>
                <tr>
                    <td><fmt:message key="field.password" bundle="${rb}"/></td>
                    <td><input type="text" name="password"></td>
                    <td><font size=2 color=red>
                            <c:out value="${errorPassword}"/>
                        </font></td>
                </tr>
            </table>
            
        <input type="submit" value=<fmt:message key="field.button_reg" bundle="${rb}"/>>
        </form>
        <form action="ServletController?method=log_out" method="POST">
            <input type="submit" value=<fmt:message key="field.button_to_main_page" bundle="${rb}"/>>
        </form>        
    </body>
</html>
