<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="" class="navbar-brand"><spring:message code="app.title"/></a>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <%--<a class="btn btn-info" href="ads"><spring:message code="ads.editor"/></a>--%>
                    <%--<form:form class="navbar-form" action="logout" method="post">--%>
                        <%--<a class="btn btn-info" href="profile"><spring:message code="app.profile"/></a>--%>
                        <%--<button class="btn btn-primary" type="submit">--%>
                            <%--<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>--%>
                        <%--</button>--%>
                    <%--</form:form>--%>
                </li>
                <%--<jsp:include page="lang.jsp"/>--%>
            </ul>
        </div>
    </div>
</div>