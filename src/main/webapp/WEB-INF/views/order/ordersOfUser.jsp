<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders of User</title>
</head>
<body>
<h1>All your orders</h1>
<table border="1">
    <tr>
        <th>ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/details?id=${order.id}">Details</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
