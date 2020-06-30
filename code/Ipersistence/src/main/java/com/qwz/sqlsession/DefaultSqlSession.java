package com.qwz.sqlsession;

import com.qwz.pojo.Configuration;
import com.qwz.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午11:20
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        // 调用executor的query方法
        List<Object> objects = new SimpleExecutor().query(configuration, mappedStatement, params);
        return (List<E>) objects;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects == null || objects.size() == 0) {
            return null;
        } else if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("返回结果过多");
        }
    }

    // 使用JDK动态代理为dao接口生成代理对象,并返回
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 获取statementId：namespace.id=dao类+方法名
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                if (methodName.contains("insert")) {
                    return insert(statementId, args);
                }
                if (methodName.contains("update")) {
                    return update(statementId, args);
                }
                if (methodName.contains("delete")) {
                    return delete(statementId, args);
                }
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });

        return (T) o;
    }

    public int delete(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        // 调用executor的update方法
        int row = new SimpleExecutor().delete(configuration, mappedStatement, params);
        return row;
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        // 调用executor的update方法
        int row = new SimpleExecutor().update(configuration, mappedStatement, params);
        return row;
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        return update(statementId, params);
    }

}
