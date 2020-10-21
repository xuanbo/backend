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
