package tk.fishfish.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@WebFilter("/*")
public class SimpleFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("请求前...");
        chain.doFilter(request, response);
        logger.info("请求后...");
    }

    @Override
    public void destroy() {
        logger.info("销毁");
    }

}
