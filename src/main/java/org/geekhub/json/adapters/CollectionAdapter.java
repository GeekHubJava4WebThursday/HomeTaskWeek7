package org.geekhub.json.adapters;

import org.geekhub.json.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;

/**
 * Converts all objects that extends java.util.Collections to JSONArray.
 */
public class CollectionAdapter implements JsonDataAdapter<Collection> {
    @Override
    public Object toJson(Collection c) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Object element : c) {
            jsonArray.put(JsonSerializer.serialize(element));
        }
        return jsonArray;
    }
}
