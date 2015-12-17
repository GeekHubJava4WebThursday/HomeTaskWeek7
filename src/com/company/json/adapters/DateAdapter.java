package com.company.json.adapters;

import sun.util.calendar.BaseCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts object of type java.util.Date to String by using dd/MM/yyyy format
 */
public class DateAdapter implements JsonDataAdapter<Date> {
    @Override
    public Object toJson(Date date) {
        //implement me
        DateFormat newFormat = new SimpleDateFormat();
        String formated = newFormat.format(date);
        return formated;
    }
}
