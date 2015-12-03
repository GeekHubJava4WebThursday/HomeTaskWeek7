package org.geekhub.json;

import org.geekhub.json.adapters.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;


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
     * @throws SecurityException
     */
    private static JSONObject toJsonObject(Object o) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();

        // iterate all fields and process them
        for (Field field: fields) {

            // check if field must be ignored
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            // get field name and value
            String name = null;
            Object value = null;
            try {
                field.setAccessible(true);
                name = field.getName();
                value = field.get(o);
            } catch (SecurityException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (field.isAnnotationPresent(UseDataAdapter.class)) {
                UseDataAdapter annotation = field.getAnnotation(UseDataAdapter.class);
                if (annotation.value().equals(ColorAdapter.class)) {
                    jsonObject.put(name, serialize((new ColorAdapter()).toJson((Color) value)));
                }
                if (annotation.value().equals(DateAdapter.class)) {
                    jsonObject.put(name, serialize((new DateAdapter()).toJson((Date) value)));
                }
                if (annotation.value().equals(MapAdapter.class)) {
                    jsonObject.put(name, serialize((new MapAdapter()).toJson((Map) value)));
                }
                if (annotation.value().equals(CollectionAdapter.class)) {
                    jsonObject.put(name, serialize((new CollectionAdapter()).toJson((Collection) value)));
                }
            } else {
                jsonObject.put(name, serialize(value));
            }
        }
        return jsonObject;
    }
}
