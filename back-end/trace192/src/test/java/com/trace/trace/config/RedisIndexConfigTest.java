package com.trace.trace.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.config
 * @Description
 * @create 2021-02-07-10:14
 */
//@SpringBootTest
class RedisIndexConfigTest {
//    @Autowired
//    RedisIndexConfig redisIndexConfig;
    @Resource
    RedisIndexConfig redisIndexConfig = new RedisIndexConfig();
    @Test
    void indexConfigTest() {
        System.out.println(redisIndexConfig.getMap().keySet());
        System.out.println(redisIndexConfig.getMap().get("工厂_炒制"));
    }

}