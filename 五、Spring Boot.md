# Spring Boot

> 14 年成为 Spring 顶级项目，约定大于配置。

Java Web 开发期需要整个很多框架，配置非常繁琐，大部分开发人员都难以搭建起来。据不完全统计，一个新项目初始化配置工作就需要 1 周以上。

当 Spring Boot 出现之后，基本上可以分分钟搭建项目。（不过，定制化开发就比较吃力咯）

从 16 年大三下学期开始，我就接触了 Spring Boot 开发框架。17 年之后就再也没有搭建过 SSM（以 XML 为主要配置的 Spring + Spring MVC + Mybatis ） 项目，基本上都忘记那些配置了。

## 快速开始

> Spring Boot 是一个快速开发框架，只要按照约定开发 Starter 之后，集成工作由 Spring Boot 自动组装。

### 依赖

```xml
<!-- web依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>${spring.boot.version}</version>
</dependency>
```

### 插件

```xml
<plugins>
    <!-- java 1.8编译，推荐 -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.0</version>
        <configuration>
            <source>1.8</source>
            <target>1.8</target>
        </configuration>
    </plugin>

    <!-- spring boot插件 -->
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>${spring.boot.version}</version>
        <executions>
            <execution>
                <goals>
                    <goal>repackage</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

### 项目结构

官方推荐包结构：

```
com
 +- example
     +- myproject
         +- Application.java                  # 启动类
         |
         +- config                            # 自定义配置
         |   +- MyConfig.java
         |
         +- domain                            # 数据库操作
         |   +- Customer.java
         |   +- CustomerRepository.java
         |
         +- service                           # 业务逻辑
         |   +- CustomerService.java
         |
         +- web                               # WEB
             +- CustomerController.java
```

Application 为启动类，推荐在包的外层，这样可以保证到子包中的 Bean 可以被默认扫描到

### 入口类

入口类采用注解 @SpringBootApplication 标注，会自动配置扫包，初始化一个基本的 Web 开发环境。

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### 自定义配置

自定义配置一般推荐 java config 配置，用 @Configuration 标注类。

```java
@Configuration
public class MyConfig {

    /**
     * 配置RestTemplate，默认实现为JDK的URLConnection
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```

这样就配置了一个 Bean，与 xml 中 `<bean class="org.springframework.web.client.RestTemplat"/>` 等同

### 运行

一般直接在 IDE 中运行 Application.java 中的 main 方法启动。

## 学习教程

上面开始一个最基础的 Web 例子，但是还远远不够，我们还需要数据库、缓存、安全等组件集成。

因此，接下来的工作就需要学习常用的 spring-boot-starter 组件的集成。

- [构建微服务：Spring boot 入门篇](https://www.cnblogs.com/ityouknow/p/5662753.html)
- [Spring Boot(二)：Web 综合开发](https://www.cnblogs.com/ityouknow/p/5730412.html)
- [文件上传/下载](https://github.com/xuanbo/easy-java#%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD)
- [Spring Boot(六)：如何优雅的使用 Mybatis](https://www.cnblogs.com/ityouknow/p/6037431.html)
- [Spring Boot(三)：Spring Boot 中 Redis 的使用](https://www.cnblogs.com/ityouknow/p/5748830.html)
- [Spring Boot(九)：定时任务](https://www.cnblogs.com/ityouknow/p/6132645.html)
- [Spring Boot (十)：邮件服务](https://www.cnblogs.com/ityouknow/p/6132645.html)

## 进阶

自定义 Starter 来完成自己的快速启动组件。

- [SpringBoot应用篇（一）：自定义starter](https://www.cnblogs.com/hello-shf/p/10864977.html)

## 练习

基于 Spring Boot 集成 Mybatis 完成一个对用户表的增删改查例子。
