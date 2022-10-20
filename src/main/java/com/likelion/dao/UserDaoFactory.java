package com.likelion.dao;

import com.likelion.domain.AwsConnectionImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao awsUserDao() throws SQLException {
        AwsConnectionImpl awsConnection = new AwsConnectionImpl();
        UserDao userDao = new UserDao(awsConnection);
        return userDao;
    }
}
