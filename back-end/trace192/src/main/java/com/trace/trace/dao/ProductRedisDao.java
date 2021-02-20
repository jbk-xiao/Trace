package com.trace.trace.dao;

import com.google.gson.Gson;
import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
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
     * 添加商品
     * @param regis_id
     * @param product_name
     * @param code
     * @return
     */
    public int insert(String regis_id, String product_name, String code){
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getClient();
            jedis.select(4);
            jedis.hset(regis_id, product_name, code);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            jedis.close();
        }
        return 1;
    }

    /**
     * 删除商品
     * @param regis_id
     * @param product_name
     * @return
     */
    public int delete(String regis_id, String product_name){
        Jedis jedis = null;
        try{
            jedis = jedisUtil.getClient();
            jedis.select(4);
            jedis.hdel(regis_id, product_name);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            jedis.close();
        }
        return 1;
    }

    /**
     * 更新商品信息
     * @param code
     * @param regis_id
     * @param product_name
     * @return
     */
    public int update(String regis_id, String product_name, String code){
        Jedis jedis = null;
        try{
            jedis = jedisUtil.getClient();
            jedis.select(4);
            jedis.hset(regis_id, product_name, code);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            jedis.close();
        }
        return 1;
    }

    /**查找单个产品
     *
     * @param regis_id
     * @param product_name
     * @return
     */
    public String find(String regis_id, String product_name){
        String value = null;
        Jedis jedis = null;
        try{
            jedis = jedisUtil.getClient();
            jedis.select(4);
            value = jedis.hget(regis_id, product_name);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取所有商品与对应十三位条形码
     * @param regis_id
     * @return
     */
    public String getAll(String regis_id){
        StringBuilder res = new StringBuilder();
        Map<String, String> map = null;
        Jedis jedis = null;
        try{
            jedis = jedisUtil.getClient();
            jedis.select(4);
            map = jedis.hgetAll(regis_id);
            res.append("[");
            for(Map.Entry<String, String> entry : map.entrySet()){
                res.append("{");
                res.append("\"product\":");
                res.append("\""+entry.getKey().trim()+"\"");
                res.append(",");
                res.append("\"code\":");
                res.append(entry.getValue().trim());
                res.append("},");
            }
            res.deleteCharAt(res.lastIndexOf(","));
            res.append("]");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return res.toString();
    }

    /**
     * 获取到所有的商品名称
     * @param regis_id
     * @return
     */
    public List<String> getAllProductName(String regis_id){
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        List<String> productList = new ArrayList<>();
        try {
            if (jedis.exists(regis_id)) {
                productList.addAll(jedis.hkeys(regis_id));
            }else {
                log.info("redis没有找到"+regis_id);
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return productList;
    }
}
