package com.trace.trace.dao;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Component
public class RedisDao {

    //分页的每一页的结果数
    static int pageRecord = 20;
    final JedisUtil jedisUtil;

    @Autowired
    public RedisDao(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }

    /**
     * 查询首页的id
     *
     * @param query 检索词
     * @return keys
     */
    public List<String> getIDList(String query) {
        List<String> list = new ArrayList<>();
        for (int i = 0, length = query.length(); i < length; i++) {
            list.add(query.substring(i, i + 1));
        }
        int runsize = list.size();
        ExecutorService executor = Executors.newFixedThreadPool(runsize);
        final CountDownLatch latch = new CountDownLatch(runsize);
        log.info("list:" + list.toString());
        List<String> res = new ArrayList<>(fuzzySearchList(query));
        for (String key : list) {
            executor.execute(() -> {
                res.addAll(fuzzySearchList(key));
                latch.countDown();
            });
        }
        try {
            latch.await();
            executor.shutdown();
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        //去重（顺序不变）
        List<String> result1 = new ArrayList<>(new LinkedHashSet<>(res));
        List<String> result = new ArrayList<>();
        log.debug("redis模糊查找:" + query + ",返回" + result1.toString());
        log.info("redis模糊查找:{},返回{}条。", query, result1.size());
        for (String id : result1) {
            id = id.trim();
            result.add(id);
        }//后边有空格时不会影响mysql中检索，行首会。
        return result;
    }

    /**
     * 根据query进行模糊匹配找相应的检索词
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchQueryByKeys(String query) {
        log.info("{} 模糊匹配", query);
        String pattern = query.trim().replaceAll("\\s+", "*");
        pattern = "*" + pattern + "*";
        long startTime = System.currentTimeMillis();
        List<String> res = jedisScan(pattern);
        long finishTime = System.currentTimeMillis();
        log.info("jediskeys process time:" + (finishTime - startTime));
        log.info("{} 模糊匹配,size:{}", pattern, res.size());
        return res;
    }

    /**
     * 根据query找相应的skuId
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchList(String query) {
        log.info("使用方法fuzzySearchList");
        long startTime = System.currentTimeMillis();
        List<String> keys = fuzzySearchQueryByKeys(query);
        Jedis jedis = jedisUtil.getClient();
        log.debug("模糊匹配到keys：" + keys.toString());
        log.info("模糊匹配到{}条keys",keys.size());
        List<String> list = new ArrayList<>();
        for (String key : keys) {
            list.addAll(jedis.zrevrange(key, 0, -1));
        }
        jedis.close();
        long finishQueryTime = System.currentTimeMillis();
        log.info("Jedis process time:" + (finishQueryTime - startTime));
        return list;
    }

    /**
     * 根据模糊的query找到所有的检索词
     *
     * @param pattern
     * @return
     */
    private List<String> jedisScan(String pattern) {
        long startTime = System.currentTimeMillis();
        Jedis jedis = jedisUtil.getClient();
        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(Integer.MAX_VALUE);
        List<String> keys = new ArrayList<>();
        do {
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            /* 返回0 说明遍历完成 */
            cursor = scanResult.getCursor();
            keys = scanResult.getResult();
        } while (!"0".equals(cursor));
        long finishTime = System.currentTimeMillis();
        log.info("jedisScan process time:" + (finishTime - startTime));
        jedis.close();
        return keys;
    }

    /**
     * getAnsOnPage通过传进的页码page和检索词query查找对应页要返回的skuId
     *
     * @param page
     * @param query
     * @return
     */
    public List<String> getIDListOnPage(String query, int page) {
        List<String> list = getIDList(query);
        int length = list.size();
        int start = (page - 1) * pageRecord;
        int end = start + pageRecord;
        end = Math.min(end, length);
        List<String> res = list.subList(start, end);
        res.add(0, getPageNumber(length) + "");
        return res;
    }

    /**
     * 返回要显示的页码总数
     *
     * @param length listLength
     * @return pagenum
     */
    private Integer getPageNumber(int length) {
        return length / pageRecord + (length % pageRecord == 0 ? 0 : 1);
    }
}
