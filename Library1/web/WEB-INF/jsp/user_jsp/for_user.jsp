<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/><!--en_US-->
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_user_page" bundle="${rb}"/></title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.css">
</head>
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
<body>
<div class="container" align="center">
    <h1 class="text-center"><fmt:message key="label.page_name_user_page" bundle="${rb}"/></h1>
    <table>
        <tr>
            <td><fmt:message key="label.page_name_take_book" bundle="${rb}"/></td>
            <td>
                <form action="ServletController?method=take_book" method="POST">
                    <input type="submit" class="btn btn-default" value=<fmt:message key="button_user_view"
                                                                                    bundle="${rb}"/>>
                </form>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="title.view_list_of_books" bundle="${rb}"/></td>
            <td>
                <form action="ServletController?method=return_book" method="POST">
                    <input type="submit" class="btn btn-default" value=<fmt:message key="button_user_view"
                                                                                    bundle="${rb}"/>>
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
