<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_reg" bundle="${rb}"/></title>
    <link rel="stylesheet" href="dist/css/bootstrap.css"/>

</head>
<body>
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

<div class="container">
    <h1 align="center"><fmt:message key="label.page_name_reg" bundle="${rb}"/></h1>

    <form action="ServletController?method=register" method="POST">
        <fmt:message key="field.name" bundle="${rb}"/>
        <input type="text" class="form-control" name="name">

        <fmt:message key="field.surname" bundle="${rb}"/>
        <input type="text" class="form-control" name="surname">

        <fmt:message key="field.login" bundle="${rb}"/>
        <input type="text" class="form-control" name="login">
        <font size=2 color=red>
            <c:out value="${errorLogin}"/>
        </font>
        <fmt:message key="field.password" bundle="${rb}"/>
        <input type="password" class="form-control" name="password">
        <font size=2 color=red>
            <c:out value="${errorPassword}"/>
        </font>


        <input type="submit" class="btn btn-default" value=<fmt:message key="field.button_reg" bundle="${rb}"/>>
    </form>

    <form action="ServletController?method=log_out" method="POST">
        <input type="submit" class="btn btn-default" value=<fmt:message key="field.button_to_main_page"
                                                                        bundle="${rb}"/>>
    </form>
</div>
</body>
</html>
