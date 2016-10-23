package com.liquoratdoor.ladlite.presenter;

import android.content.Context;


import com.liquoratdoor.ladlite.component.PerActivity;
import com.liquoratdoor.ladlite.dto.DataMapper;
import com.liquoratdoor.ladlite.dto.StatusDTO;
import com.liquoratdoor.ladlite.dto.UserDTO;
import com.liquoratdoor.ladlite.exception.DefaultErrorBundle;
import com.liquoratdoor.ladlite.exception.ErrorBundle;
import com.liquoratdoor.ladlite.exception.ErrorMessageFactory;
import com.liquoratdoor.ladlite.interector.DefaultSubscriber;
import com.liquoratdoor.ladlite.task.AsyncTaskHandler;
import com.liquoratdoor.ladlite.task.CommonTask;
import com.liquoratdoor.ladlite.view.AuthView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * Created by ashqures on 8/18/16.
 */
@PerActivity
public class AuthPresenter implements Presenter {

    private enum AuthTye{
        SIGNIN,  IDENTIFY
    }


    AuthView view;
    DataMapper dataMapper;
    CommonTask authTask;

    public AuthPresenter() {
        this.dataMapper = new DataMapper();
    }

    public void setView(AuthView authView){
        this.view = authView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if(null!= this.authTask){
            this.authTask.cancel(true);
        }
        this.view=null;
    }

    public void attemptSignIn(Map<String,String> requestParam){
        this.authTask = AsyncTaskHandler.getSignInTask(new AuthSubscriber(AuthTye.SIGNIN));
        this.authTask.execute(requestParam);
    }


    public void attemptIdentifyUser(Map<String,String> requestParam){
        this.authTask = AsyncTaskHandler.getIdentifyTask(new AuthSubscriber(AuthTye.IDENTIFY));
        this.authTask.execute(requestParam);
    }

    private void onSignInSuccess(UserDTO userDTO){
        this.view.handleSignIn(userDTO);
    }


    private void onIdentifyUserSuccess(StatusDTO statusDTO){
        this.view.handleIdentifyUser(statusDTO);
    }

    private void onSendOTPSuccess(StatusDTO statusDTO){
        this.view.handleIdentifyUser(statusDTO);
    }

    private void onVerifyUsernameSuccess(StatusDTO statusDTO){
        this.view.handleIdentifyUser(statusDTO);
    }

    private void showViewLoading() {
        this.view.showLoading();
    }

    private void hideViewLoading() {
        this.view.hideLoading();
    }

    private void showViewRetry() {
        this.view.showRetry();
    }

    private void hideViewRetry() {
        this.view.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.view.context(), errorBundle);
        this.view.showError(errorMessage);
    }

    private final class AuthSubscriber extends DefaultSubscriber<JSONObject> {

        private AuthTye authTye;

        public AuthSubscriber(AuthTye authTye){
            this.authTye = authTye;
        }

        @Override
        public Context getContext() {
            return AuthPresenter.this.view.context();
        }

        @Override
        public void onCompleted() {
            AuthPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            AuthPresenter.this.hideViewLoading();
            AuthPresenter.this.showErrorMessage((ErrorBundle) e);
            AuthPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(JSONObject response) {
            try{
                switch (authTye){
                    case SIGNIN:
                        AuthPresenter.this.onSignInSuccess(AuthPresenter.this.dataMapper.transformUser(response));
                        break;
                    case IDENTIFY:
                        AuthPresenter.this.onIdentifyUserSuccess(AuthPresenter.this.dataMapper.transformStatus(response));
                        break;
                }
            }catch (JSONException je){
                onError(new DefaultErrorBundle(je));
            }
        }
    }
}
