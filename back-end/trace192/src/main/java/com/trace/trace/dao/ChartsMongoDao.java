package com.trace.trace.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.regex;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.dao
 * @Description
 * @create 2021-02-23-17:43
 */
@Slf4j
@Component
public class ChartsMongoDao {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    @Autowired
    public ChartsMongoDao(MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
    }

    public String getPredictData(String companyName) {
        StringBuilder result = new StringBuilder();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("predict");
        for (Document document : collection.find(regex("company_name", companyName))) {
            String docJson = document.toJson();
            result.append(docJson);
        }
        return result.toString();
    }

    public String getNewsData(String companyName) {
        StringBuilder result = new StringBuilder();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("news");
        for (Document document : collection.find(regex("company_name", companyName))) {
            String docJson = document.toJson();
            result.append(",").append(docJson);
        }
        try {
            result.deleteCharAt(result.indexOf(","));
        } catch (StringIndexOutOfBoundsException e) {
            log.warn("{} has no news.", companyName);
        }
        return "[" + result.toString() + "]";
    }

    public String getIndexPredict(String keyword) {
        StringBuilder result = new StringBuilder();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("index");
        for (Document document : collection.find(regex("key", keyword))) {
            String docJson = document.toJson();
            result.append(docJson);
        }
        log.info("getIndexPredict: {}chars", result.length());
        return result.toString();
    }

    public String getCommentStatistic(String skuId) {
        StringBuilder result = new StringBuilder();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("comment_statistic");
        for (Document document : collection.find(regex("sku_id", skuId))) {
            Object str = document.get("data");
            result.append(new Gson().toJson(str));
        }
        log.info("getCommentStatistic: {}chars", result.length());
        return result.toString();
    }
}
