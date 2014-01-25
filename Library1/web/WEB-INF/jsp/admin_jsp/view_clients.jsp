<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_view_clients" bundle="${rb}"/></title>
    <link rel="stylesheet" href="dist/css/bootstrap.css"/>
</head>
<body>
<table align="left">
    <form action="ServletController?method=log_out" method="POST">
        <input class="btn btn-default" type="submit" value=<fmt:message key="log_out" bundle="${rb}"/>>
    </form>
</table>
<table align="right">
    <tr>
        <td>
            <form action="ServletController?method=ru" method="POST">
                <input class="btn btn-default" type="submit" value=RU>
            </form>
        </td>
        <td>
            <form action="ServletController?method=en" method="POST">
                <input class="btn btn-default" type="submit" value=EN>
            </form>
        </td>
    </tr>
</table>
<div class="container" align="center">
    <h1 align="center"><fmt:message key="label.title_view_clients" bundle="${rb}"/></h1>
    <table border="1" align="center">
        <tr>

            <td><fmt:message key="field.name" bundle="${rb}"/></td>
            <td><fmt:message key="field.surname" bundle="${rb}"/></td>
        </tr>
        <c:forEach var="reader" items="${readers}">
            <tr>
                <td>${reader.name}</td>
                <td>${reader.surname}</td>
                <form action="ServletController?method=admin_view_client_card" method="POST">
                    <td>
                        <input type="hidden" name="idCl" value="${reader.id}"/>
                        <input type="submit" class="btn btn-default" value=<fmt:message key="button_view_taken_books"
                                                                                        bundle="${rb}"/>>
                    </td>
                </form>
                <form action="ServletController?method=delete_client_from_db" method="POST">
                    <td>
                        <input type="hidden" name="idCl" value="${reader.id}"/>
                        <input type="submit" class="btn btn-default" value=<fmt:message key="button_delete_clients"
                                                                                        bundle="${rb}"/>>
                    </td>
                </form>
            </tr>
        </c:forEach>
    </table>

    <form action="ServletController?method=return_to_admin_page" method="POST">
        <input type="submit" class="btn btn-default" align="center" value=<fmt:message key="button_return_to_admin_page"
                                                                                       bundle="${rb}"/>>
    </form>
</div>
</body>
</html>
