package com.github.chaudhryfaisal.wsk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    public static final Gson GSON = new GsonBuilder().create();

    public static <T> T deserialize(Object obj, Class<T> type) {
        return deserialize(serialize(obj), type);
    }

    public static <T> T deserialize(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    public static String serialize(Object o) {
        return GSON.toJson(o);
    }
}
