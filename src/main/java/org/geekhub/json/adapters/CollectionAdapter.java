package org.geekhub.json.adapters;

import org.json.JSONException;

import java.util.Collection;

/**
 * Converts all objects that extends java.util.Collections to JSONArray.
 */
public class CollectionAdapter implements JsonDataAdapter<Collection> {
    @Override
    public Object toJson(Collection c) throws JSONException{
        //implement me
        return null;
    }
}
