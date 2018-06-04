package com.app.pawapp.Util.Gson;

import com.app.pawapp.Util.Gson.Deserializer.WCFDateDeserializer;
import com.app.pawapp.Util.Gson.Serializer.WCFDateSerializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public final class GsonFactory {

    private GsonFactory(){
    }

    public static Gson getWCFGson(){
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new WCFDateSerializer())
                .registerTypeAdapter(Date.class, new WCFDateDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
    }

}
