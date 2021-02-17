package com.trace.trace.dao;

import com.google.gson.Gson;
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

    Gson gson = new Gson();
    MongoDBUtil mongoDBUtil = new MongoDBUtil();
    public  MongoDatabase database;
    public  MongoCollection<Document> collection;
    public  Document document;

    /**
     * 根据品类查询知识图谱
     * @param kind
     * @return
     */
    public String getGraphByKind(String kind)
    {
        database= MongoDBUtil.getConnect("trace");
        collection=database.getCollection("Graph");
        document = collection.find(eq("keyword", kind)).first();
        return document.toJson();
    }

    /**
     * 根据品牌查询知识图谱
     * @param brand
     * @return
     */
    public  String getGraphByBrand(String brand)
    {
        StringBuilder nodesSB = new StringBuilder();
        StringBuilder linkSB = new StringBuilder();
        String categoryStr = null;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        BasicDBObject nodeObj = new BasicDBObject("nodesMap", new BasicDBObject("$elemMatch", new BasicDBObject("name", new BasicDBObject("$regex", brand))));
        BasicDBObject keywordObj1 = new BasicDBObject("keyword", new BasicDBObject("$ne","生鲜"));
        BasicDBObject keywordObj2 = new BasicDBObject("keyword", new BasicDBObject("$ne","食品饮料、保健食品"));
        BasicDBObject andObj = new BasicDBObject("$and", Arrays.asList(nodeObj,keywordObj1,keywordObj2));
        MongoCursor<Document> cursor = collection.find(andObj).iterator();
        while (cursor.hasNext()){
            document = cursor.next();
            String totalStr = document.toJson();
            String str1 = totalStr.split("\", \"nodesMap\" : \\[")[1];
            String nodeStr = str1.split("], \"linksMap\" : \\[")[0];
            nodesSB.append(" ");
            nodesSB.append(nodeStr);
            nodesSB.append(",");
            String str2 = str1.split("], \"linksMap\" : \\[")[1];
            String linkStr = str2.split("], \"categoriesMap\" : \\[")[0];
            linkSB.append(linkStr);
            linkSB.append(",");
            categoryStr = " \"categories\" : ["+str2.split("], \"categoriesMap\" : \\[")[1];
        }
        log.info("node"+nodesSB.toString());
        log.info("link"+linkSB.toString());
        String[] singleNodes = nodesSB.toString().split("},");
        singleNodes = unique(singleNodes);
        String[] singleLinks = linkSB.toString().split("},");
        singleLinks = unique(singleLinks);
        sb.append("\"nodes\" :");
        sb.append(" [");
        for (int i=0; i<singleNodes.length; i++)
        {
            sb.append(singleNodes[i]);
            sb.append("},");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("], ");
        sb.append("\"links\" :");
        sb.append(" [");
        for (int j=0; j<singleLinks.length; j++)
        {
            sb.append(singleLinks[j]);
            sb.append("},");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("], ");
        sb.append(categoryStr);
        sb.append("}");
        sb.deleteCharAt(sb.lastIndexOf("}"));
        return sb.toString();
    }

    /**
     * 对字符串数组进行去重
     * @param arr
     * @return
     */
    public static String[] unique(String[] arr){
        //用来记录去除重复之后的数组长度和给临时数组作为下标索引
        int t = 0;
        //临时数组
        String[] tempArr = new String[arr.length];
        //遍历原数组
        for(int i = 0; i < arr.length; i++){
            //声明一个标记，并每次重置
            boolean isTrue = true;
            //内层循环将原数组的元素逐个对比
            for(int j=i+1;j<arr.length;j++){
                //如果发现有重复元素，改变标记状态并结束当次内层循环
                if(arr[i].equals(arr[j])){
                    isTrue = false;
                    break;
                }
            }
            //判断标记是否被改变，如果没被改变就是没有重复元素
            if(isTrue){
                //没有元素就将原数组的元素赋给临时数组
                tempArr[t] = arr[i];
                //走到这里证明当前元素没有重复，那么记录自增
                t++;
            }
        }
        //声明需要返回的数组，这个才是去重后的数组
        String[]  newArr = new String[t];
        //用arraycopy方法将刚才去重的数组拷贝到新数组并返回
        System.arraycopy(tempArr,0,newArr,0,t);
        return newArr;
    }
}
