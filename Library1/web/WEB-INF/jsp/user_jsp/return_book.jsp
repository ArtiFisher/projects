<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_return_book" bundle="${rb}"/></title>
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
    <h1><fmt:message key="label.page_name_return" bundle="${rb}"/></h1>

    <c:if test="${bookNumber > 0}">
        <table border="1">
            <tr>
                <td><fmt:message key="book_ISBN" bundle="${rb}"/></td>
                <td><fmt:message key="book_title" bundle="${rb}"/></td>
                <td><fmt:message key="book_author" bundle="${rb}"/></td>
                <td><fmt:message key="book_year" bundle="${rb}"/></td>
                <td></td>
            </tr>
            <c:forEach var="book" items="${books}">
                <form action="ServletController?method=update_db_after_returning_book" method="POST">
                    <tr>
                        <td>${book.ISBN}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.year}</td>
                        <td>
                            <input type="hidden" name="id" value="${book.id}"/>
                            <input class="btn btn-default" type="submit" value=<fmt:message key="return_book"
                                                                                            bundle="${rb}"/>>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${bookNumber <= 0}">
        <h2><fmt:message key="msgNoBooks" bundle="${rb}"/></h2>
    </c:if>


    <form action="ServletController?method=return_to_user_page" method="POST">
        <input type="submit" class="btn btn-default" value=<fmt:message key="return_to_user_page" bundle="${rb}"/>>
    </form>

</div>
</body>
</html>
