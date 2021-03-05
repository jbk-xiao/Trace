package com.trace.trace.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.regex;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.dao
 * @Description
 * @create 2021-02-23-17:43
 */
@Slf4j
@Repository
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
            result.append(document.toJson());
        }
        return result.toString();
    }

    public String getNewsData(String companyName) {
        StringBuilder result = new StringBuilder("[");
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("news");
        for (Document document : collection.find(regex("company_name", companyName))) {
            result.append(",").append(document.toJson());
        }
        try {
            result.deleteCharAt(result.indexOf(","));
        } catch (StringIndexOutOfBoundsException e) {
            log.warn("{} has no news.", companyName);
        } finally {
            result.append("]");
        }
        return  result.toString();
    }

    public String getIndexPredict(String keyword) {
        StringBuilder result = new StringBuilder();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("index");
        for (Document document : collection.find(regex("key", keyword))) {
            result.append(document.toJson());
        }
        log.info("getIndexPredict: {}chars", result.length());
        return result.toString();
    }

    public String getCommentStatistic(String skuId) {
        StringBuilder result = new StringBuilder();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("comment_statistic");
        for (Document document : collection.find(regex("sku_id", skuId))) {
            result.append(gson.toJson(document.get("data")));
        }
        log.info("getCommentStatistic: {}chars", result.length());
        return result.toString();
    }
}
