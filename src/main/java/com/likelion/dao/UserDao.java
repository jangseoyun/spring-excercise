package com.likelion.dao;

import com.likelion.context.JdbcContext;
import com.likelion.context.StatementStrategy;
import com.likelion.domain.QueryCrud;
import com.likelion.domain.UserQueryImpl;
import com.likelion.vo.UserFactory;
import com.likelion.vo.UserVo;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final DataSource dataSource;
    private final JdbcContext jdbcContext;
    private final QueryCrud query;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
        this.query = new UserQueryImpl();
    }

    public void add(UserVo user) {
        jdbcContext.setWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(query.add());
                ps.setInt(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });

    }

    public UserVo userFindById(int id) throws SQLException {
        PreparedStatement ps = dataSource.getConnection().prepareStatement(query.findOne());
        UserVo user = null;
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = UserFactory.createUser(rs.getInt(1)
                        , rs.getString(2)
                        , rs.getString(3));
                System.out.println(user);
            } else {
                throw new EmptyResultDataAccessException("해당 유저가 없습니다", 1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return user;
    }

    public void deleteAll() {//DeleteAllStrategy 사용
        jdbcContext.setWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                return dataSource.getConnection().prepareStatement(query.deleteAll());
            }
        });
    }

    public void deleteById(int id) {
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(query.deleteOne());
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            System.out.println(result);
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCountAll() {
        int count = 0;

        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(query.getCountAll());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
        //userDao.add(UserFactory.createUser(3, "sesese", "1234"));
        //System.out.println(userDao.userFindById(1));
        //userDao.deleteById(2);
        //userDao.deleteAll();
        int countAll = userDao.getCountAll();
        System.out.println(countAll);
    }


}
