package com.likelion.domain;

public class UserQueryImpl implements QueryCrud {
    @Override
    public String findOne() {
        String query = "";
        query += "select *";
        query += " from users";
        query += " where id = ?";
        System.out.println(query);
        return query;
    }

    @Override
    public String add() {
        String query = "";
        query += "insert into users(id, name, password) values (?, ?, ?)";
        System.out.println(query);
        return query;
    }

    @Override
    public String findAll() {
        String query = "";
        query += "select *";
        query += " from users";
        System.out.println(query);
        return query;
    }

    @Override
    public String deleteOne() {
        String query = "";
        query += "delete from users where id = ?";
        System.out.println(query);
        return query;
    }

    @Override
    public String deleteAll() {
        String query = "";
        query += "delete from users";
        System.out.println(query);
        return query;
    }

    @Override
    public String getCountAll() {
        String query = "";
        query += "select count(*) from users";
        System.out.println(query);
        return query;
    }

}
