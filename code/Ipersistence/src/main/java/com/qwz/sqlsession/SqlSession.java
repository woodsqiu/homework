package com.qwz.sqlsession;

import java.util.List;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午11:18
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    <T> T selectOne(String statementId, Object... params) throws Exception;

    <T> T getMapper(Class<?> mapperClass);

    int delete(String statementId, Object... params) throws Exception;

    int update(String statementId, Object... params) throws Exception;

    int insert(String statementId, Object... params) throws Exception;
}
