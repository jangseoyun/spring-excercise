package com.likelion.dao;

import com.likelion.vo.UserFactory;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext ac;

    UserDao userDao;
    UserVo user1;
    UserVo user2;
    UserVo user3;

    @BeforeEach
    void setUp() {
        userDao = ac.getBean("localUserDao", UserDao.class);
        user1 = UserFactory.createUser(1, "seoyun", "1234");
        user2 = UserFactory.createUser(2, "seoseo", "1234");
        user3 = UserFactory.createUser(3, "yunyun", "1234");
        userDao.deleteAll();
    }

    @DisplayName("사용자 등록 성공 확인")
    @Test
    void 사용자등록테스트() {
        userDao.add(user1);
        UserVo selectUserOne = userDao.userFindById(1);
        assertEquals("seoyun", selectUserOne.getName());
    }

    @DisplayName("테이블 데이터 전체 삭제")
    @Test
    void 테이블전체삭제() {
        userDao.deleteAll();
        assertEquals(0, userDao.getCountAll());
    }

    @DisplayName("사용자 등록 후 가져오기")
    @Test
    void addAndGet() {
        assertEquals(0, userDao.getCountAll());

        userDao.add(user1);
        assertEquals(1, userDao.getCountAll());
        UserVo user = userDao.userFindById(1);

        assertEquals("seoyun", user.getName());
        assertEquals("1234", user.getPassword());
    }

    @DisplayName("user 테이블 전체 카운트")
    @Test
    void count() {
        assertEquals(0, userDao.getCountAll());

        userDao.add(user1);
        assertEquals(1, userDao.getCountAll());
        userDao.add(user2);
        assertEquals(2, userDao.getCountAll());
        userDao.add(user3);
        assertEquals(3, userDao.getCountAll());
    }

    @DisplayName("해당 유저 한명 데이터 반환")
    @Test()
    void findById() {
        userDao.add(user1);
        UserVo user = userDao.userFindById(1);
        assertEquals("seoyun", user.getName());

        assertThrows(EmptyResultDataAccessException.class,
                () -> userDao.userFindById(4));

    }

    @DisplayName("전체 유저 데이터 반환")
    @Test
    void getAll() {
        List<UserVo> userList = userDao.findAll();
        assertEquals(0, userList.size());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        assertEquals(3, userDao.findAll().size());
    }
}