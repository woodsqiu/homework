package com.qwz.sqlsession;

import com.qwz.pojo.Configuration;
import com.qwz.pojo.MappedStatement;

import java.util.List;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午11:52
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}