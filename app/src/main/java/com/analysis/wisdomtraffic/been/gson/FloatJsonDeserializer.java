package com.analysis.wisdomtraffic.been.gson;

import com.analysis.wisdomtraffic.utils.TLog;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


/**
 * Created by qiujuer
 * on 2016/11/22.
 */
public class FloatJsonDeserializer implements JsonDeserializer<Float> {
    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsFloat();
        } catch (Exception e) {
            TLog.log("FloatJsonDeserializer-deserialize-error:" + (json != null ? json.toString() : ""));
            return 0F;
        }
    }
}
