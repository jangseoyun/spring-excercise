package com.likelion.context;

import com.likelion.domain.UserQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy {
    @Autowired
    private UserQueryImpl userQuery;

    public DeleteAllStrategy() {
        this.userQuery = new UserQueryImpl();
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(userQuery.deleteAll());
    }
}
