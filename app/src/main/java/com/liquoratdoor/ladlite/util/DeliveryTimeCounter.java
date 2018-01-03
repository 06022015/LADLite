package com.liquoratdoor.ladlite.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by ashqures on 10/22/16.
 */
public class DeliveryTimeCounter extends CountDownTimer {


    //private static Long COUNT_DOWN_INTERVAL = 60000L;
    private static Long COUNT_DOWN_INTERVAL = 1000L;

    private TextView view;

    public DeliveryTimeCounter(TextView view, long millisInFuture) {
        super(millisInFuture, DeliveryTimeCounter.COUNT_DOWN_INTERVAL);
        this.view = view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Long hour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
        Long min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
        String hms = String.format("%02d:%02d:%02d", hour,min,seconds);
/*
        Long minuteLeft = millisUntilFinished/1000;
        int seconds = (int) (millisUntilFinished / 1000);
        int hour = seconds/(60*60);
        int minutes = seconds%60*60;

        seconds = seconds % 60;
*/
        this.view.setText(hms);
        if(hour<= 00 && min<10){
            this.view.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        /*if(millisUntilFinished<0)
            this.cancel();*/
    }

    @Override
    public void onFinish() {

    }
}
