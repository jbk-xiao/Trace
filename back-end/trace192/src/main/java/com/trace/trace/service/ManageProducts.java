package com.trace.trace.service;

import com.trace.trace.dao.ProductRedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github-new
 * @packageName: com.trace.trace.service
 * @Description: 管理模块
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

    /**
     * 获取到所有的商品名称列表，用于商品名称选择
     *
     * @param regis_id
     * @return
     */
//    public String getAllProductNameList(String regis_id){
//        String res = productRedisDao.getAllProductName(regis_id).toString();
//        return res;
//    }
}
