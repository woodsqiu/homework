package com.qwz.test;

import com.qwz.dao.IUserDao;
import com.qwz.io.Resources;
import com.qwz.pojo.User;
import com.qwz.sqlsession.SqlSession;
import com.qwz.sqlsession.SqlSessionFactory;
import com.qwz.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author wangzhiqiu
 * @since 20/6/26 下午8:27
 */
public class IPersistenceTest {
    private IUserDao userDao;

    @Before
    public void before() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.builder(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @Test
    public void findByConditionMapper() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("中文");
        User byCondition = userDao.findByCondition(user);
        System.out.println(byCondition);
    }

    @Test
    public void findAllMapper() throws Exception {
        List<User> list = userDao.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setId(5);
        user.setUsername("小明");
        int row = userDao.insertUser(user);
        System.out.println("插入数量:" + row);
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setId(5);
        user.setUsername("小明改名");
        int row = userDao.updateUser(user);
        System.out.println("修改数量:" + row);
    }

    @Test
    public void deleteUser() throws Exception {
        int row = userDao.deleteUser(5);
        System.out.println("删除数量:" + row);
    }
}
