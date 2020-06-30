package com.qwz.sqlsession;

import com.qwz.pojo.Configuration;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午11:12
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration =  configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
