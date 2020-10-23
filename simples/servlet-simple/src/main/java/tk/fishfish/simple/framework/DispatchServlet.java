package tk.fishfish.simple.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 请求派发
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@WebServlet("/")
public class DispatchServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(DispatchServlet.class);

    private Router router;

    @Override
    public void init() {
        router = DefaultRouter.INSTANCE;
        // SPI机制加载路由
        ServiceLoader<RouterDefinition> definitions = ServiceLoader.load(RouterDefinition.class);
        definitions.forEach(definition -> definition.registration(router));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("{} - {}", req.getMethod(), req.getRequestURI());
        Map<String, Object> namingParams = new HashMap<>(8);
        Handler handler = router.match(req.getRequestURI(), req.getMethod(), namingParams);
        if (handler == null) {
            // 404
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // 处理请求
            handler.handle(new DefaultContext(req, resp, namingParams));
        }
    }

}
