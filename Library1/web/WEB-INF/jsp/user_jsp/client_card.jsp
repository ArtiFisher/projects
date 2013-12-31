<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        <h1>Client card</h1>
        <table border="1">            
            <td>ISBN</td>
            <td>Title</td>
            <td>Author</td>
            <td>Year</td>            
            <c:forEach var="book" items="${books}">
                <tr>                    
                    <td>${book.ISBN}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.year}</td>                    
                </tr>
            </c:forEach>
        </table>
        <form action="ServletController?method=return_to_user_page" method="POST">
            <input type="submit" value="Return to my page">
        </form>
        <form action="ServletController?method=log_out" method="POST">
            <input type="submit" value="Log out">
        </form>


    </body>
</html>
