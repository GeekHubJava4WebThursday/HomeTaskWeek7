package org.geekhub.json;

import org.geekhub.test.Cat;
import org.geekhub.test.Paw;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.awt.*;
import java.util.*;

public class JsonSerializerTest {

    @Test
    public void serializeTomCatObject2Json() {
        final String tomCatObjectInJson = "{\"whiskers\":[1,2,3,4,5,6],\"color\":\"(128,128,128)\",\"name\":\"Tom\",\"paws\":{\"front-left\":{\"color\":\"(128,128,128)\",\"length\":23},\"back-right\":{\"color\":\"(128,128,128)\",\"length\":22},\"front-right\":{\"color\":\"(255,255,255)\",\"length\":24},\"back-left\":{\"color\":\"(0,0,0)\",\"length\":23}},\"birthDate\":\"09/03/2004\",\"age\":4}";

        Cat cat = new Cat();
        cat.setColor(Color.GRAY);
        cat.setAge(4);
        cat.setName("Tom");

        java.util.List<Integer> whiskers = cat.getWhiskers();
        whiskers.add(1);
        whiskers.add(2);
        whiskers.add(3);
        whiskers.add(4);
        whiskers.add(5);
        whiskers.add(6);

        cat.setBirthDate(new Date(1078790400000L));
        cat.getPaws().put("front-left", new Paw(23, Color.GRAY));
        cat.getPaws().put("front-right", new Paw(24, Color.WHITE));
        cat.getPaws().put("back-left", new Paw(23, Color.BLACK));
        cat.getPaws().put("back-right", new Paw(22, Color.GRAY));

        String objectInJson = JsonSerializer.serialize(cat).toString();
        assertTrue(tomCatObjectInJson.equals(objectInJson));
    }
}
