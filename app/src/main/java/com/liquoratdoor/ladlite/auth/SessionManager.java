package com.liquoratdoor.ladlite.auth;

import android.content.Context;
import android.content.SharedPreferences;


import com.liquoratdoor.ladlite.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ashqures on 1/23/16.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "liquoratdoor_staff";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Email address (make variable public to access from outside)
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_AGE_VERIFIED = "ageVerified";
    public static final String KEY_MOBILE_VERIFIED = "mobileVerified";
    public static final String KEY_EMAIL_VERIFIED = "emailVerified";
    public static final String KEY_ZIP_CODE = "zipCode";

    // Email address (make variable public to access from outside)
    public static final String KEY_TOKEN = "token";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Map<String,String> sessionParams){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        for(String key : sessionParams.keySet()){
            editor.putString(key, sessionParams.get(key));
        }
        //editor.putString(KEY_NAME, name);
        // Storing email in pref
        //editor.putString(KEY_EMAIL, email);
        //editor.putString(KEY_TOKEN, token);
        // commit changes
        editor.commit();
    }

    public void createLoginSession(UserDTO userDTO){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, userDTO.getName());
        editor.putString(KEY_EMAIL, userDTO.getEmail());
        editor.putString(KEY_TOKEN, userDTO.getToken());
        editor.putString(KEY_PHONE_NUMBER, userDTO.getMobile());
        editor.putBoolean(KEY_AGE_VERIFIED, userDTO.isAgeVerified());
        editor.putBoolean(KEY_EMAIL_VERIFIED, userDTO.isEmailVerified());
        editor.putBoolean(KEY_MOBILE_VERIFIED, userDTO.isMobileVerified());
        editor.commit();
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.remove("logged");
        editor.clear();
        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_PHONE_NUMBER, pref.getString(KEY_PHONE_NUMBER, null));

        // return user
        return user;
    }


    public boolean checkPermission(String key){
        return pref.getBoolean(key, false);
    }

    public void updatePermission(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getAuthToken(){
      return pref.getString(KEY_TOKEN, null);
    }

    /**
     * Clear session details
     * */

    public void add(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key){
        return pref.getString(key, null);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
