package com.cars.data.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static String dateFormat = "dd/MM/yyyy";
    private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    public static String now() {
        return sdf.format(Calendar.getInstance().getTime());
    }
    public static String format(Date date) {
        return sdf.format(date.getTime());
    }

}
