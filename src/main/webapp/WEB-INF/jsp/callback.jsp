<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Callback" >
    <jsp:attribute name="content">
        <img src="<c:url value="/images/animated-exclaim.gif"/>"/>You've successfully authenticated!<img src="<c:url value="/images/animated-exclaim.gif"/>"/>
    </jsp:attribute>
</layout:default>