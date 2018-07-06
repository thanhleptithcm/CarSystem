package com.matas.caroperatingsystem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matas.caroperatingsystem.data.network.deserialize.DateSerializeAndDeserialize;

public class GsonUtils {
    public static Gson getGson(Class clas) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.registerTypeHierarchyAdapter(DateSerializeAndDeserialize.class, new DateSerializeAndDeserialize());
        return gsonBuilder.create();
    }
}
