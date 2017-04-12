<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 22.03.2017
  Time: 3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" type="image/x-icon" href="images/favicon.png"/>
    <link href="css/master.css" rel="stylesheet">
</head>
<body >
<div class="comments">
<c:forEach var="comment" items="${requestScope.commentsList}">
    <div class="b-detail__main-aside-about-form-links">
        <a href="#" data-carId="${comment.car.getId()}" data-id="${comment.id}" onclick="onClickComment(this)" class="fa fa-times" aria-hidden="true" style="color: darkred"></a>
        <a href="#" class="j-tab m-active s-lineDownCenter" data-to="#info1">${comment.user.login}</a>
    </div>
    <div>
        <p>${comment.comment}</p>
    </div>
</c:forEach>
</div>
<script type="text/javascript">
    function onClickComment(_this) {
        var value = $(_this).data("id");
        var carId = $(_this).data("carId");
        $.ajax({
            method: "POST",
            url: "/autobazar/controller",
            data: {'id': value, "carId": carId, "command": "DeleteComment"},
            success: function (result) {
                $(".comments").html(result);
            }
        });
    }
</script>
</body>
</html>
