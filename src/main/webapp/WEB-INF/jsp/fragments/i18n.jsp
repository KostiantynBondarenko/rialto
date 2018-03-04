<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    var i18n = [];
    <c:forEach var='key' items='<%=new String[]{
            "file.select", "file.add", "file.format.err", "file.add.err", "file.size.err"}%>'>
        i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>