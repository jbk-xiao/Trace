package com.trace.trace.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.trace.trace.util.MongoDBUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Filters.eq;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github - 副本
 * @packageName: com.trace.trace.dao
 * @Description:
 * @create: 2021-02-04-18:40
 */
@Slf4j
@Service
public class MongoDao {
//    @Resource
//    private MongoTemplate mongoTemplate;

    MongoDBUtil mongoDBUtil = new MongoDBUtil();
    public  MongoDatabase database;
    public  MongoCollection<Document> collection;
    public  Document document;
    public String getGraph(String kind)
    {
//        Query query = new Query(Criteria.where("keyword").is(kind));
//        List<Graph> graphs = mongoTemplate.find(query,Graph.class);
//        return graphs;
        database= MongoDBUtil.getConnect("trace");
        collection=database.getCollection("Graph");
        document = collection.find(eq("keyword", kind)).first();
        return document.toJson();
    }
}
