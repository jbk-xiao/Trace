package com.trace.trace.util;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github - 副本
 * @packageName: com.trace.trace.util
 * @Description:
 * @create: 2021-02-06-10:29
 */
@Slf4j
public class MongoDBUtil {
    /**
     * @return Mongodb的连接
     */
    public static MongoClient getConn(){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient("localhost", 27017);
        }catch (Exception e){
            log.error(e.getClass().getName()+": "+e.getMessage());
        }
        return mongoClient;

    }
}
