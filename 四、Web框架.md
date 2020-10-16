# Web框架

> Servlet、JDBC 编程太累，为了快速开发，轮子一个接着一个～

## 注意

**建议有空把每个组件从零搭建起来，写一写单元测试。**

**如果为了快速入门，可以直接学习 Spring Boot 相关，跳过该篇。**

也许，国内最早的 JAVA 架构师是为了整合各大框架的吧？

推荐无聊的时候，读一读：

- [《Spring实战（第4版）》](http://www.java1234.com/a/javabook/javaweb/2016/1102/7020.html)

## Spring

> Spring 是一个大家庭，这里的 Spring 主要指其核心，即 IOC、AOP 部分。

- [IOC](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html)（控制反转，依赖注入。说白了就是把对象的创建、销毁等生命周期交给 Spring 容器来管理，从而让开发者专注于业务逻辑开发。）
- [AOP](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop)（动态代理。说白了就是代理目标对象，进行增强）

## Spring MVC

> 封装 Servlet 快速开发 API

- 了解 @Controller 等 MVC 相关注解
- 参数映射
- 过滤器

## Spring Jdbc

> 封装 JDBC 提供操作数据库操作模板

- JdbcTemplate操作
- 事务

## MyBatis

> 国内最流行的 ORM 框架

- 基本使用
- 动态标签
- 动态SQL
- 拦截器

博客推荐：

- [Mybatis教程-实战看这一篇就够了](https://www.cnblogs.com/diffx/p/10611082.html)

## Jedis

> Redis Java 操作库

- Redis 五大数据结构基本使用
- 分布式锁
- 限流
- 发布订阅

## Spring Data Redis

> 封装 Redis 提供操作数据库操作模板

- RedisTemplate 操作

## Spring Data JPA

> 基于 Hibernate 提供 ORM 操作，国内用的较少，主要是封装程度太 高，非常难以掌握，而在查询灵活性方面不及 MyBatis。

- 增删改查
- 分页注解
