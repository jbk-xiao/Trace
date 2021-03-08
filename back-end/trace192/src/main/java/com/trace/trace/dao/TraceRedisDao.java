package com.trace.trace.dao;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class TraceRedisDao {

    //分页的每一页的结果数
    private static final int pageRecord = 10;
    private final JedisUtil jedisUtil;

    @Autowired
    public TraceRedisDao(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }

    /**
     * 获得初始流程输入时的产品列表选项内容
     *
     * @param regis_id
     * @return
     */
    public List<String> getProductList(String regis_id) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        List<String> productList = new ArrayList<>();
        try {
            if (jedis.exists(regis_id)) {
                productList.addAll(jedis.hkeys(regis_id));
                log.info("redis found productList:" + productList.toString());
            } else {
                log.info("redis没有找到" + regis_id);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return productList;
    }


    /**
     * 从redis中获取到公司产品对应的条形码，用来构建溯源码
     *
     * @param product_name
     * @param regis_id
     * @return Code
     */
    public String getProductCode(String product_name, String regis_id) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        String code = null;
        try {
            if (jedis.exists(regis_id)) {
                code = jedis.hget(regis_id, product_name);
                log.info("redis found code:" + code);
            } else {
                log.info("redis没有找到" + regis_id);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return code;
    }

    /**
     * 根据页码返回该商品下所有溯源码，一次十条
     *
     * @param code
     * @param page
     * @return
     */
    public List<String> getAllTraceCode(String code, int page) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        List<String> traceCodeList = new ArrayList<>();
        int start = (page - 1) * pageRecord;
        log.debug("start: " + start);
        int end = start + pageRecord - 1;
        long length = jedis.llen(code);
        if (end > length) {
            end = (int) length;
        }
        log.debug("end: " + end);
        try {
            if (jedis.exists(code)) {
                traceCodeList.addAll(jedis.lrange(code, start, end));
                log.info("redis found traceCodeList:" + traceCodeList.toString());
            } else {
                log.info("redis didn't find traceCodeList: " + code);
            }
        } finally {
            jedis.close();
        }
        return traceCodeList;
    }

    /**
     * 返回要显示的页码总数
     *
     * @param code
     * @return
     */
    public Long getPageNumber(String code) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        long num;
        try {
            num = jedis.llen(code);
        } finally {
            jedis.close();
        }
        long page = num / pageRecord + 1;
        log.info("pageCount: " + page);
        return page;
    }
}
