package com.mp2i.colloscope;

import java.util.Calendar;

public class dateUtils {

    public static void setToFirstDayOfTheWeek(Calendar date) {
        int numberOfTheWeek = (date.get(Calendar.DAY_OF_WEEK) - 1) % 7; // because of american format, Sunday is 1 (I think ?)
        date.add(Calendar.DATE, -numberOfTheWeek + 1); // get to the first day of the week

    }
}
