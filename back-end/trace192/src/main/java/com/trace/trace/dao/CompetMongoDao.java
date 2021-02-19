package com.trace.trace.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.trace.trace.util.MongoDBUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.dao
 * @Description
 * @create 2021-02-19-4:22 下午
 */
@Slf4j
@Component
public class CompetMongoDao {
    private static MongoCollection<Document> collection;
    private static StringBuilder sb;


    public String getCommentStatistic(String sku_id){
        MongoClient mongoClient=null;
       try{
           mongoClient = MongoDBUtil.getConn();
           collection = mongoClient.getDatabase("trace").getCollection("comment_statistic");
           sb= new StringBuilder();
           BasicDBObject sku = new BasicDBObject("sku_id",sku_id);
           Document originDoc = collection.find(sku).first();
           Document extractedDoc = new Document();
           if (originDoc!=null&&!originDoc.isEmpty()) {
               extractedDoc.put("comment_statistic",originDoc.get("data"));
               sb.append(extractedDoc.toJson());
           }else{
               sb.append("{}");
               log.info("not found");
           }
           sb.deleteCharAt(0);
           log.info("has already comment_statistic from MongoDB");
       }finally {
           mongoClient.close();
       }
        return sb.toString();
    }
}
