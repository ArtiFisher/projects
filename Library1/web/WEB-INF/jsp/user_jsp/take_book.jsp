<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${loc}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title_take_book" bundle="${rb}"/></title>
    <link rel="stylesheet" href="dist/css/bootstrap.css"/>
</head>
<script type="text/javascript">
    function Pager(tableName, itemsPerPage) {
        this.tableName = tableName;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 1;
        this.pages = 0;
        this.inited = false;

        this.showRecords = function (from, to) {
            var rows = document.getElementById(tableName).rows;
            // i starts from 1 to skip table header row
            for (var i = 1; i < rows.length; i++) {
                if (i < from || i > to)
                    rows[i].style.display = 'none';
                else
                    rows[i].style.display = '';
            }
        }

        this.showPage = function (pageNumber) {
            if (!this.inited) {
                alert("not inited");
                return;
            }
            this.currentPage = pageNumber;
            var from = (pageNumber - 1) * itemsPerPage + 1;
            var to = from + itemsPerPage - 1;
            this.showRecords(from, to);
        }

        this.prev = function () {
            if (this.currentPage > 1)
                this.showPage(this.currentPage - 1);
        }

        this.next = function () {
            if (this.currentPage < this.pages) {
                this.showPage(this.currentPage + 1);
            }
        }

        this.init = function () {
            var rows = document.getElementById(tableName).rows;
            var records = (rows.length - 1);
            this.pages = Math.ceil(records / itemsPerPage);
            this.inited = true;
        }

        this.showPageNav = function (pagerName, positionId) {
            if (!this.inited) {
                alert("not inited");
                return;
            }
            var element = document.getElementById(positionId);

            var pagerHtml = '<button class="btn btn-default" onclick="' + pagerName + '.prev();" > « Prev </button> | ';
            for (var page = 1; page <= this.pages; page++)
                pagerHtml += '<button id="pg' + page + '" onclick="' + pagerName + '.showPage(' + page + ');" class="btn btn-default">' + page + ' </button> | ';
            pagerHtml += '<button class="btn btn-default" onclick="' + pagerName + '.next();" > Next »</button>';

            element.innerHTML = pagerHtml;
        }
    }
</script>
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
    <h1><fmt:message key="label.page_name_take_book" bundle="${rb}"/></h1>
    <c:if test="${bookNumber > 0}">

        <font label.title_take_book size=2 color=red>
            <c:out value="${error}"/>
        </font>

        <font size=2 color=red>
            <c:out value="${error2}"/>
        </font>

        <div id="pageNavPosition"></div>
        <form action="ServletController?method=search" method="POST">
            <table>
                <td>
                    <input type="text" name="search" class="form-control" style="width: 100%">
                </td>
                <td>
                    <input class="btn btn-default" type="submit" value=<fmt:message key="search" bundle="${rb}"/>>
                </td>
            </table>
        </form>
        <table border="1" id="results">
            <td><fmt:message key="book_ISBN" bundle="${rb}"/></td>
            <td><fmt:message key="book_title" bundle="${rb}"/></td>
            <td><fmt:message key="book_author" bundle="${rb}"/></td>
            <td><fmt:message key="book_year" bundle="${rb}"/></td>
            <td><fmt:message key="book_numberOfCopies" bundle="${rb}"/></td>
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
                                <input class="btn btn-default" type="submit" value=<fmt:message key="button_take_book"
                                                                                                bundle="${rb}"/>>
                            </td>
                        </c:if>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${bookNumber <= 0}">
        <h2><fmt:message key="msgNoBooks" bundle="${rb}"/></h2>

        <form action="ServletController?method=search" method="POST">
            <table>
                <td>
                    <input type="text" name="search" class="form-control" style="width: 100%">
                </td>
                <td>
                    <input class="btn btn-default" type="submit" value=<fmt:message key="search" bundle="${rb}"/>>
                </td>
            </table>
        </form>
    </c:if>
    <form action="ServletController?method=return_to_user_page" method="POST">
        <input class="btn btn-default" type="submit" value=<fmt:message key="return_to_user_page" bundle="${rb}"/>>
    </form>
</div>

<script type="text/javascript"><!--
var pager = new Pager('results', 3);
pager.init();
pager.showPageNav('pager', 'pageNavPosition');
pager.showPage(1);
//--></script>
</body>
</html>
