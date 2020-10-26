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

        // 匹配 GET /v1/city
        router.get("/v1/city", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("GET " + req.getRequestURI());
        });
        // 匹配 POST /v1/city
        router.post("/v1/city", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("POST " + req.getRequestURI());
        });

        // 匹配 GET /v1/xxx
        router.get("/v1/:name", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("GET " + req.getRequestURI());
            // 获取到 xxx 的值
            resp.getWriter().println("name " + ctx.path("name"));
        });

        // 匹配 GET /v1/xxx/yyy
        router.get("/v1/:name/:some", ctx -> {
            HttpServletRequest req = ctx.req();
            HttpServletResponse resp = ctx.resp();
            resp.getWriter().println("GET " + req.getRequestURI());
            resp.getWriter().println("name " + ctx.path("name"));
            resp.getWriter().println("some " + ctx.path("some"));
        });
    }

}
