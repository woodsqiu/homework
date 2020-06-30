package com.qwz.pojo;

/**
 * 封装mapper配置的值
 *
 * @author wangzhiqiu
 * @since 20/6/26 下午8:51
 */
public class MappedStatement {
    private String id;
    private String resultType;
    private String sql;
    private String paramType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}
