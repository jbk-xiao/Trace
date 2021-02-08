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


@Slf4j
@Component
public class RedisDao {

    static int pagecount = 20;
    //分页的每一页的结果数
    static int pageRecord = 20;
    @Autowired
    JedisUtil jedisUtil;

    /**
     * 根据页码查询id
     * @param query 检索词
     * @param page 页码
     * @return keys
     */
//    public List<String> getIDListOnPage(String query, int page) {
//        int start = (page - 1 ) * pagecount;
//        int end = start + pagecount - 1;
//        log.info("jedisUtil.toString(): "+jedisUtil.toString());
//        List<String> queries = FuzzySearchQueryByKeys(query);
//        Jedis jedis= jedisUtil.getClient();
//        List<String> keys = new ArrayList<String>();
//        try {
//            if (jedis.exists(query)) {
//                log.info("使用redis查询query返回idList");
//                keys.addAll(jedis.zrevrange(query,start,end));
//            } else {
//                log.info("redis没有找到query");
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return keys;
//    }
//
//    /**
//     * 返回页码总数
//     * @param query 检索词
//     * @return page
//     */
//    public long getPageNumber(String query)
//    {
//        Jedis jedis= jedisUtil.getClient();
//        long num = jedis.zcard(query);
//        long page = num/pagecount + 1;
//        return page;
//    }

    /**
     * 查询首页的id
     *
     * @param query 检索词
     * @return keys
     */
//    @Cacheable(value = "bw_id", key = "#query")
    public ArrayList<String> getIDList(String query) {
//        Jedis jedis= jedisUtil.getClient();
        List<String> list = new ArrayList<>();
        for (int i = 0; i<query.length(); i++){
            list.add(query.substring(i,i+1));
        }
        log.info("list:"+list.toString());
        ArrayList<String> res = new ArrayList<>();
        try {
            for(String key:list){
                res.addAll(fuzzySearchList(key));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据query进行模糊匹配找相应的检索词
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchQueryByKeys(String query) {
        log.info("{} 模糊匹配", query);
//        Jedis jedis = jedisUtil.getClient();
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
        List<String> keys = new ArrayList<>(fuzzySearchQueryByKeys(query));
        Jedis jedis = jedisUtil.getClient();
        log.info("模糊匹配到keys：" + keys.toString());
        List<String> list = new ArrayList<>();
        if (keys.size() > 0) {
            for (String key : keys) {
                list.addAll(jedis.zrevrange(key, 0, -1));
            }
            jedis.close();
        } else {
            log.info("redis没有查到，返回" + list.toString());
            jedis.close();
            return list;
        }
        //去重（顺序不变）
        List<String> result1 = new ArrayList<>(new LinkedHashSet<>(list));
        List<String> result = new ArrayList<>();
        log.info("redis模糊查找:" + query + ",返回" + result1.toString());
        for (String id : result1) {
            id = id.trim();
            result.add(id);
        }
        long finishQueryTime = System.currentTimeMillis();
        log.info("Jedis process time:" + (finishQueryTime - startTime));
        return result;
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
        List<String> keys = new ArrayList<>();
        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(Integer.MAX_VALUE);
        while (true) {
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            /* 返回0 说明遍历完成 */
            cursor = scanResult.getCursor();
            keys = scanResult.getResult();
            if ("0".equals(cursor)) {
                break;
            }
        }
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
    public List<String> getIDListOnPage(String query, int page){
        ArrayList<String> list = new ArrayList<String>();
        list = getIDList(query);
        int start = (page-1)*pageRecord;
        int end = start+pageRecord-1;
        List<String> res = new ArrayList<>();
        if(list.size()>=end) {
            res = list.subList(start,end+1);
        }
        return res;
    }

    /**
     * 返回要显示的页码总数
     *
     * @param query
     * @return
     */
    public Long getPageNumber(String query) {
        ArrayList<String> list;
        list = getIDList(query);
        long num = list.size();
        long page = num / pageRecord + 1;
        return page;
    }
}
