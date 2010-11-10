<%@attribute name="content" required="true" fragment="true" %>
<%@attribute name="title" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
    <head>
        <title><c:out value="${title != null ? title : 'StreamSavr'}"/></title>
       <!-- <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>  -->
        <link rel="stylesheet" href="<c:url value="/css/ui.css"/>">
        <link rel="stylesheet" href="<c:url value="/css/ui.progress-bar.css"/>">
         

    </head>
    <body>
        <div id="container">
            <div id="header">
                <a href="<c:url value="/"/>"><img src="<c:url value="/images/question-mark.jpg"/>"/></a>
            </div>
            <div id="content">
                <jsp:invoke fragment="content"/>
            </div>
            <div id="footer">
                Why remember the tweets when you can save them!
            </div>
        </div>
    </body>
</html>
