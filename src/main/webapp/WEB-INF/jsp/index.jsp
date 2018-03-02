<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <p/>

        <div class="container">
            <label class="btn btn-default btn-file">
                <input id="file" type="file" name="file">
            </label>
            <input type="button" onclick="uploudFile()" value="отправить" class="btn btn-info"/>
        </div>
        <%--<form method="post" action="addAds" enctype="multipart/form-data">--%>

            <%--<label class="btn btn-default btn-file">--%>
                <%--<input id="file" type="file" name="file">--%>
            <%--</label>--%>
            <%--<button type="submit" class="btn btn-info">отправить</button>--%>
        <%--</form>--%>
        <%--<form method="post" action="users">--%>
            <%--<spring:message code="app.login"/>: <select name="userId">--%>
            <%--<option value="100000" selected>User</option>--%>
            <%--<option value="100001">Admin</option>--%>
        <%--</select>--%>
            <%--<button type="submit"><spring:message code="common.select"/></button>--%>
        <%--</form>--%>
        <%--<ul>--%>
            <%--<li><a href="users"><spring:message code="user.title"/></a></li>--%>
        <%--</ul>--%>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script>
    function uploudFile() {
        var file = $("#file");
        var fileVal = file.val();
        if (fileVal != null && fileVal != "") {
            var fileFormat = fileVal.split('.')[1];
            if (fileFormat != "xls" && fileFormat != "xlsx") {
                alert("Был передан не zip архив");
                return;
            }

            var fd = new FormData;
            fd.append('file', file.prop('files')[0]);

            $.ajax({
                url: '/rialto/addAds',
                type: 'POST',
                // dataType: "json",
                cache: false,
                contentType: false,
                processData: false,
                data: fd,
                success: function () {
                    alert("Операция поставлена в очередь на место");
                },
                error: function () {
                    alert("2Операция поставлена в очередь на место");
                },
            });
        } else {
            alert('Файл не выбран');
        }
    }
</script>
</html>