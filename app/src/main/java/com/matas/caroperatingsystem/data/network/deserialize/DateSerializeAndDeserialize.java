package com.matas.caroperatingsystem.data.network.deserialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.matas.caroperatingsystem.utils.DateTimeUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

public class DateSerializeAndDeserialize implements JsonDeserializer<Date> , JsonSerializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        try {
            return DateTimeUtils.convertTimeZone(DateTimeUtils.SERVER_DAY_FORMAT.parse(json.getAsString()), TimeZone.getDefault(), TimeZone.getTimeZone("GMT"));
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }


    @Override
    public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DateTimeUtils.SERVER_DAY_FORMAT.format(DateTimeUtils.convertTimeZone(date, TimeZone.getDefault(), TimeZone.getTimeZone("GMT"))));
    }
}