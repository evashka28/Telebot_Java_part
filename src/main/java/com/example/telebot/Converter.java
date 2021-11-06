package com.example.telebot;

import java.util.Map;

public class Converter {
    public static String mapToString(Map<String, Object> params){
        StringBuffer result = new StringBuffer();
        boolean first = true;
        for(Map.Entry<String, Object> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        return result.toString();
    }
}
