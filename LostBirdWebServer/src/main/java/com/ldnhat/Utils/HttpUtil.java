package com.ldnhat.Utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtil {

    private String value;

    public HttpUtil(String value) {
        this.value = value;
    }

    public <T> T toModel(Class<T> tClass){
        try {
            return new ObjectMapper().readValue(value, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpUtil of(BufferedReader reader){
        StringBuilder json = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null){
                json.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return new HttpUtil(json.toString());
    }
}
