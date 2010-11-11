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

    <!-- YAHOO Global Object source file -->
    <script type="text/javascript" src="http://yui.yahooapis.com/2.8.2r1/build/yahoo/yahoo-min.js"></script>

    <!-- Used for Custom Events and event listener bindings -->
    <script src="http://yui.yahooapis.com/2.8.2r1/build/event/event-min.js"></script>

    <!-- Source file -->
    <!--
        If you require only basic HTTP transaction support, use the
        connection_core.js file.
    -->
    <script src="http://yui.yahooapis.com/2.8.2r1/build/connection/connection_core-min.js"></script>
    <!-- Source file -->
    <script src="http://yui.yahooapis.com/2.8.2r1/build/json/json-min.js"></script>
     

    <!--
        Use the full connection.js if you require the following features:
        - Form serialization.
        - File Upload using the iframe transport.
        - Cross-domain(XDR) transactions.
    -->
    <script src="http://yui.yahooapis.com/2.8.2r1/build/connection/connection-min.js"></script>

    <script type="text/javascript">
    
    </script>


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
