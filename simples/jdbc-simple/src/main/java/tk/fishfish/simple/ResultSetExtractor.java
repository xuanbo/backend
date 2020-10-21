package tk.fishfish.simple;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ResultSet结果提取
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@FunctionalInterface
public interface ResultSetExtractor<T> {

    /**
     * 提取结果
     *
     * @param rs ResultSet
     * @return 结果
     * @throws SQLException SQL异常
     */
    T extractData(ResultSet rs) throws SQLException;

}
