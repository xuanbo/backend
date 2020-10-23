package tk.fishfish.simple.framework;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 请求处理
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public interface Handler {

    /**
     * 请求处理
     *
     * @param ctx 上下文
     * @throws IOException      if an input or output error is  detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     */
    void handle(Context ctx) throws ServletException, IOException;

}
