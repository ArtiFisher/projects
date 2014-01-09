<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_remove_book" bundle="${rb}"/></title>
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
h1><fmt:message key="label.title_view_books" bundle="${rb}"/></h1>
<c:if test="${bookNumber > 0}">
    <table border="1">
        <tr>
            <td><fmt:message key="book_id" bundle="${rb}"/></td>
            <td><fmt:message key="book_ISBN" bundle="${rb}"/></td>
            <td><fmt:message key="book_title" bundle="${rb}"/></td>
            <td><fmt:message key="book_author" bundle="${rb}"/></td>
            <td><fmt:message key="book_year" bundle="${rb}"/></td>
            <td><fmt:message key="book_numberOfCopies" bundle="${rb}"/></td>
            <td></td>
        </tr>
        <c:forEach var="book" items="${books}">
            <form action="ServletController?method=update_db_after_removing" method="POST">
                <tr>
                    <c:if test="${book.numberOfCopies>0}">
                        <td>${book.id}</td>
                        <td>${book.ISBN}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.year}</td>
                        <td>${book.numberOfCopies}</td>
                        <td>
                            <input type="hidden" name="id" value="${book.id}"/>
                            <input type="submit" class="btn btn-default" value=<fmt:message key="button_remove_book"
                                                                                            bundle="${rb}"/>>
                        </td>
                    </c:if>
                </tr>
            </form>
        </c:forEach>
    </table>
</c:if>
<c:if test="${bookNumber <= 0}">
    <h2><fmt:message key="msgNoBooks" bundle="${rb}"/></h2>
</c:if>


<form action="ServletController?method=return_to_admin_page" method="POST">
    <input type="submit" class="btn btn-default" value=<fmt:message key="button_return_to_admin_page" bundle="${rb}"/>>
</form>

</body>
</html>
