<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>


<layout:default title="StreamSavr" >
    <jsp:attribute name="content">
        <h3>Hits Remaining: <c:out value="${hitsRemaining}"/></h3>
        <h3>Username: <c:out value="${userName}"/></h3>
        <h3>Total Tweets: <c:out value="${totalTweets}"/></h3>
        <button onClick="window.location='/status'">Archive!</button>
        <%--<a id="archive_link" href="/status">Archive!</a>--%>
      </jsp:attribute>
</layout:default>

