package org.geekhub.test;

import org.geekhub.json.adapters.ColorAdapter;
import org.geekhub.json.adapters.UseDataAdapter;

import java.awt.*;

public class Paw {
    private Integer length;

    @UseDataAdapter(ColorAdapter.class)
    private Color color;

    public Paw(Integer length, Color color) {
        this.length = length;
        this.color = color;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}