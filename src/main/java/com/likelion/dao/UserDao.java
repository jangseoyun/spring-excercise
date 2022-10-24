package com.likelion.dao;

import com.likelion.domain.QueryCrud;
import com.likelion.domain.UserQueryImpl;
import com.likelion.vo.UserVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final QueryCrud query;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.query = new UserQueryImpl();
    }

    public void add(UserVo user) {
        jdbcTemplate.update(query.add(), user.getId(), user.getName(), user.getPassword());
    }

    public UserVo userFindById(int id) throws SQLException {
        RowMapper<UserVo> rowMapper = new RowMapper<>(){
            @Override
            public UserVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserVo getUser = new UserVo(rs.getInt("id")
                        , rs.getString("name")
                        , rs.getString("password"));
                return getUser;
            }
        };
        return jdbcTemplate.queryForObject(query.findOne(), rowMapper, id);
    }

    public void deleteAll() throws SQLException {//템플릿 , 콜백 적용
        jdbcTemplate.update(query.deleteAll());
    }

    public void deleteById(int id) {

    }

    public int getCountAll() {
        return jdbcTemplate.queryForObject(query.getCountAll(), Integer.class);
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
