package com.likelion.dao;

import com.likelion.domain.AwsConnetionImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao awsUserDao() throws SQLException {
        AwsConnetionImpl awsConnetion = new AwsConnetionImpl();
        UserDao userDao = new UserDao(awsConnetion);
        return userDao;
    }
}
