package com.globeandi.toeat.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

/*
When serialize a field that require a boolean
 */
public class BooleanDeserializer implements JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json.isJsonPrimitive()){
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isBoolean()){
                return primitive.getAsBoolean();
            }
            if (primitive.isNumber()){
                return primitive.getAsInt() == 1;
            }
            if (primitive.isString()){
                return "yes".equals(primitive.getAsString());
            }
        }
        throw new JsonParseException("Invalid boolean");
    }
}
