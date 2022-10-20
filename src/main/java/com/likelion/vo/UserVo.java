package com.likelion.vo;

public class UserVo {
    private int id;
    private String name;
    private String password;

    private static UserVo userVo = new UserVo();

    public UserVo() {
    }

    public UserVo(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public static UserVo getUserVo() {
        return userVo;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
