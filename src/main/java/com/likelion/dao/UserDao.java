package com.likelion.dao;

import com.likelion.domain.DbConnector;
import com.likelion.domain.Query;
import com.likelion.domain.UserQueryImpl;
import com.likelion.vo.UserFactory;
import com.likelion.vo.UserVo;

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
            ps = toLocalConn.dbConnection().prepareStatement(query.add());
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
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        UserVo user = null;
        while (rs.next()) {
            user = UserFactory.createUser(rs.getInt(1)
                    , rs.getString(2)
                    , rs.getString(3));
        }
        rs.close();
        ps.close();
        return user;

    }

    public void deleteAll() {
        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.deleteAll());
            int result = ps.executeUpdate();
            System.out.println(result);
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("user table 전체 삭제 실패");
        }
    }

    public void deleteById(int id) {
        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.deleteOne());
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            System.out.println(result);// 성공하면 1, 실패하면 0
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCountAll() {
        int count = 0;
        try {
            PreparedStatement ps = toLocalConn.dbConnection().prepareStatement(query.getCountAll());

            ResultSet rs = ps.executeQuery();
            count = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().awsUserDao();
        //userDao.add(UserFactory.createUser(1, "seoyun", "1234"));
        //userDao.userFindById(1);
        //userDao.deleteById(2);
        userDao.deleteAll();
        int countAll = userDao.getCountAll();
        System.out.println(countAll);
    }
}
