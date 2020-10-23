package tk.fishfish.simple.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 上下文
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public interface Context {

    /**
     * 获取路径命名参数值
     *
     * @param name 命名参数
     * @return 参数值
     */
    Object path(String name);

    /**
     * 请求
     *
     * @return HttpServletRequest
     */
    HttpServletRequest req();

    /**
     * 响应
     *
     * @return HttpServletResponse
     */
    HttpServletResponse resp();

}
