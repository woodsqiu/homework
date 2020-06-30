package com.qwz.sqlsession;

import com.qwz.config.BoundSql;
import com.qwz.pojo.Configuration;
import com.qwz.pojo.MappedStatement;
import com.qwz.utils.GenericTokenParser;
import com.qwz.utils.ParameterMapping;
import com.qwz.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午11:53
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);

        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getTypeClass(resultType);

        ArrayList<Object> list = new ArrayList<>();

        // 遍历结果集，封装到返回类型中
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

                // 使用反射或内省，根据数据库及实体映射关系，封装结果
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            list.add(o);
        }

        return (List<E>) list;
    }

    private PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement,
                                                   Object... params) throws SQLException, ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException {
        // 获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        // 获取sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        String sqlText = boundSql.getSqlText();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlText);

        // 获取参数类型
        String paramType = mappedStatement.getParamType();
        Class<?> paramTypeClass = getTypeClass(paramType);

        // 根据占位符中的字段，设置preparedStatement占位符的值
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = paramTypeClass.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }

        return preparedStatement;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);
        int row = preparedStatement.executeUpdate();
        return row;
    }

    @Override
    public int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        // 获取sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        String sqlText = boundSql.getSqlText();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlText);

        // 如果参数类型为Integer或者String类型，直接给占位符?赋值
        preparedStatement.setObject(1, params[0]);
        int row = preparedStatement.executeUpdate();
        return row;
    }

    // 获取类
    private Class<?> getTypeClass(String classname) throws ClassNotFoundException {
        if (classname != null) {
            return Class.forName(classname);
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String sqlText = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(sqlText, parameterMappings);
        return boundSql;
    }
}
