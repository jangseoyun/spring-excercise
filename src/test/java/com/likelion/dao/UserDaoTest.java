package com.likelion.dao;

import com.likelion.vo.UserVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    UserDao userDao;

    UserVo user1;
    UserVo user2;
    UserVo user3;

    @BeforeEach
    void setUp() {
         userDao = context.getBean("awsUserDao", UserDao.class);
         user1 = new UserVo(1, "seoyun", "1234");
         user2 = new UserVo(2, "seoseo", "1234");
         user3 = new UserVo(3, "yunyun", "1234");
    }

    @DisplayName("사용자 등록 성공 확인")
    @Test
    void 사용자등록테스트() throws SQLException {
        //given
        userDao.add(user2);
        UserVo selectUserOne = userDao.userFindById(2);
        assertEquals("seoseo", selectUserOne.getName());
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
        assertThrows(EmptyResultDataAccessException.class, ()-> {
            userDao.userFindById(2);
        });
    }
}