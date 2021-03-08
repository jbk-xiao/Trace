package com.trace.trace.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@Document("Graph")
public class Graph {
    private String keyword;
    private Map[] nodesMap;
    private Map[] linksMap;
    private Map[] categoriesMap;

    public Graph(String keyword, Map[] nodesMap, Map[] linksMap, Map[] categoriesMap) {
        this.keyword = keyword;
        this.nodesMap = nodesMap;
        this.linksMap = linksMap;
        this.categoriesMap = categoriesMap;
    }

    public Graph(String keyword, Map[] nodesMap, Map[] linksMap) {
        this.keyword = keyword;
        this.nodesMap = nodesMap;
        this.linksMap = linksMap;
        ArrayList<Map> mapList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "first_class");
        mapList.add(map);
        map = new HashMap<>();
        map.put("name", "second_class");
        mapList.add(map);
        map = new HashMap<>();
        map.put("name", "third_class");
        mapList.add(map);
        map = new HashMap<>();
        map.put("name", "brand");
        mapList.add(map);
        Map[] mapArray = new Map[mapList.size()];
        for (int n = 0; n < mapList.size(); n++) {
            mapArray[n] = mapList.get(n);
        }
        this.categoriesMap = mapArray;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Map[] getNodesMap() {
        return nodesMap;
    }

    public void setNodesMap(Map[] nodesMap) {
        this.nodesMap = nodesMap;
    }

    public Map[] getLinksMap() {
        return linksMap;
    }

    public void setLinksMap(Map[] linksMap) {
        this.linksMap = linksMap;
    }

    public Map[] getCategoriesMap() {
        return categoriesMap;
    }

    public void setCategoriesMap(Map[] categoriesMap) {
        this.categoriesMap = categoriesMap;
    }

    @Override
    public String toString() {
        return "GraphBean{" +
               "keyword='" + keyword + '\'' +
               ", nodesMap=" + Arrays.toString(nodesMap) +
               ", linksMap=" + Arrays.toString(linksMap) +
               ", categoriesMap=" + Arrays.toString(categoriesMap) +
               '}';
    }
}
