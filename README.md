**"jv-internet-market"** 
=====================

***1. Project goal***
-----------------------------------
The goal of the project is to create a template for an online store, to recreate its main functionality using technologies such as jdbc, servlets, filters, jsp,
jstl and java.security.
 
***2. Project structure:***
-----------------------------------
*all sources are in the "internet.market" package
*all controllers are responsible for handling requests are in the package "controllers"
*the "dao" package contains the classes responsible for the implementation of interaction with the database and interfaces that describe their work
*all custom exceptions needed to identify the application failure are in the "exceptions" package
*the "lib" package contains the injector implementation and its own annotations
*the "model" package is responsible for recreating and describing entities whose data is stored in the database
*the "security" package contains authentication implementation
*the "service" package contains the main business logic of the application including classes with implementation of the main methods and interfaces that describe classes' work
*the "util" package contains two classes responsible for connecting to the database "ConnectionUtil" and for hashing users' passwords "HashUtil"
*the "web.filters" package include implementation of filters for authentication and authorization
*the "resources" folder contains sql.file for the initial creating of tables in the database 
*the "webapp" folder contains jsp.files and configuration file "web.xml"

***3. Implementation details:***
-----------------------------------
The project implements authorization, authentication, hashing passwords using the SHA-512 algorithm, dividing user roles into buyers and administrators, 
and corresponding functionality for each role (using filters). MySQL was used as a database, com.mysql.cj.jdbc.Driver - driver to connect to it. 
The protocol for transferring data between the client and the server are http, so all controllers extend the functionality of HttpServlet. 
Apache Tomcat 9.0.37 was used as a web server and servlet container (2 in 1). The code was organized in such a way as to comply with the SOLID principles and,
accordingly, for the implementation of Dependency Inversion, we created our own annotations and a custom injector, which, using reflection, 
supported the work of Dependency Injection. And for convenient display of information on the client side, the jsp template engine and the jstl library were used
to display dynamic data in it.

***4. Launch guide:***
-----------------------------------
*Clone this project 
*Install and configure apache Tomcat and MySQL(or another rdbms) 
*Fix the information about connection to database in "util" package "ConnectionUtil" class(user, password and driver if you use another database)
*Run code in init_db.sql to create the needed tables and schema 

***5. Functionality:***
-----------------------------------
general capabilities:
*registration ("/user/registration")
*adding new product to the product-list ("/product/add")
*watching all products' plethora("/product/all")
*injecting start data to the db ("/injectData")
*logout from the system ("/user/logout")
*login ("/user/login")
*watching the details of selected order ("/order/details")

user capabilities:
-watching the list of his selected products ("/cart/product/all") 
-adding product to the shopping cart ("/cart/product/add")
-completing of order ("/orders/complete")
-deleting selected products from the cart ("/cart/products/delete")
-watching the list of user's own list of orders ("/user/orders")

admin capabilities:
-watching all users list ("/user/all")
-deleting users ("/user/delete")
-watching the list of all users' orders ("/admin/orders")
-deleting orders from the previous list ("/admin/orders/delete")
-watching products' list with capability to delete some products ("/admin/products")
-deleting products from the previous list ("/admin/products/delete")

***6. Author:*** [Maryna Voloshyna](https://github.com/voloshynamaryna11)
-----------------------------------
