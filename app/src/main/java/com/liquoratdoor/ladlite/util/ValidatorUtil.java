package com.liquoratdoor.ladlite.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ashqures on 1/24/16.
 */
public class ValidatorUtil {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String ZIP_CODE_PATTERN = "\\d{6}";


    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public static boolean isMobileValid(String phoneNumber) {
        //TODO: Replace this with your own logic
        return phoneNumber.length() > 4;
    }

    public static boolean isDateValid(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidZipCode(String zipCode){
        return zipCode.matches(ZIP_CODE_PATTERN);
    }

    public static boolean isNotEmpty(String text){
        return null!= text && !"".equals(text);
    }
}
