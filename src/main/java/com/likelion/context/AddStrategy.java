package com.likelion.context;

import com.likelion.vo.UserFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatementStrategy {

    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
        /*ps.setInt(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());*/
        return ps;
    }
}
