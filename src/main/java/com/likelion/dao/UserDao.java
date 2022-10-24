package com.likelion.dao;

import com.likelion.context.AddStrategy;
import com.likelion.context.DeleteAllStrategy;
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

    private DataSource dataSource;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private QueryCrud query;

    public UserDao(DataSource dataSource) throws SQLException {
        this.conn = dataSource.getConnection();
        this.query = new UserQueryImpl();
        this.ps = null;
        this.rs = null;
    }

    public void add(UserVo user) {
        try {
            PreparedStatement ps = new AddStrategy(user).makePreparedStatement(conn);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserVo userFindById(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query.findOne());
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
        } catch (SQLException e) {
            e.getMessage();
        }

        return user;
    }

    public void deleteAll() {//DeleteAllStrategy 사용
        jdbcContextWithStatementStrategy(
            new DeleteAllStrategy() {
                public PreparedStatement makePreparedStatement(Connection conn) throws SQLException{
                    return conn.prepareStatement(query.deleteAll());
                }
            });
    }

    public void deleteById(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement(query.deleteOne());
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
            PreparedStatement ps = conn.prepareStatement(query.getCountAll());
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    private void jdbcContextWithStatementStrategy(StatementStrategy stmt) {
        try {
            PreparedStatement ps = stmt.makePreparedStatement(conn);
            int result = ps.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }

    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
        //userDao.add(UserFactory.createUser(3, "sesese", "1234"));
        //System.out.println(userDao.userFindById(1));
        //userDao.deleteById(2);
        //userDao.deleteAll();
        int countAll = userDao.getCountAll();
        System.out.println(countAll);
        userDao.close();
    }



}
