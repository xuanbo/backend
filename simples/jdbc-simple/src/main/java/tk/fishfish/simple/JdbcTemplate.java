package tk.fishfish.simple;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * jdbc使用
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
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
