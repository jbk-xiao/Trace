package com.trace.trace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.config
 * @Description
 * @create 2021-03-04-21:09
 */
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String uri;


    @Bean
    MongoDatabaseFactory mongoDatabaseFactory() {
//        MongoClient mongoClient = MongoClients.create(uri);
//        return new SimpleMongoClientDatabaseFactory(mongoClient, "trace");
        return new SimpleMongoClientDatabaseFactory(uri);
    }
}
