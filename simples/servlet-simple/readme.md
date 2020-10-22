# jdbc-simple

> jdbc操作数据库

## 说明

通过这个例子，你可以掌握 jdbc 编程的模板化代码以及范型、反射的相关知识，它可是
ORM 框架的基础。

## 功能

参见如下的接口设计：

```java
public interface JdbcTemplate {

    /**
     * 查询单个值
     *
     * @param sql   SQL语句
     * @param clazz 类型
     * @param args  预编译参数
     * @param <T>   类型
     * @return 值
     * @throws SQLException SQL执行异常
     */
    <T> T queryForObject(String sql, Class<T> clazz, Object... args) throws SQLException;

    /**
     * 将一行结果转为map，如果有多行只查询一行
     *
     * @param sql  SQL语句
     * @param args 预编译参数
     * @return 值
     * @throws SQLException SQL执行异常
     */
    Map<String, Object> queryForMap(String sql, Object... args) throws SQLException;

    /**
     * 查询结果
     *
     * @param sql  SQL语句
     * @param args 预编译参数
     * @return 值
     * @throws SQLException SQL执行异常
     */
    List<Map<String, Object>> queryForList(String sql, Object... args) throws SQLException;

    /**
     * 更新
     *
     * @param sql  SQL语句
     * @param args 预编译参数
     * @return 值
     * @throws SQLException SQL执行异常
     */
    int update(String sql, Object... args) throws SQLException;

}
```

## 使用

创建 DefaultJdbcTemplate 对象，进行数据库操作。

```java
package tk.fishfish.simple;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 测试 {@link JdbcTemplate}
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class JdbcTemplateTest {

    private final Logger logger = LoggerFactory.getLogger(JdbcTemplateTest.class);

    private HikariDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/spring_boot_simple?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        config.setUsername("root");
        config.setPassword("123456");
        config.setMinimumIdle(8);
        config.setMaximumPoolSize(32);
        // MySQL数据库编码设置为utf8mb4
        config.addDataSourceProperty("connectionInitSql", "set names utf8mb4;");
        // MySQL推荐配置
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);
        // 数据源
        dataSource = new HikariDataSource(config);
        // 默认实现
        jdbcTemplate = new DefaultJdbcTemplate(dataSource);
    }

    /**
     * 单个结果映射
     */
    @Test
    public void queryForObject() throws SQLException {
        String name = jdbcTemplate.queryForObject("SELECT name FROM city", String.class);
        logger.info("name: {}", name);

        Long id = jdbcTemplate.queryForObject("SELECT id FROM city", Long.class);
        logger.info("id: {}", id);
    }

    /**
     * 一行映射为map
     */
    @Test
    public void queryForMap() throws SQLException {
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * FROM city");
        logger.info("result: {}", result);
    }

    /**
     * 多行结果映射
     */
    @Test
    public void queryForList() throws SQLException {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM city");
        logger.info("result: {}", result);
    }

    /**
     * 更新操作
     */
    @Test
    public void update() throws SQLException {
        int update = jdbcTemplate.update("INSERT INTO city (name, state, country) VALUES ('天门', '湖北', '中国')");
        logger.info("result: {}", update);
    }

    @After
    public void cleanup() {
        dataSource.close();
    }

}
```

## 注意

那么，如果将结果映射为一个 bean 、以及批量操作呢？接下来就需要你去深入的完善咯～
