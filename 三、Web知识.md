# Web知识

> 即编写服务端程序，操作数据库、缓存，结合各种中间件编写业务逻辑，提供API

## Servlet

> 服务端程序规范，需要运行在 Web 容器中，比如：Tomcat、Jetty

- Servlet
- Filter
- Listener
- HTTP协议
- HTML基本标签
- JSON格式
- JSP（了解）

课后练习：实现简单的 Web 程序，部署到 Tomcat 容器，通过浏览器可访问某个目录下的图片。

## JDBC

> 数据库连接、增删改查等数据库操作规范，需要由不同的数据库厂商（比如MySQL、Oracle）实现该规范，即数据库驱动。

- [JDBC 使用说明](https://www.runoob.com/w3cnote/jdbc-use-guide.html)
- 数据库连接池
- 通过反射将结果映射成 Java 对象，即简易 ORM 功能。

课后练习：实现一个增删改查操作 MySQL 数据的例子。

## 注意

由于框架的出现，越来越多的人不注重底层规范以及实现原理，结果工作中一旦遇到问题毫无头绪，无法更进一步。如果选择 Java 后端的工作，Servlet、JDBC 编程一定要掌握，虽然工作中不可能造轮子，但是终生受益。
