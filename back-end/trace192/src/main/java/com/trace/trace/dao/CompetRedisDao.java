package com.trace.trace.dao;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.dao
 * @Description
 * @create 2021-02-12-6:09 下午
 */
@Slf4j
@Repository
public class CompetRedisDao {
    @Autowired
    JedisUtil jedisUtil;

    /**
     * 获取到regis_id对应的sku_id
     *
     * @param regis_id
     * @return
     */
    public String getSkuId(String regis_id) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(1);
        String sku_id = null;
        try {
            if (jedis.exists(regis_id)) {
                sku_id = jedis.get(regis_id);
                log.info("regis_id:" + regis_id + ",sku_id:" + sku_id);
            } else {
                log.info("redis didn't find" + regis_id);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return sku_id;
    }

    /**
     * 获取到主公司regis_id对应的所有竞品公司regis_id
     *
     * @param regis_id
     * @return
     */
    public List<String> getCompetRegisId(String regis_id) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(2);
        List<String> regisIdList = new ArrayList<>();
        try {
            if (jedis.exists(regis_id)) {
                regisIdList.addAll(jedis.smembers(regis_id));
                log.info(regisIdList.toString());
            } else {
                log.info("redis didn't find" + regis_id);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return regisIdList;
    }

    /**
     * 根据主公司sku_id获取到所有竞品商品的sku_id
     *
     * @param sku_id
     * @return
     */
    public List<String> getCompetSkuId(String sku_id) {
        Jedis jedis = jedisUtil.getClient();
        jedis.select(2);
        List<String> skuIdList = new ArrayList<>();
        try {
            if (jedis.exists(sku_id)) {
                skuIdList.addAll(jedis.zrevrange(sku_id, 0, -1));
                log.info(skuIdList.toString());
            } else {
                log.info("redis didn't find" + sku_id);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return skuIdList;
    }
}
