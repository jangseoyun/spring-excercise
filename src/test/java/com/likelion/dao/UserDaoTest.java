package com.likelion.dao;

import com.likelion.vo.UserVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @DisplayName("사용자 등록 성공 확인")
    @Test
    void 사용자등록테스트() throws SQLException, ClassNotFoundException {
        //given
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        UserVo user = new UserVo(2, "hellohahaha", "1234");
        userDao.add(user);

        UserVo selectUserOne = userDao.userFindById(2);

        assertEquals("hellohahaha", selectUserOne.getName());
    }

    @DisplayName("특정 사용자 삭제")
    @Test
    void 테이블전체삭제() throws SQLException {
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCountAll());
    }

    @DisplayName("addAndGet")
    @Test
    void addAndGet() throws SQLException {
        UserVo user1 = new UserVo(1, "seoyun", "1234");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCountAll());

        userDao.add(user1);
        assertEquals(1, userDao.getCountAll());
        UserVo user = userDao.userFindById(1);

        assertEquals("testName", user.getName());
        assertEquals("testpw", user.getPassword());
    }

    @DisplayName("count")
    @Test
    void count() {
        UserVo user1 = new UserVo(1, "seoyun", "1234");
        UserVo user2 = new UserVo(2, "seoseo", "1234");
        UserVo user3 = new UserVo(3, "yunyun", "1234");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCountAll());

        userDao.add(user1);
        assertEquals(1, userDao.getCountAll());
        userDao.add(user2);
        assertEquals(2, userDao.getCountAll());
        userDao.add(user3);
        assertEquals(3, userDao.getCountAll());
    }

    @DisplayName("findById")
    @Test
    void findById() {

    }
}