<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Add product details</h1>

<h4 style="color:crimson">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/product/add">
    Enter product name <input type="text" name="name" required>
    Enter product price <input type="text" name="price" required>

    <button type="submit">Done!</button>
</form>
</body>
</html>
