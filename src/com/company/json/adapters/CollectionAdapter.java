package com.company.json.adapters;

import com.company.json.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;

/**
 * Converts all objects that extends java.util.Collections to JSONArray.
 */
public class CollectionAdapter implements com.company.json.adapters.JsonDataAdapter<Collection> {
    @Override
    public Object toJson(Collection c) throws JSONException{
        //implement me
        JSONArray jsonArray = new JSONArray();
        c.stream().forEach(object -> jsonArray.put(JsonSerializer.serialize (object)));
        return jsonArray;
    }
}
