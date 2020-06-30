package com.qwz.sqlsession;

import com.qwz.config.XMLConfigBuilder;
import com.qwz.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午10:22
 */
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory builder(InputStream in) throws PropertyVetoException, DocumentException {
        // 使用dom4j解析xml，将配置信息写入configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(in);

        // 创建SqlSessionFactory,工程类，用于生产sqlSession
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
