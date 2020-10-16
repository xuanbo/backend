# Java基础知识

> 掌握核心的基础知识

不拘泥于任何学习渠道，选择一个自己喜欢的方式学习。

- [java-tutorial](https://www.javatpoint.com/java-tutorial)
- [Java 教程](https://www.runoob.com/java/java-tutorial.html)

推荐无聊的时候，读一读[《Java编程思想》](http://blog.didispace.com/books/think-in-java/)

## Java核心

> 掌握基本的语法，类、对象、重载、继承、多态等特点。

- 基本语法
- 面向对象

## 集合

> 掌握数组、链表、hash、set等集合内库

- 数组
- List
- Map
- Set

推荐学习开源库 guava。

课后练习：统计某一篇英文文章每个单词的数量。

## IO

> 文件读写，后面开发中基本上不会自己写文件读写，基本都是利用开源库进行文件读写操作。

按照处理数据单位不同分类：

- 字节流 InputStream/OutputStream

- 字符流 Reader/Writer

按照数据流的方向分类：

- 输入流 InputStream/Reader
- 输出流 OutputStream/Writer
- 转换流 InputStreamReader/OutputStreamWriter

课后练习：实现 linux 命令 ls、cat。

## 网络

> 持之以恒的学习，速成可暂时放一放

- Socket
- SocketServer

毕业之前最好了解 BIO、NIO、AIO，推荐学习网络库 netty ，了解 rpc 基本原理（dubbo、motan）。

课后练习：命令行版本聊天室

## 多线程

> 持之以恒的学习，速成可简单看一看

- Thread
- Runnable
- Callable
- 锁
- 线程池

课后练习：多线程实现生产者、消费者模式。

## 范型

> 持之以恒的学习，速成可简单看一看

- 泛型类
- 泛型接口
- 泛型方法

课后练习：参考 List，自定义实现 MyList，实现 add、size、get 等方法。

## 注解

> 持之以恒的学习，速成可简单看一看

- 元注解
- 如何自定义注解
- 如何解析注解

## 反射

> 持之以恒的学习，速成可简单看一看

- Field
- Method
- Class
- Proxy（JDK静态代理）

课后练习：定义 Before 方法注解，标注在方法上后，每次执行该方法时，输出：我被执行啦！

### 类加载

>  持之以恒的学习，速成可简单看一看

- 双亲委托模型
- ClassLoader加载机制
- 自定义ClassLoader

## 注意

在毕业前，如果选择 Java 后端的工作，下面的东西掌握的越深入当然是最好咯：

- 多线程、锁、线程池
- 网络、rpc
- 反射、类加载
- gc 参数、分析 dump 日志
