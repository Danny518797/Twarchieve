<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="StreamSavr Auth Request" >
    <jsp:attribute name="content">
        <p>To use StreamSavr, you must authorize the application access your Twitter account. Click the link below to do this.</p>
            <li>Click <a href="<c:out value="${authUrl}"/>">here</a> to authorize StreamSavr.</li>

    </jsp:attribute>
</layout:default>