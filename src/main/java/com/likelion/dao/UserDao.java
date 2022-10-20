package com.likelion.dao;

import com.likelion.domain.DbConnector;
import com.likelion.domain.Query;
import com.likelion.domain.UserQueryImpl;
import com.likelion.vo.UserVo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DbConnector toAwsConn;
    private PreparedStatement ps;
    private Query query;

    public UserDao(DbConnector toDatabase) throws SQLException {
        this.toAwsConn = toDatabase;
        this.query = new UserQueryImpl();
        this.ps = null;
    }

    public void add(UserVo user) {
        try {
            ps = toAwsConn.dbConnection().prepareStatement(query.add());
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
        PreparedStatement ps = toAwsConn.dbConnection().prepareStatement(query.findOne());
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        UserVo user = null;
        while (rs.next()) {
            int getId = rs.getInt(1);
            String getName = rs.getString(2);
            String getPassword = rs.getString(3);
            user = UserFactory.createUser(getId, getName, getPassword);
        }
        rs.close();
        ps.close();
        return user;

    }

    public void deleteAll(){
        try {
            PreparedStatement ps = toAwsConn.dbConnection().prepareStatement(query.deleteAll());
            int result = ps.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            throw new RuntimeException("user table 전체 삭제 실패");
        }
    }

    public void deleteById(int id) {
        try {
            PreparedStatement ps = toAwsConn.dbConnection().prepareStatement(query.deleteOne());
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            System.out.println(result);// 성공하면 1, 실패하면 0
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCountAll() {
        int count = 0;
        try {
            PreparedStatement ps = toAwsConn.dbConnection().prepareStatement(query.getCountAll());
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDaoFactory().awsUserDao();
        //userDao.add(new UserVo(1, "seoyun", "1234"));
        //userDao.userFindById(1);
        //userDao.deleteById(2);
        int countAll = userDao.getCountAll();
        System.out.println(countAll);
    }
}
