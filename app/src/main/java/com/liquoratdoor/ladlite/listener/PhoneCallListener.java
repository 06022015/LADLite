package com.liquoratdoor.ladlite.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by ashqures on 10/22/16.
 */
public class PhoneCallListener extends PhoneStateListener {

    private boolean isPhoneCalling = false;

    String LOG_TAG = "LOGGING Liquor At Door";

    private Context context;

    public PhoneCallListener(Context context){
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        if (TelephonyManager.CALL_STATE_RINGING == state) {
            Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
        }
        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            Log.i(LOG_TAG, "OFFHOOK");
            isPhoneCalling = true;
        }
        if (TelephonyManager.CALL_STATE_IDLE == state) {
            Log.i(LOG_TAG, "IDLE");
            if (isPhoneCalling) {
                Log.i(LOG_TAG, "restart app");
                Intent i = this.context.getPackageManager().getLaunchIntentForPackage(this.context.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.context.startActivity(i);
                isPhoneCalling = false;
            }
        }
    }
}
