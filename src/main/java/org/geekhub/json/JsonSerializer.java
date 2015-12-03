package org.geekhub.json;

import org.geekhub.json.adapters.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.*;

import java.lang.reflect.Field;


/**
 * JsonSerializer converts Java objects to JSON representation.
 *
 */
public class JsonSerializer {

    /**
     * simpleTypes contains java classes for which we should not make any deeper serialization and we should return object as is
     * and use toString() method to get it serialized representation
     */
    private static Set<Class> simpleTypes = new HashSet<Class>(Arrays.asList(
            JSONObject.class,
            JSONArray.class,
            String.class,
            Integer.class,
            Short.class,
            Long.class,
            Byte.class,
            Double.class,
            Float.class,
            Character.class,
            Boolean.class,
            int.class,
            short.class,
            long.class,
            byte.class,
            double.class,
            float.class,
            char.class,
            boolean.class
    ));

    /**
     * Main method to convert Java object to JSON. If type of the object is part of the simpleTypes object itself will be returned.
     * If object is null String value "null" will be returned.
     * @param o object to serialize.
     * @return JSON representation of the object.
     */
    public static Object serialize(Object o) {
        if (null == o) {
            return "null";
        }
        if (simpleTypes.contains(o.getClass())) {
            return o;
        } else {
            try {
                return toJsonObject(o);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Converts Java object to JSON. Uses reflection to access object fields.
     * Uses JsonDataAdapter to serialize complex values. Ignores @Ignore annotated fields.
     * @param o object to serialize to JSON
     * @return JSON object.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static JSONObject toJsonObject(Object o) throws Exception {
        JSONObject jsonObject = new JSONObject();

        Class argClass = o.getClass();
        Field fields[] = argClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Ignore.class) || field.isSynthetic()) {
                continue;
            }

            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            Object fieldValue = field.get(o);

            UseDataAdapter dataAdapter = field.getAnnotation(UseDataAdapter.class);
            if (dataAdapter != null) {
                Class adapterClass = (Class) dataAdapter.value();
                jsonObject.put(fieldName, ((JsonDataAdapter) adapterClass.newInstance()).toJson(fieldValue));
            }

            if (Collection.class.isAssignableFrom(fieldType)) {
                jsonObject.put(fieldName, new CollectionAdapter().toJson((Collection) fieldValue));
            } else if (Map.class.isAssignableFrom(fieldType)) {
                jsonObject.put(fieldName, new MapAdapter().toJson((Map) fieldValue));
            } else if (fieldType.equals(Date.class)) {
                jsonObject.put(fieldName, new DateAdapter().toJson((Date) fieldValue));
            } else if (fieldType.equals(Color.class)) {
                jsonObject.put(fieldName, new ColorAdapter().toJson((Color) fieldValue));
            } else {
                jsonObject.put(fieldName, JsonSerializer.serialize(fieldValue));
            }
        }
        return jsonObject;
    }
}
