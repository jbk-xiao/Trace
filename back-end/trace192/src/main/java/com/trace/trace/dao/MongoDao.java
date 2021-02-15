package com.trace.trace.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.trace.trace.util.MongoDBUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


/**
 * @author: Clivia-Han
 * @projectName: Foxishengcun-github
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

    /**
     * 根据品类查询知识图谱信息
     * @param kind
     * @return
     */
    public String getGraphByKind(String kind)
    {
//        Query query = new Query(Criteria.where("keyword").is(kind));
//        List<Graph> graphs = mongoTemplate.find(query,Graph.class);
//        return graphs;
        database= MongoDBUtil.getConnect("trace");
        collection=database.getCollection("Graph");
        document = collection.find(eq("keyword", kind)).first();
//        document = collection.find({$and:[{"nodesMap":{$elemMatch:{"name":"湾仔码头"}}},{"keyword":{$ne:"生鲜"}},{"keyword":{$ne:"食品饮料、保健食品"}}]}).pretty()
        return document.toJson();
    }

    /**
     * 根据品牌返回竞品的知识图谱信息
     * @param brand
     * @return
     */
    public  String getGraphByBrand(String brand)
    {
        ArrayList<DBObject> objList = new ArrayList<DBObject>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        database= MongoDBUtil.getConnect("trace");
        collection=database.getCollection("Graph");
        /*db.Graph.find(
         {$and:
            [
                {"nodesMap":{$elemMatch:{"name":"湾仔码头"}}},
                {"keyword":{$ne:"生鲜"}},
                {"keyword":{$ne:"食品饮料、保健食品"}}
            ]}
        ).pretty()
         */
        BasicDBObject nodeObj = new BasicDBObject("nodesMap", new BasicDBObject("$elemMatch", new BasicDBObject("name", new BasicDBObject("$eq", brand))));
        BasicDBObject keywordObj1 = new BasicDBObject("keyword", new BasicDBObject("$ne","生鲜"));
        BasicDBObject keywordObj2 = new BasicDBObject("keyword", new BasicDBObject("$ne","食品饮料、保健食品"));
        BasicDBObject andObj = new BasicDBObject("$and", Arrays.asList(nodeObj,keywordObj1,keywordObj2));
        MongoCursor<Document> cursor = collection.find(andObj).iterator();
        while (cursor.hasNext()){
            document = cursor.next();
            System.out.println(document.toJson());
            sb.append(document.toJson());
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");
        return sb.toString();
    }
}
