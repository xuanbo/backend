# Spring Cloud

> 基于 HTTP 的微服务框架

不知从何时起 Spring Cloud 成为了微服务的代名词，不懂 Spring Cloud 都不配玩微服务。

Spring Cloud 全家桶提供一整套企业级微服务解决方案，让 Spring 又又又又火了。

在容器化时代，Spring 或者说 Java 显得有些乏力。

Javaer 该何去何从，学 Go ?

## 核心思想

### 网关

在微服务架构模式下后端服务的实例数一般是动态的，对于客户端而言很难发现动态改变的服务实例的访问地址信息。因此在基于微服务的项目中为了简化前端的调用逻辑，通常会引入 API Gateway 作为轻量级网关，同时 API Gateway 中也会实现相关的认证、授权、限流等逻辑从而简化内部服务之间相互调用的复杂度。

网关模块：

- Spring Cloud Gateway
- Spring Cloud Zuul

### 注册中心

由于服务越来越多，每个服务背后的实例数也动态配置，那么就需要有一个地方来管理这里元数据信息。

- Spring Cloud Eureka
- Spring Cloud Consul

### 配置中心

由于服务越来越多，每个服务背后的实例数也动态配置，如果想修改服务的配置时，工作量非常大，也无法热更新。因此提供一个配置中心来统一管理配置，应用检测到配置更新（或 Event Bus 通知变更）时热刷新配置。

- Spring Cloud Config

### 事件总线

主要配合配置中心来通知集群刷新状态，否则想通知这些应用做变更是挺困难的一件事。

- Spring Cloud Bus

### 服务调用

由于服务越来越多，每个服务背后的实例数也动态配置，那么调用方想调用服务时就很懵逼。因此，需要结合注册中心，服务发现这些实例的内网地址，根据负载均衡算法（随机、权重、轮询等）进行调用。

- Spring Cloud Open Feign
- Spring Cloud Ribbon

### 熔断、降级

在服务调用时，当服务超时、宕机不可用等达到一定的阈值时，需要有机制能重试，或者降级服务（错误时如何处理）。

- Spring Cloud Hystrix

### 链路跟踪

服务较多的时候，特别是调用链非常长（ 如：A -> B -> C -> D ）时，一旦某个环节出现异常，我们很难排查到。而且也不清楚每个环节经历了什么，耗时多久。

- Spring Cloud Sleuth

### 其他

当然，不仅如此。我们还需要解决分布式事务、集群认证安全、集群监控、服务灰度发布、日志收集、分布式定时任务等一系列问题。

除了 Spring Cloud 之外，还有各大公司开源的一些组件来解决这些问题。

## 学习教程

教程 1 :

- [springcloud(二)：注册中心Eureka](https://www.cnblogs.com/ityouknow/p/6854805.html)
- [springcloud(三)：服务提供与调用](https://www.cnblogs.com/ityouknow/p/6859802.html)
- [springcloud(四)：熔断器Hystrix](https://www.cnblogs.com/ityouknow/p/6868833.html)
- [springcloud(十)：服务网关zuul](https://www.cnblogs.com/ityouknow/p/6944096.html)
- [springcloud(六)：配置中心git示例](https://www.cnblogs.com/ityouknow/p/6892584.html)
- [springcloud(九)：配置中心和消息总线（配置中心终结版）](https://www.cnblogs.com/ityouknow/p/6931958.html)

教程 2 :

- [Spring-Cloud教程](http://yangdongdong.org/categories/Spring-Cloud/)

教程 3 :

- [使用Spring Cloud与Docker实战微服务](https://github.com/eacdy/spring-cloud-book)

## 进阶

永远不要局限于框架，学习背后的微服务思想与核心实现。

- [spring-cloud-alibaba](https://spring.io/projects/spring-cloud-alibaba)
