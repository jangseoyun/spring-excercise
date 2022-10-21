package com.likelion.context;

import com.likelion.domain.UserQueryImpl;
import com.likelion.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatementStrategy {

    private UserQueryImpl userQuery;
    private UserVo user;

    public AddStrategy(UserVo user) {
        this.userQuery = new UserQueryImpl();
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(userQuery.add());
        ps.setInt(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        return ps;
    }
}
