<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Archiver" >
    <jsp:attribute name="content">
        <h1>Under construction...</h1>
        <br>This is where we will start archiving the tweets.
        <h1>Hits Remaining: <c:out value="${hitsRemaining}"/></h1>
    </jsp:attribute>
</layout:default>