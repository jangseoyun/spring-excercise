package com.likelion.context;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(String sql) throws SQLException {
        setWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                return dataSource.getConnection().prepareStatement(sql);
            }
        });
    }

    public void setWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = stmt.makePreparedStatement(dataSource.getConnection());
            int result = ps.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
