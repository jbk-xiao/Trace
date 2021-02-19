package com.trace.trace.service;

import com.trace.trace.dao.ProductRedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github-new
 * @packageName: com.trace.trace.service
 * @Description:
 * @create: 2021-02-19-16:54
 */
@Slf4j
@Component
public class ManageProducts {
    @Autowired
    ProductRedisDao productRedisDao;

    public String AddProduct(String key, String field, String value){
        String info = "";
        int flag = productRedisDao.insert(key, field, value);
        if(flag == 1){
            info = "添加产品成功";
        } else {
            info = "添加产品失败";
        }
        return info;
    }

    public String DeleteProduct(String key, String field){
        String info = "";
        int flag = productRedisDao.delete(key, field);
        if(flag == 1){
            info = "删除产品成功";
        } else {
            info = "删除产品失败";
        }
        return info;
    }

    public String SearchProducts(String key){
        String res = productRedisDao.getAll(key);
        return res;
    }
}
