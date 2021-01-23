# servlet-simple

> servlet 示例（注解）

## 启动

在本目录下启动 jetty web 容器：

```shell
mvn jetty:run
```

浏览访问 http://localhost:8080/v1/simple?a=b 即可。

快快利用原生的 servlet 规范来写一个 API 服务吧～，它可是 mvc 框架的底层核心。

## 注意

提供了一个简单的请求派发框架，可以去完善它，让它更强大（支持参数提取、文件上传等）

```java
package tk.fishfish.simple;

import tk.fishfish.simple.framework.Router;
import tk.fishfish.simple.framework.RouterDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义路由
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class SimpleRouterDefinition implements RouterDefinition {

    @Override
    public void registration(Router router) {
        //  注册路由
        
        // 匹配 /v1/city
        router.get("/v1/city", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("GET " + req.getRequestURI());
        });
        
        // 匹配 /v1/xxx
        router.get("/v1/book/:name", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("GET " + req.getRequestURI());
            // 获取到 xxx 的值
            resp.getWriter().println("name " + ctx.path("name"));
        });
        
        // 匹配 /v1/xxx/yyy
        router.get("/v1/:name/:some", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("GET " + req.getRequestURI());
            resp.getWriter().println("name " + ctx.path("name"));
            resp.getWriter().println("some " + ctx.path("some"));
        });
    }

}

```
