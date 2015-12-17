package com.company.json.adapters;

import com.company.json.JsonSerializer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Converts all objects that extends java.util.Map to JSONObject.
 */

public class MapAdapter implements JsonDataAdapter<Map> {
    @Override
    public Object toJson(Map map) throws JSONException {
        JSONObject m = new JSONObject();
        for (Object key : map.keySet()) {
            m.put(key.toString(), JsonSerializer.serialize(map.get(key)));
        }
        return m;
    }
}
