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
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.dao
 * @Description
 * @create 2021-02-19-4:22 下午
 */
@Slf4j
@Component
public class CompetMongoDao {
    private final MongoDatabaseFactory mongoDatabaseFactory;

    @Autowired
    public CompetMongoDao(MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
    }

    public String getCommentStatistic(String skuId) {
        StringBuilder result = new StringBuilder();
        MongoCollection<Document> collection = mongoDatabaseFactory.getMongoDatabase()
                .getCollection("comment_statistic");
        for (Document document : collection.find(regex("sku_id", skuId))) {
            Object str = document.get("data");
            result.append(new Gson().toJson(str));
        }
        return result.toString();
    }
}
