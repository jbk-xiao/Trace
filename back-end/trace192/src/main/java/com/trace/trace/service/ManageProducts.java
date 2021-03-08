package com.trace.trace.service;

import com.trace.trace.dao.ProductRedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理模块
 */
@Slf4j
@Service
public class ManageProducts {
    private final ProductRedisDao productRedisDao;

    @Autowired
    public ManageProducts(ProductRedisDao productRedisDao) {
        this.productRedisDao = productRedisDao;
    }

    public String addProduct(String regisId, String productName, String code) {
        String info = "";
        int flag = productRedisDao.insert(regisId, productName, code);
        if (flag == 1) {
            info = "添加产品成功";
        } else {
            info = "添加产品失败";
        }
        return info;
    }

    public String deleteProduct(String regisId, String productName) {
        String info = "";
        int flag = productRedisDao.delete(regisId, productName);
        if (flag == 1) {
            info = "删除产品成功";
        } else {
            info = "删除产品失败";
        }
        return info;
    }

    public String searchProducts(String regisId) {
        String res = productRedisDao.getAll(regisId);
        return res;
    }

}
