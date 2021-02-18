package com.trace.trace.dao;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.dao
 * @Description 溯源模块
 * @create 2021-02-18-1:13 下午
 */
@Slf4j
@Component
public class TraceRedisDao {

    @Autowired
    JedisUtil jedisUtil;

    /**
     * 获得初始流程输入时的产品列表选项内容
     * @param company_name
     * @return
     */
    public List<String> getProductList(String company_name){
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        List<String> productList = new ArrayList<>();
        try {
            if (jedis.exists(company_name)) {
                productList.addAll(jedis.hkeys(company_name));
                log.info("redis found productList:" + productList.toString());
            }else {
                log.info("redis没有找到"+company_name);
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        jedis.close();
        return productList;
    }


    /**
     * 从redis中获取到公司产品对应的条形码，用来构建溯源码
     * @param pname
     * @param company_name
     * @return
     */
    public String getProductCode(String pname,String company_name){
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        String productCode = null;
        try {
            if (jedis.exists(company_name)) {
                productCode = jedis.hget(company_name,pname);
                log.info("redis found productCode:" + productCode);
            }else {
                log.info("redis没有找到"+company_name);
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        jedis.close();
        return productCode;
    }
}
