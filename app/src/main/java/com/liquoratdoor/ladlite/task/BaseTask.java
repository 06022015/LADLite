package com.liquoratdoor.ladlite.task;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.liquoratdoor.ladlite.auth.SessionManager;
import com.liquoratdoor.ladlite.dto.DataMapper;
import com.liquoratdoor.ladlite.dto.StatusDTO;
import com.liquoratdoor.ladlite.exception.DefaultErrorBundle;
import com.liquoratdoor.ladlite.exception.NetworkConnectionException;
import com.liquoratdoor.ladlite.exception.RepositoryErrorBundle;
import com.liquoratdoor.ladlite.interector.DefaultSubscriber;
import com.liquoratdoor.ladlite.presenter.Presenter;
import com.liquoratdoor.ladlite.service.ApiConnection;
import com.liquoratdoor.ladlite.service.RestResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.HttpStatus;


/**
 * Created by ashqures on 8/12/16.
 */
public abstract class BaseTask<R,T> extends AsyncTask<R, Void, AsyncTaskResult<T>> {


    protected Context context;

    protected SessionManager sessionManager;


    protected DataMapper dataMapper;

    public BaseTask(Context context){
        this.context = context;
        this.sessionManager = new SessionManager(context);
        this.dataMapper = new DataMapper();
    }

    protected boolean isThereInternetConnection() {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }


    public AsyncTaskResult<JSONObject> handle(Map<String,String> param){
        AsyncTaskResult<JSONObject> asyncTaskResult;
        if (isThereInternetConnection()) {
            try {
                RestResponse restResponse = getDataFromApi(param);
                Log.i("Rest Response",restResponse.getResponse());
                if (restResponse.getCode().equals(HttpStatus.SC_OK)) {
                    asyncTaskResult = new AsyncTaskResult<JSONObject>(transform(restResponse.getResponse()));
                } else {
                    StatusDTO statusDTO = this.dataMapper.transformStatus(transform(restResponse.getResponse()));
                    asyncTaskResult = new AsyncTaskResult<JSONObject>(new DefaultErrorBundle(statusDTO));
                }
            }catch (RepositoryErrorBundle re){
                asyncTaskResult = new AsyncTaskResult<>(re);
            }catch (Exception e) {
                e.printStackTrace();
                asyncTaskResult = new AsyncTaskResult<>(new DefaultErrorBundle(e));
            }
        } else {
            asyncTaskResult = new AsyncTaskResult<>(new NetworkConnectionException());
        }
        return asyncTaskResult;
    }


    public abstract DefaultSubscriber<T> getSubscriber();


    public abstract RestResponse getDataFromApi(Map<String,String> paramMap)throws RepositoryErrorBundle;

    protected void showToastMessage(String message) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
    }

    protected JSONObject transform(String response) throws JSONException {
        return new JSONObject(response);
    }

    protected ApiConnection.RequestMethod getConnectionMethod(Presenter.Method method){
        ApiConnection.RequestMethod requestMethod = ApiConnection.RequestMethod.GET;
        switch (method){
            case GET:requestMethod = ApiConnection.RequestMethod.GET;break;
            case POST:requestMethod = ApiConnection.RequestMethod.POST;
                break;
            case PUT:requestMethod = ApiConnection.RequestMethod.PUT;
                break;
            case DELETE:requestMethod = ApiConnection.RequestMethod.DELETE;
                break;
        }
        return requestMethod;
    }

}
