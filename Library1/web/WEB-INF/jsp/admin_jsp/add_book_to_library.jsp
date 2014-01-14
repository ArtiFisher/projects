<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_adding_page" bundle="${rb}"/></title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.css">
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
    <h1><fmt:message key="label.title_adding_page" bundle="${rb}"/></h1>

    <form action="ServletController?method=add_book_filling_information" method="POST">
        <table align="center">
            <tr>
                <td><fmt:message key="book_ISBN" bundle="${rb}"/></td>
                <td><input type="text" name="ISBN" class="form-control" style="width: 100%" autofocus required></td>
                <td><font size=2 color=red>
                    <c:out value="${errorISBN}"/>
                </font></td>
            </tr>
            <tr>
                <td><fmt:message key="book_title" bundle="${rb}"/></td>
                <td><input type="text" name="title" class="form-control" style="width: 100%" required></td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="book_author" bundle="${rb}"/></td>
                <td><input type="text" name="author" class="form-control" style="width: 100%" required></td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="book_year" bundle="${rb}"/></td>
                <td><input type="text" name="year" class="form-control" style="width: 100%" required></td>
                <td><font size=2 color=red>
                    <c:out value="${errorYear}"/>
                </font></td>
            </tr>
            <tr>
                <td><fmt:message key="book_numberOfCopies" bundle="${rb}"/></td>
                <td><input type="text" name="copiesNumber" class="form-control" style="width: 100%" required></td>
                <td><font size=2 color=red>
                    <c:out value="${errorNumOfCopies}"/>
                </font></td>
            </tr>
        </table>
        <input type="submit" class="btn btn-default" value=<fmt:message key="button_add_book" bundle="${rb}"/>>
    </form>
    <form action="ServletController?method=return_to_admin_page" method="POST">
        <input type="submit" class="btn btn-default" value=<fmt:message key="button_return_to_admin_page"
                                                                        bundle="${rb}"/>>
    </form>
</div>
</body>
</html>
