package com.rambabusaravanan.android.framework.network.base;

import com.google.gson.Gson;
import com.rambabusaravanan.android.framework.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Andro Babu
 */
public class BaseResponse implements Serializable {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject(toString());
        } catch (JSONException e) {
            Utils.log(e.getMessage());
            return new JSONObject();
        }
    }

    public static <T extends BaseResponse> T fromJsonString(String jsonString, Class<T> clazz) {
        return new Gson().fromJson(jsonString, clazz);
    }
}
