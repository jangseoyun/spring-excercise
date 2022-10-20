package com.likelion.dao;

import com.likelion.domain.DbConnector;
import com.likelion.domain.Query;
import com.likelion.domain.UserQueryImpl;
import com.likelion.vo.UserFactory;
import com.likelion.vo.UserVo;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DbConnector toLocalConn;
    private PreparedStatement ps;
    private Query query;

    public UserDao(DbConnector setDatabase) throws SQLException {
        this.toLocalConn = setDatabase;
        this.query = new UserQueryImpl();
        this.ps = null;
    }

    public void add(UserVo user) {
        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.add());
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserVo userFindById(int id) throws SQLException {
        PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.findOne());
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

    public void deleteAll() {
        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.deleteAll());
            int result = ps.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void deleteById(int id) {
        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.deleteOne());
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            System.out.println(result);
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCountAll() {
        ResultSet rs = null;
        int count = 0;

        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.getCountAll());
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }

        return count;
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoFactory().localUserDao();
        //userDao.add(UserFactory.createUser(4, "sesese", "1234"));
        System.out.println(userDao.userFindById(4));
        //userDao.deleteById(2);
        //userDao.deleteAll();
        //int countAll = userDao.getCountAll();
        //System.out.println(countAll);
    }
}
