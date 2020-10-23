package tk.fishfish.simple.framework;

import java.util.Map;

/**
 * 路由
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public interface Router {

    /**
     * 注册GET请求处理
     *
     * @param path    路径
     * @param handler Handler
     * @return 路由
     */
    Router get(String path, Handler handler);

    /**
     * 注册POST请求处理
     *
     * @param path    路径
     * @param handler Handler
     * @return 路由
     */
    Router post(String path, Handler handler);

    /**
     * 注册PUT请求处理
     *
     * @param path    路径
     * @param handler Handler
     * @return 路由
     */
    Router put(String path, Handler handler);

    /**
     * 注册DELETE请求处理
     *
     * @param path    路径
     * @param handler Handler
     * @return 路由
     */
    Router delete(String path, Handler handler);

    /**
     * 匹配该路径的处理器
     *
     * @param path         请求路径
     * @param method       请求方式
     * @param namingParams 命名参数
     * @return 处理器
     */
    Handler match(String path, String method, Map<String, Object> namingParams);

}
