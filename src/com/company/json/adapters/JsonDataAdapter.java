package com.company.json.adapters;

import org.json.JSONException;

/**
 * JsonDataAdapter contains instructions how to serialize Java object to Json representation.
 * @param <T> determines type adapter works with.
 */
public interface JsonDataAdapter<T> {
    Object toJson(T o) throws JSONException;
}
