package org.geekhub.json;

import org.geekhub.json.adapters.JsonDataAdapter;
import org.geekhub.json.adapters.UseDataAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


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
        Class<?> objectClass = o.getClass();
        JSONObject jsonObject = new JSONObject();
        for(Field field : objectClass.getDeclaredFields()){
            field.setAccessible(true);

            if (null != field.getAnnotation(Ignore.class)) {
                continue;

            } else if (simpleTypes.contains(field.getType())) {
                jsonObject.put(field.getName(), field.get(o));

            } else if (null != field.getAnnotation(UseDataAdapter.class)) {
                Class<JsonDataAdapter> dataAdapter = (Class<JsonDataAdapter>)field
                                                                            .getAnnotation(UseDataAdapter.class)
                                                                            .value();
                jsonObject.put(field.getName(), dataAdapter.newInstance().toJson(field.get(o)));

            } else {
                jsonObject.put(field.getName(), toJsonObject(o));
            }
        }

        return jsonObject;
    }
}
