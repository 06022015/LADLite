package com.liquoratdoor.ladlite.util;

import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.liquoratdoor.ladlite.listener.PhoneCallListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ashqures on 6/29/16.
 */
public class CommonUtil {

    private static final String TELEPHONY_SERVICE = Context.TELEPHONY_SERVICE;

    public static Map<String,String> getDeviceInfo(Context context){
        TelephonyManager telephonyManager =  (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        Map<String,String> deviceInfoMap = new HashMap<>();
        deviceInfoMap.put("deviceId", telephonyManager.getDeviceId());
        deviceInfoMap.put("phoneNumber", telephonyManager.getLine1Number());
        deviceInfoMap.put("simSerialNumber", telephonyManager.getSimSerialNumber());
        return deviceInfoMap;
    }



    public static void attachPhoneListener(Context context){
        PhoneCallListener phoneListener = new PhoneCallListener(context);
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }


    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    private String getZipCodeFromLocation(Context context,Location location) {
        Address addr = getAddressFromLocation(context,location);
        return addr.getPostalCode() == null ? "" : addr.getPostalCode();
    }

    private Address getAddressFromLocation(Context context,Location location) {
        Geocoder geocoder = new Geocoder(context);
        Address address = new Address(Locale.getDefault());
        try {
            List<Address> addr = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addr.size() > 0) {
                address = addr.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static void handleDeliveryTime(TextView textView,Date orderPlaceTime){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderPlaceTime);
        calendar.add(Calendar.MINUTE, 45);
        DeliveryTimeCounter  counter = new DeliveryTimeCounter(textView, calendar.getTimeInMillis()- now.getTime());
        counter.start();
    }

}
