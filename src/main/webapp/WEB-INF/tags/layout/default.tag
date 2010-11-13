<%@attribute name="content" required="true" fragment="true" %>
<%@attribute name="title" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
<head>
    <title><c:out value="${title != null ? title : 'StreamSavr'}"/></title>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/> 
    <%--<link rel="stylesheet" href="<c:url value="/css/ui.css"/>"> --%>
    <%-- <link rel="stylesheet" href="<c:url value="/css/ui.progress-bar.css"/>"> --%>

    <!-- YAHOO Global Object source file -->
    <script type="text/javascript" src="http://yui.yahooapis.com/2.8.2r1/build/yahoo/yahoo-min.js"></script>

    <!-- Used for Custom Events and event listener bindings -->
    <script src="http://yui.yahooapis.com/2.8.2r1/build/event/event-min.js"></script>
    <script src="http://yui.yahooapis.com/2.8.2r1/build/connection/connection_core-min.js"></script>
    <script src="http://yui.yahooapis.com/2.8.2r1/build/json/json-min.js"></script>
    <script src="http://yui.yahooapis.com/2.8.2r1/build/connection/connection-min.js"></script>


</head>
<body>
<div id="container">
    <div id="header">
        <div style="font-size:30pt; font-weight:900">StreamSavr</div>
        <div style="float:right; position:relative; top:-35px; left:-5px">
            <c:if test="${userName != null}">
                Welcome, <c:out value="${userName}"></c:out>
            </c:if>
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
