package com.qwz.sqlsession;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午10:24
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
