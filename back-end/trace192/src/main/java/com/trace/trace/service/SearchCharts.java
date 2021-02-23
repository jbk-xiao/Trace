package com.trace.trace.service;

import com.trace.trace.dao.MongoSearchChartsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.service
 * @Description
 * @create 2021-02-23-17:05
 */
@Slf4j
@Component
public class SearchCharts {
    final MongoSearchChartsDao mongoSearchChartsDao;
    @Autowired
    public SearchCharts(MongoSearchChartsDao mongoSearchChartsDao) {
        this.mongoSearchChartsDao = mongoSearchChartsDao;
    }

    public String getPredictData(String companyName) {

        return mongoSearchChartsDao.getPredictData(companyName);
    }

    public String getNewsData(String companyName) {
        return mongoSearchChartsDao.getNewsData(companyName);
    }
}
