    <%@ page contentType="text/html;charset=UTF-8" %>
        <html>
        <head>
           <title>Title</title>
        </head>
        <body>
        <h1>Guitar store Smelly Cat</h1>
        <h3>User Panel</h3>
        <a href="${pageContext.request.contextPath}/injectData">Inject test user data into the DB</a>
        <a href="${pageContext.request.contextPath}/user/registration">Registration</a>
        <a href="${pageContext.request.contextPath}/product/add">Add product</a>
        <a href="${pageContext.request.contextPath}/product/all">See all products</a>
        <a href="${pageContext.request.contextPath}/user/all">See all users</a>

        <h3>Special opportunities(only for admin)</h3>
        <a href="${pageContext.request.contextPath}/admin/order/all">All orders</a>
        <a href="${pageContext.request.contextPath}/admin/product/all">All products</a>
        </body>
        </html>
