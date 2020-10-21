package tk.fishfish.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link JdbcTemplate}默认实现
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class DefaultJdbcTemplate implements JdbcTemplate {

    private final Logger logger = LoggerFactory.getLogger(DefaultJdbcTemplate.class);

    /**
     * 数据源
     */
    private final DataSource dataSource;

    public DefaultJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> clazz, Object... args) throws SQLException {
        return query(sql, rs -> {
            String[] columns = extractColumns(rs);
            if (columns.length > 1) {
                throw new SQLException("返回字段有多个，无法使用该方法");
            }
            // 只取一条记录
            if (rs.next()) {
                return covert(rs.getObject(columns[0]), clazz);
            }
            return null;
        }, args);
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Object... args) throws SQLException {
        return query(sql, rs -> {
            String[] columns = extractColumns(rs);
            // 只取一条记录
            Map<String, Object> result = new HashMap<>(columns.length);
            if (rs.next()) {
                for (String column : columns) {
                    Object value = rs.getObject(column);
                    result.put(column, value);
                }
            }
            return result;
        }, args);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Object... args) throws SQLException {
        return query(sql, rs -> {
            String[] columns = extractColumns(rs);
            // 只取一条记录
            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>(columns.length);
                for (String column : columns) {
                    Object value = rs.getObject(column);
                    row.put(column, value);
                }
                result.add(row);
            }
            return result;
        }, args);
    }

    @Override
    public int update(String sql, Object... args) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("Exec SQL => {}", sql);
            logger.debug("Exec parameter => {}", args);
        }
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            if (args == null || args.length == 0) {
                statement = connection.createStatement();
                return statement.executeUpdate(sql);
            } else {
                preparedStatement = prepareStatement(connection, sql, args);
                return preparedStatement.executeUpdate();
            }
        } finally {
            close(preparedStatement);
            close(statement);
            close(connection);
        }
    }

    private <T> T query(String sql, ResultSetExtractor<T> extractor, Object... args) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("Exec SQL => {}", sql);
            logger.debug("Exec parameter => {}", args);
        }
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            if (args == null || args.length == 0) {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
            } else {
                preparedStatement = prepareStatement(connection, sql, args);
                resultSet = preparedStatement.executeQuery();
            }
            return extractor.extractData(resultSet);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(statement);
            close(connection);
        }
    }

    /**
     * 提取返回结果的字段名称
     *
     * @param resultSet ResultSet
     * @return 字段名称
     * @throws SQLException SQL操作异常
     */
    private String[] extractColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columns = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            String name = metaData.getColumnName(i + 1);
            columns[i] = name;
        }
        return columns;
    }

    @SuppressWarnings("unchecked")
    private <T> T covert(Object value, Class<T> clazz) {
        if (value == null) {
            return null;
        }
        if (clazz == String.class) {
            return (T) value.toString();
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(value.toString());
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(value.toString());
        } else if (clazz == Float.class) {
            return (T) Float.valueOf(value.toString());
        } else if (clazz == Double.class) {
            return (T) Double.valueOf(value.toString());
        } else if (clazz == BigDecimal.class) {
            return (T) new BigDecimal(value.toString());
        } else {
            throw new IllegalArgumentException("不支持的格式转化: " + clazz.getName());
        }
    }

    private PreparedStatement prepareStatement(Connection connection, String sql, Object[] args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement;
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

}
