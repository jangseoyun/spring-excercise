package com.likelion.dao;

import com.likelion.vo.UserVo;

public class UserFactory {
    public static UserVo createUser(int getId, String getName, String getPassword) {
        //여기서 값을 검증하여 넣어줄 수 있음
        UserVo userVo = new UserVo(getId, getName, getPassword);
        return userVo;
    }
}
