package com.trace.trace.dao;

import com.trace.trace.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.*;


@Slf4j
@Component
public class RedisDao {

    @Autowired
    JedisUtil jedisUtil;

    static int pagecount=20;
    /**
     * 查询首页的id
     * @param query 检索词
     * @return keys
     */
//    @Cacheable(value = "bw_id", key = "#query")
    public ArrayList<String> getIDList(String query) {
        log.info("调试"+jedisUtil.toString());
        Jedis jedis= jedisUtil.getClient();
        String queries[] = query.split(" ");
        List<String> list = Arrays.asList(queries);
        log.info("list:"+list.toString());
        ArrayList<String> res = new ArrayList<String>();
        try {
            for(String key:list){
                if(jedis.exists(key)){
                    res.addAll(FuzzySearchList(key));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return res;
    }

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
     * 根据query进行模糊匹配找相应的检索词
     * @param query
     * @return
     */
    public List<String> FuzzySearchQueryByKeys(String query){
        log.info("{} 模糊匹配",query);
        Jedis jedis= jedisUtil.getClient();
        String pattern=query.trim().replaceAll("\\s+","*");
        pattern="*"+pattern+"*";
        long startTime = System.currentTimeMillis();
        List<String> res = jedisScan(pattern);
        long finishTime = System.currentTimeMillis();
        log.info("jediskeys process time:" + (finishTime - startTime));
        log.info("{} 模糊匹配,size:{}",pattern, res.size());
        return res;
    }

    /**
     * 根据query找相应的skuId
     * @param query
     * @return
     */
    public List<String> FuzzySearchList(String query)
    {
        log.info("使用方法FuzzySearchList");
//        long startTime = System.currentTimeMillis();
        List<String> keys = new ArrayList<String>();
        keys.addAll(FuzzySearchQueryByKeys(query));
        Jedis jedis= jedisUtil.getClient();
        log.info("模糊匹配到keys："+keys.toString());
        List<String> list = new ArrayList<String>();
        if(keys.size()>0){
            for (String key : keys) {
                list.addAll(jedis.zrevrange(key,0,-1));
            }
        }else {
            log.info("redis没有查到，返回"+list.toString());
            return list;
        }
        List<String> result1 = new ArrayList<String>(new LinkedHashSet<String>(list)); //去重（顺序不变）
        List<String> result = new ArrayList<>();
        log.info("redis模糊查找:"+query+",返回"+result1.toString());
        for(String id : result1)
        {
            id = id.trim();
            result.add(id);
        }
//        long finishQueryTime = System.currentTimeMillis();
//        log.info("Jedis process time:" + (finishQueryTime - startTime));
        return result;
    }

    /**
     * 根据模糊的query找到所有的检索词
     * @param pattern
     * @return
     */
    private List<String> jedisScan(String pattern){
        long startTime = System.currentTimeMillis();
        Jedis jedis= jedisUtil.getClient();
        String cursor = ScanParams.SCAN_POINTER_START;
        List<String> keys = new ArrayList<String>();
        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(Integer.MAX_VALUE);
        while (true){
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getCursor();// 返回0 说明遍历完成
            keys = scanResult.getResult();
            if ("0".equals(cursor)){
                break;
            }
        }
        long finishTime = System.currentTimeMillis();
        log.info("jedisScan process time:" + (finishTime - startTime));
        return keys;
    }


    //分页的每一页的结果数
    static int pageRecord = 20;

    /**
     * getAnsOnPage通过传进的页码page和检索词query查找对应页要返回的skuId
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
        res = list.subList(start,end+1);
        return res;
    }

    /**
     * 返回要显示的页码总数
     * @param query
     * @return
     */
    public Long getPageNumber(String query){
        ArrayList<String> list = new ArrayList<String>();
        list = getIDList(query);
        long num = list.size();
        long page = num/pageRecord+1;
        return page;
    }
}