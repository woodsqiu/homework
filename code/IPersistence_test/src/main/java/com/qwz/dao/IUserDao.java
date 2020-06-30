package com.qwz.dao;

import com.qwz.pojo.User;

import java.util.List;

/**
 * @author wangzhiqiu
 * @since 20/6/26 上午11:59
 */
public interface IUserDao {
    List<User> findAll() throws Exception;

    User findByCondition(User user) throws Exception;

    int insertUser(User user) throws Exception;

    int updateUser(User user) throws Exception;

    int deleteUser(Integer id) throws Exception;
}
