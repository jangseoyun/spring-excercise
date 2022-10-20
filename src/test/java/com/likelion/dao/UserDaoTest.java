package com.likelion.dao;

import com.likelion.vo.UserVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    void 특정사용자삭제() throws SQLException {
        UserDao userDao = new UserDaoFactory().awsUserDao();

    }
}