package com.trace.trace.dao;

import com.google.gson.Gson;
import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github-new
 * @packageName: com.trace.trace.dao
 * @Description:
 * @create: 2021-02-19-12:49
 */
@Slf4j
@Component
public class ProductRedisDao {
    @Autowired
    JedisUtil jedisUtil;

    Gson gson = new Gson();

    /**
     * 添加产品
     * @param key
     * @param field
     * @param value
     * @return
     */
    public int insert(String key, String field, String value){
        try {
            Jedis jedis = jedisUtil.getClient();
            jedis.select(4);
            jedis.hset(key, field, value);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 删除产品
     * @param key
     * @param field
     * @return
     */
    public int delete(String key, String field){
        try{
            Jedis jedis = jedisUtil.getClient();
            jedis.select(4);
            jedis.hdel(key, field);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 更新产品信息
     * @param key
     * @param field
     * @param value
     * @return
     */
    public int update(String key, String field, String value){
        try{
            Jedis jedis = jedisUtil.getClient();
            jedis.select(4);
            jedis.hset(key, field, value);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**查找单个产品
     *
     * @param key
     * @param field
     * @return
     */
    public String find(String key, String field){
        String value = null;
        try{
            Jedis jedis = jedisUtil.getClient();
            jedis.select(4);
            value = jedis.hget(key, field);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取所有产品
     * @param key
     * @return
     */
    public String getAll(String key){
        Map<String, String> map = null;
        try{
            Jedis jedis = jedisUtil.getClient();
            jedis.select(4);
            map = jedis.hgetAll(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return gson.toJson(map);
    }
}
