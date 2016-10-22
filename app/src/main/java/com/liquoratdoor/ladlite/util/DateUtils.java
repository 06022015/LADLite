package com.liquoratdoor.ladlite.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by ashqures on 10/22/16.
 */
public class DateUtils {


    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static Date convertStringToDate(String dateToConvert) throws DataFormatException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        sdf.setLenient(false);
        try {
            return sdf.parse(dateToConvert);
        } catch (ParseException e) {
            throw new DataFormatException("Unable to Parse date");
        }
    }

    public static String convertDateToString(Date dateToConvert){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        sdf.setLenient(false);
        return sdf. format(dateToConvert);
    }

}
