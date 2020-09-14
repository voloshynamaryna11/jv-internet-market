<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h1>Enter your user details</h1>

<h4 style="color:crimson">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/user/login">
    Enter your login <input type="text" name="login" required>
    Enter your password <input type="password" name="password" required>

    <button type="submit">Log in!</button>
</form>
</body>
</html>
