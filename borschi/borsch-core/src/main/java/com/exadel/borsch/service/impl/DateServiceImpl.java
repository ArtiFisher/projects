package com.exadel.borsch.service.impl;


import com.exadel.borsch.service.DateService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateServiceImpl implements DateService {

    private final String SEPARATOR = "-";

    @Override
    public String getDateByString(String date) {
        String resultDate;
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            resultDate = getCurrentDate();
        } else {
            try {
                resultDate = getStringDate(DATE_FORMAT.parse(date));
            } catch (ParseException e) {
                resultDate = getCurrentDate();
            }
        }
        return resultDate;

    }

    @Override
    public String getCurrentDate() {
        Calendar date = Calendar.getInstance();
        return getString(date);
    }

    private String getStringDate(Date d) {
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        return getString(date);
    }

    private String getString(Calendar date) {
        return String.valueOf(date.get(Calendar.YEAR)) + SEPARATOR +
                String.valueOf(date.get(Calendar.MONTH) + 1) + SEPARATOR +
                String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }


}
