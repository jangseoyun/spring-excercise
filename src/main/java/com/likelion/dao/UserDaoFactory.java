package com.likelion.dao;

import com.likelion.domain.AwsConnectionImpl;
import com.likelion.domain.LocalConnectionImpl;
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

    @Bean
    public UserDao localUserDao() throws SQLException {
        LocalConnectionImpl localConnection = new LocalConnectionImpl();
        UserDao userDao = new UserDao(localConnection);
        return userDao;
    }

}
