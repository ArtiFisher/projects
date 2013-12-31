<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${loc}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="label.title_take_book" bundle="${rb}"/></title>
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
        <h1><fmt:message key="label.page_name_take_book" bundle="${rb}"/></h1>
        <c:if test="${bookNumber > 0}">
           
        <font label.title_take_book size=2 color=red>
            <c:out value="${error}"/>
        </font>

        <font size=2 color=red>
            <c:out value="${error2}"/>
        </font>
        <table border="1">            
            <td><fmt:message key="book_ISBN" bundle="${rb}"/></td>
            <td><fmt:message key="book_title" bundle="${rb}"/></td>
            <td><fmt:message key="book_author" bundle="${rb}"/></td>
            <td><fmt:message key="book_year" bundle="${rb}"/></td>
            <td><fmt:message key="book_numbaerOfCopies" bundle="${rb}"/></td>
            <c:forEach var="book" items="${books}">
                <form action="ServletController?method=update_db_after_taking_book" method="POST">
                    <tr>
                        <c:if test="${book.numberOfCopies != 0}">                           
                            <td>${book.ISBN}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.year}</td>
                            <td>${book.numberOfCopies}</td>
                            <td>
                                <input type="hidden" name="id" value="${book.id}"/>
                                <input type="submit" value=<fmt:message key="button_take_book" bundle="${rb}"/>>
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
        <form action="ServletController?method=return_to_user_page" method="POST">
            <input type="submit" value=<fmt:message key="return_to_user_page" bundle="${rb}"/>>
        </form>
        <form action="ServletController?method=log_out" method="POST">
            <input type="submit" value=<fmt:message key="log_out" bundle="${rb}"/>>
        </form>       
    </body>
</html>
