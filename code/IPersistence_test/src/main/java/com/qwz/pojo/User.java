package com.qwz.pojo;

/**
 * 用户类
 *
 * @author wangzhiqiu
 * @since 20/6/26 上午8:54
 */
public class User {
    private Integer id;

    private String username;

    @Override
    public String toString() {
        return "com.qwz.pojo.User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

