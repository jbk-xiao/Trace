package com.JingDong.JingDong;

import com.JingDong.JingDong.mapper.QueryMapper;
import com.JingDong.JingDong.redisDao.RedisDao;
import com.JingDong.JingDong.redisDao.RedisDao2;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Clivia-Han
 * @projectName: FoodCommoditySearch
 * @packageName: com.JingDong.JingDong
 * @Description:
 * @create: 2021-01-28-16:55
 */
@Slf4j
@SpringBootTest
public class ServerApplicationTest {
    @Resource
    private QueryMapper queryMapper;
    private Gson gson = new Gson();
    @Resource
    private RedisDao redisDao = new RedisDao();

    @Test
    public void contextLoads() {
        String query = "糖果";
//        List<String> keys = redisDao.getIDListOnPage(query,1);
        List<String> keys = redisDao.getIDList(query);
        log.info("redis查询结果"+keys.toString());
        System.out.println("共"+keys.size()+"条");
        String jsonInfo = gson.toJson(queryMapper.selectQueryBySkuIds(keys));

//        Detail detail = detailMapper.selectDetailBySkuId(skuId);
//        String detail_json = gson.toJson(detail);
        System.out.println("查询结果："+jsonInfo);
    }
}
