package com.liquoratdoor.ladlite.task;


import com.liquoratdoor.ladlite.exception.RepositoryErrorBundle;
import com.liquoratdoor.ladlite.interector.DefaultSubscriber;
import com.liquoratdoor.ladlite.presenter.Presenter;
import com.liquoratdoor.ladlite.service.ApiConnection;
import com.liquoratdoor.ladlite.service.RestApi;
import com.liquoratdoor.ladlite.service.RestResponse;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ashqures on 8/19/16.
 */
public class CommonTask extends BaseTask<Map<String,String>, JSONObject> {


    private DefaultSubscriber<JSONObject> subscriber;

    private String url;

    private Presenter.Method method = Presenter.Method.GET;

    public CommonTask(DefaultSubscriber<JSONObject> subscriber){
        super(subscriber.getContext());
        this.subscriber = subscriber;
    }

    public void setURL(String url){
        this.url = url;
    }

    public void setMethod(Presenter.Method method){
        this.method = method;
    }

    @Override
    public DefaultSubscriber<JSONObject> getSubscriber() {
        return subscriber;
    }

    @Override
    protected AsyncTaskResult<JSONObject> doInBackground(Map<String, String>... params) {
        AsyncTaskResult<JSONObject> asyncTaskResult;
        if(null!= params && params.length>0){
            asyncTaskResult = handle(params[0]);
        }else {
            asyncTaskResult = handle(null);
        }
        return asyncTaskResult;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<JSONObject> asyncTaskResult) {
        if(!isCancelled()){
            if(!asyncTaskResult.hasError()){
                this.subscriber.onNext(asyncTaskResult.getResult());
            }else{
                this.subscriber.onError(asyncTaskResult.getErrorBundle().getException());
            }
        }
        super.onPostExecute(asyncTaskResult);
    }

    public RestResponse getDataFromApi(Map<String,String> paramMap)throws RepositoryErrorBundle {
        System.out.println("Rest Url:- "+ this.url);
        ApiConnection apiConnection = new ApiConnection(this.url);
        if(null != sessionManager.getAuthToken()){
            apiConnection.addHeader(RestApi.HEADER_PARAM_AUTHORIZATION, sessionManager.getAuthToken());
        }
        if(null!=paramMap && paramMap.size()>0)
            apiConnection.addParams(paramMap);
        return apiConnection.execute(getConnectionMethod(this.method));
    }
}
