<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_admin_page" bundle="${rb}"/></title>
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
    <h1><fmt:message key="label.title_admin_page" bundle="${rb}"/></h1>
    <font size=4 color=green>
        <c:out value="${bookAdded}"/>
    </font>
    <table>
        <tr>
            <td><fmt:message key="admin_add_book" bundle="${rb}"/></td>
            <td>
                <form action="ServletController?method=add_book" method="POST">
                    <input type="submit" class="btn btn-default" value=<fmt:message key="button_add_book"
                                                                                    bundle="${rb}"/>>
                </form>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="label.title_view_books" bundle="${rb}"/></td>
            <td>
                <form action="ServletController?method=remove_book_from_library" method="POST">
                    <input type="submit" class="btn btn-default" value=<fmt:message key="button_view_clients"
                                                                                    bundle="${rb}"/>>
                </form>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="admin_view_clients" bundle="${rb}"/></td>
            <td>
                <form action="ServletController?method=view_clients" method="POST">
                    <input type="submit" class="btn btn-default" value=<fmt:message key="button_view_clients"
                                                                                    bundle="${rb}"/>>
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
