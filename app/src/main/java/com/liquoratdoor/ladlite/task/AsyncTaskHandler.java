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
}
