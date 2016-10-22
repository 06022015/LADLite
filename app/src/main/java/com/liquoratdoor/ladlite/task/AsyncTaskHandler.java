package com.liquoratdoor.ladlite.task;


import com.liquoratdoor.ladlite.interector.DefaultSubscriber;
import com.liquoratdoor.ladlite.presenter.Presenter;
import com.liquoratdoor.ladlite.service.RestApi;

import org.json.JSONObject;

/**
 * Created by ashqures on 8/12/16.
 */
public class AsyncTaskHandler {


    public static CommonTask getSignInTask(DefaultSubscriber<JSONObject> subscriber){
        CommonTask loginTask = new CommonTask(subscriber);
        loginTask.setURL(RestApi.LOGIN_URL);
        loginTask.setMethod(Presenter.Method.POST);
        return loginTask;
    }


    public static CommonTask getIdentifyTask(DefaultSubscriber<JSONObject> subscriber){
        CommonTask identifyTask = new CommonTask(subscriber);
        identifyTask.setURL(RestApi.IDENTIFY_USER_URL);
        identifyTask.setMethod(Presenter.Method.POST);
        return identifyTask;
    }


    public static CommonTask getSignOutTask(DefaultSubscriber<JSONObject> subscriber){
        CommonTask signOutTask = new CommonTask(subscriber);
        signOutTask.setURL(RestApi.LOGOUT_URL);
        return signOutTask;
    }

    public static  CommonTask getOrderTask(DefaultSubscriber<JSONObject> subscriber){
        CommonTask orderTask = new CommonTask(subscriber);
        orderTask.setURL(RestApi.STORE_OPEN_ORDER);
        return orderTask;
    }


    public static  CommonTask getOrderDetailTask(DefaultSubscriber<JSONObject> subscriber, Long orderId){
        String url  = RestApi.STORE_ORDER_DETAIL;
        url = url.replaceFirst("PATH_PARAM", orderId+"");
        CommonTask orderTask = new CommonTask(subscriber);
        orderTask.setURL(url);
        return orderTask;
    }


    public static  CommonTask getOrderProcessTask(DefaultSubscriber<JSONObject> subscriber){
        String url  = RestApi.STORE_ORDER_PROCESS;
        CommonTask orderTask = new CommonTask(subscriber);
        orderTask.setMethod(Presenter.Method.POST);
        orderTask.setURL(url);
        return orderTask;
    }
}
