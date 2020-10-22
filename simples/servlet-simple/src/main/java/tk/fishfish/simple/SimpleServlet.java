package tk.fishfish.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接口
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@WebServlet("/v1/simple")
public class SimpleServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("执行");
        resp.getWriter().println("GET: " + req.getRequestURI());
        resp.getWriter().println("QueryString: " + req.getQueryString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("执行");
        resp.getWriter().println("POST: " + req.getRequestURI());
        resp.getWriter().println("QueryString: " + req.getQueryString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("执行");
        resp.getWriter().println("PUT: " + req.getRequestURI());
        resp.getWriter().println("QueryString: " + req.getQueryString());
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("执行");
        resp.getWriter().println("DELETE: " + req.getRequestURI());
        resp.getWriter().println("QueryString: " + req.getQueryString());
    }

}
