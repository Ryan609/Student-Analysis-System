package com.spa.dao;

import org.apache.ibatis.annotations.Mapper;

import com.spa.model.User;

@Mapper
public interface userDao {

    User getUser(String username, String password);

}
