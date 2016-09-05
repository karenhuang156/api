package com.example.gavin.uberapitest;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Gavin on 5/09/2016.
 */
public class OwnerDeserializer implements JsonDeserializer<Owner> {

    @Override
    public Owner deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
        JsonElement owner = je.getAsJsonObject().get("owner");

        return new Gson().fromJson(owner, Owner.class);
    }
}
