 

package com.axway.mbaas_preprod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

public class JSONUtil {

    private static GsonBuilder gsonBuilder;

    static {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    public static GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public static Gson getGson() {
        return gsonBuilder.create();
    }

    public static String serialize(Object obj) {
        return getGson().toJson(obj);
    }

    public static <T> T deserialize(String jsonString, Type t) {
        return getGson().fromJson(jsonString, t);
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            getGson().fromJson(jsonInString, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    public static JSONObject stringToJSONObject(String stringToParse) throws IOException, JSONException {
        return new JSONObject(stringToParse);
    }
}
