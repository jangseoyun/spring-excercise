package com.likelion.domain;

public interface Query {
    String findOne();

    String add();

    String findAll();

    String deleteOne();

    String deleteAll();

    String getCountAll();
}
