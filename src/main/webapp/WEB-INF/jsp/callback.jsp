<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>


<layout:default title="StreamSavr" >
    <jsp:attribute name="content">
        <h1>Hits Remaining: <c:out value="${hitsRemaining}"/></h1>
        <h1>Username: <c:out value="${userName}"/></h1>
        <h1>Total Tweets: <c:out value="${totalTweets}"/></h1>
        <a id="archive_link" href="/status">Archive!</a>
      </jsp:attribute>
</layout:default>

