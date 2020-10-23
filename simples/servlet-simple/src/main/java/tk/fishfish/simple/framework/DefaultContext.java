package tk.fishfish.simple.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 默认实现
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class DefaultContext implements Context {

    /**
     * 请求
     */
    private final HttpServletRequest req;

    /**
     * 响应
     */
    private final HttpServletResponse resp;

    /**
     * 命名参数
     */
    private final Map<String, Object> namingParams;

    public DefaultContext(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> namingParams) {
        this.req = req;
        this.resp = resp;
        this.namingParams = namingParams;
    }

    @Override
    public Object path(String name) {
        return namingParams.get(name);
    }

    @Override
    public HttpServletRequest req() {
        return req;
    }

    @Override
    public HttpServletResponse resp() {
        return resp;
    }

}
