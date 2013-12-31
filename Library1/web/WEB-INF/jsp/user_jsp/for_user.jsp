<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="label.title_user_page" bundle="${rb}"/></title>
    </head>
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
    <body>       
        <h1><fmt:message key="label.page_name_user_page" bundle="${rb}"/></h1>
        <font size=2 color=red>
            <c:out value="${emptyLibrary}"/>
        </font>

        <table>
            <tr>
                <td><fmt:message key="title.view_list_of_books" bundle="${rb}"/></td>
                <td>
                    <form action="ServletController?method=take_book" method="POST">
                        <input type="submit" value=<fmt:message key="button_user_view" bundle="${rb}"/>>
                    </form>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="title.view_my_card" bundle="${rb}"/></td>
                <td>
                    <form action="ServletController?method=return_book" method="POST">
                        <input type="submit" value=<fmt:message key="button_user_view" bundle="${rb}"/>>
                    </form>
                </td>
            </tr>
        </table>
        <h1></h1>
        <form action="ServletController?method=log_out" method="POST">
            <input type="submit" value=<fmt:message key="log_out" bundle="${rb}"/>>
        </form>
    </body>
</html>
