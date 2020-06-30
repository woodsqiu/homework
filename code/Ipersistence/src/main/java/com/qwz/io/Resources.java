package com.qwz.io;

import java.io.InputStream;

/**
 * 读取配置文件
 *
 * @author wangzhiqiu
 * @since 20/6/26 下午8:16
 */
public class Resources {

    // 根据配置文件路径将配置文件读取为输入流
    public static InputStream getResourceAsStream(String path){
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
