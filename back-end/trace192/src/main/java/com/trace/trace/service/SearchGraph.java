package com.trace.trace.service;

import com.trace.trace.dao.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github
 * @packageName: com.trace.trace.service
 * @Description:
 * @create: 2021-02-09-22:16
 */
public class SearchGraph {
    @Autowired
    MongoDao mongodbDao;

    public String searchGraph(String kind){
        return mongodbDao.getGraph(kind);
    }
}
