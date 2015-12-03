package org.geekhub.json.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts object of type java.util.Date to String by using dd/MM/yyyy format
 */
public class DateAdapter implements JsonDataAdapter<Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Object toJson(Date date) {
        return dateFormat.format(date);
    }
}
