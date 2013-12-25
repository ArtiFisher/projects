
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <jsp:useBean id="dfg" class="com.fisher.beans.Librarian"/>
  <jsp:setProperty name="dfg" property="librarianId" value="55"/>
  <jsp:getProperty name="dfg" property="librarianId"/>
     something
  </body>
</html>