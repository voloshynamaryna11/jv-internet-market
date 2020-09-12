<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Enter your user details</h1>

<h4 style="color:crimson">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/user/registration">
    Enter your name <input type="text" name="name">
    Enter your login <input type="text" name="login" required>
    Enter your password <input type="password" name="password" required>
    Enter your password again <input type="password" name="password-repeat" required>

    <button type="submit">Ready!</button>
</form>
</body>
</html>
