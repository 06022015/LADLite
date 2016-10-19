package com.liquoratdoor.ladlite.util;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by ashqures on 8/17/16.
 */
public class FlipAnimationUtil {

    private static int ANIMATION_DURATION = 1000;

    public static Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
    public static Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    public static Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }
    public static Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
        );
        outtoRight.setDuration(500);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    public static Animation slideUp(){
        Animation slideUp = new TranslateAnimation(Animation.RELATIVE_TO_SELF, +1.0f, Animation.RELATIVE_TO_SELF, +1.0f,
                Animation.RELATIVE_TO_SELF, +1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        slideUp.setDuration(ANIMATION_DURATION);
        slideUp.setInterpolator(new AccelerateInterpolator());
        //slideUp.setInterpolator(R.anim.lin);
        return slideUp;
    }

    public static Animation slideDown(){
        Animation slideDown = new TranslateAnimation(Animation.RELATIVE_TO_SELF, +1.0f, Animation.RELATIVE_TO_SELF, +1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        slideDown.setDuration(ANIMATION_DURATION);
        slideDown.setInterpolator(new AccelerateInterpolator());
        return slideDown;
    }
}
