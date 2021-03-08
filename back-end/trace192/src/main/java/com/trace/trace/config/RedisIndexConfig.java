package com.trace.trace.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;

@Data
@Component
public class RedisIndexConfig {
    private HashMap<String, Integer> map;

    {
        ClassPathResource classPathResource = new ClassPathResource("redis_index.json");
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int n;
            while ((n = reader.read(buf)) > 0) {
                sb.append(new String(buf, 0, n));
            }
            String json = sb.toString();
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            map = gson.fromJson(json, type);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
