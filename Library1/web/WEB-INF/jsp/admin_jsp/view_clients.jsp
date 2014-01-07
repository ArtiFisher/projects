<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="label.title_view_clients" bundle="${rb}"/></title>
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
        <h1><fmt:message key="label.title_view_clients" bundle="${rb}"/></h1>
        <table border="1">
            <tr>
                
                <td><fmt:message key="field.name" bundle="${rb}"/></td>
                <td><fmt:message key="field.surname" bundle="${rb}"/></td>
                <td><fmt:message key="field.age" bundle="${rb}"/></td>
            </tr>
            <c:forEach var="reader" items="${readers}">
                <form action="ServletController?method=admin_view_client_card" method="POST">
                    <tr>                        
                        <td>${reader.name}</td>
                        <td>${reader.surname}</td>
                        <td>${reader.age}</td>
                        <td>
                            <input type="hidden" name="idCl" value="${reader.id}"/>
                            <input type="submit" value=<fmt:message key="button_view_taken_books" bundle="${rb}"/>>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>

        <form action="ServletController?method=return_to_admin_page" method="POST">
            <input type="submit" value=<fmt:message key="button_return_to_admin_page" bundle="${rb}"/>>
        </form>
        <form action="ServletController?method=log_out" method="POST">
            <input type="submit" value=<fmt:message key="log_out" bundle="${rb}"/>>
        </form>
    </body>
</html>
