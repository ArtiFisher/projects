<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="bktbl" uri="booktabletag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="label.title_view_client_card" bundle="${rb}"/></title>
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
        <h1><fmt:message key="label.title_view_client_card" bundle="${rb}"/></h1>

        <c:if test="${bookNumber > 0}">
            <bktbl:booktable books="${books}"></bktbl:booktable>
        </c:if>
        <c:if test="${bookNumber <= 0}">
            <h2><fmt:message key="msgNoBooks" bundle="${rb}"/></h2>
        </c:if>
        <form action="ServletController?method=return_to_admin_page" method="POST">
            <input type="submit" value=<fmt:message key="button_return_to_admin_page" bundle="${rb}"/>>
        </form>
        <form action="ServletController?method=log_out" method="POST">
            <input type="submit" value=<fmt:message key="log_out" bundle="${rb}"/>>
        </form>
    </body>
</html>
