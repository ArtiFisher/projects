<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Start page</h1>
        <FORM action="serv" method="POST">
            <INPUT type="submit" value="Button">
        </FORM>
        <jsp:forward page="WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp"/>.
    </body>
</html>