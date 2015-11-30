package org.geekhub.json;

import org.geekhub.json.adapters.ColorAdapter;
import org.geekhub.json.adapters.JsonDataAdapter;
import org.geekhub.json.adapters.UseDataAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static Set<Class> simpleTypes = new HashSet<>(Arrays.asList(
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
        }

        try {
            return toJsonObject(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

        Class objectClass = o.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }
            if (field.isAnnotationPresent(UseDataAdapter.class)) {
                Annotation annotation = field.getAnnotation(UseDataAdapter.class);
                Class<JsonDataAdapter> adapter = (Class<JsonDataAdapter>) ((UseDataAdapter) annotation).value();

                Method method = adapter.getMethod("toJson", Object.class);
                Object object = method.invoke(adapter.newInstance(), field.get(o));

                jsonObject.put(field.getName(), object);
            } else {
                jsonObject.put(field.getName(), field.get(o));
            }
        }

        return jsonObject;
    }
}
