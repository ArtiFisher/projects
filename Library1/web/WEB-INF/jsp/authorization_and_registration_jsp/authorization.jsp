<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="ru_RU" scope="session"/>
<c:if test="${flag==1}">
    <fmt:setLocale value="${loc}" scope="session"/>
</c:if>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.css">
    <%--<link rel="stylesheet" type="text/css" href="../../bootstrap-3.0.3-dist/css/bootstrap.css" />--%>
</head>
<body>
<table align="left">
    <form action="ServletController?method=registration" method="POST">
        <input type="submit" class="btn btn-default" value=<fmt:message key="field.registration"
                                                                        bundle="${rb}"/>>
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

    <h2 class="text-center"><fmt:message key="label.title" bundle="${rb}"/></h2>
    <c:out value="${successfulRegistration}"/>
    <c:out value="${error}"/>

    <form class="form-signin" role="form" action="ServletController?method=authorization" method="POST">
        <input type="text" name="login" style="width: 40%" class="form-control" placeholder=
        <fmt:message key="field.login" bundle="${rb}"/> required autofocus>
        <input type="password" name="password" style="width: 40%" class="form-control" placeholder=
        <fmt:message key="field.password" bundle="${rb}"/> required>
        <input type="submit" class="btn btn-default" value=<fmt:message key="field.log_in" bundle="${rb}"/>>
    </form>


</div>
</body>
</html>

