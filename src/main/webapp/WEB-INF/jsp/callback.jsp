<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Callback" >
    <jsp:attribute name="content">
        <img src="<c:url value="/images/animated-exclaim.gif"/>"/><a href="/archiver">Archive your tweets!</a><img src="<c:url value="/images/animated-exclaim.gif"/>"/>
        <br><b>Hits Remaining: <c:out value="${hitsRemaining}"/></b>
    </jsp:attribute>
</layout:default>