package com.liquoratdoor.ladlite.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.liquoratdoor.ladlite.activity.R;
import com.liquoratdoor.ladlite.component.AuthComponent;
import com.liquoratdoor.ladlite.dto.StatusDTO;
import com.liquoratdoor.ladlite.dto.UserDTO;
import com.liquoratdoor.ladlite.presenter.AuthPresenter;
import com.liquoratdoor.ladlite.service.RestApi;
import com.liquoratdoor.ladlite.util.CommonUtil;
import com.liquoratdoor.ladlite.util.FlipAnimationUtil;
import com.liquoratdoor.ladlite.util.ValidatorUtil;
import com.liquoratdoor.ladlite.view.AuthView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashqures on 8/18/16.
 */
public class AuthFragment extends BaseFragment implements AuthView {

    public interface AuthListener {
        void onSignIn(UserDTO userDTO);

        void onIdentify();
    }

    private AuthListener listener;

    @Inject
    AuthPresenter presenter;

    @Bind(R.id.flipper)
    ViewFlipper flipper;

    @Bind(R.id.forgot_password_link)
    TextView forgotPasswordLink;

    @Bind(R.id.login_from_forgot_password_link)
    TextView loginFromForgotPasswordLink;

    /*Sign In Field*/
    @Bind(R.id.j_username)
    AutoCompleteTextView j_username;

    @Bind(R.id.j_password)
    EditText j_password;

    @Bind(R.id.sign_in_button)
    Button signInButton;


    /*Forgot Password Field*/
    @Bind(R.id.j_identify_username)
    AutoCompleteTextView identifyUsername;

    @Bind(R.id.forgot_password_button)
    Button forgotPasswordButton;

    public AuthFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthListener) {
            this.listener = (AuthListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(AuthComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_auth_flipper, container, false);
        ButterKnife.bind(this, fragmentView);
        setUpView();
        return fragmentView;
    }

    private void setUpView() {
        if (null != this.forgotPasswordLink)
            this.forgotPasswordLink.setOnClickListener(new FlipperClickViewListener());
        if (null != this.loginFromForgotPasswordLink)
            this.loginFromForgotPasswordLink.setOnClickListener(new FlipperClickViewListener());
        if (null != this.signInButton)
            this.signInButton.setOnClickListener(new AuthButtonClickViewListener());
        if (null != this.forgotPasswordButton)
            this.forgotPasswordButton.setOnClickListener(new AuthButtonClickViewListener());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter.setView(this);
        checkSelfPermission(Manifest.permission.READ_PHONE_STATE, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        if (savedInstanceState == null) {
            // this.loadItemList();
        }
    }

    private class FlipperClickViewListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_from_forgot_password_link:
                    showSignInForm();
                    break;
                case R.id.forgot_password_link:
                    showForgotPasswordForm();
                    break;
                default:
                    showSignInForm();
                    break;
            }
        }
    }

    private void showSignInForm() {
        this.flipper.setInAnimation(FlipAnimationUtil.inFromLeftAnimation());
        this.flipper.setOutAnimation(FlipAnimationUtil.outToRightAnimation());
        this.flipper.setDisplayedChild(flipper.indexOfChild(getActivity().findViewById(R.id.login)));
    }

    private void showForgotPasswordForm() {
        this.flipper.setInAnimation(FlipAnimationUtil.inFromRightAnimation());
        this.flipper.setOutAnimation(FlipAnimationUtil.outToLeftAnimation());
        this.flipper.setDisplayedChild(flipper.indexOfChild(getActivity().findViewById(R.id.forgot_password)));
    }


    private class AuthButtonClickViewListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    attemptSignIn();
                    break;
                case R.id.forgot_password_button:
                    attemptIdentifyUser();
                    break;
            }
        }
    }

    private void attemptSignIn() {
        if (isValidateSignInForm()) {
            Map<String, String> formAttr = new HashMap<String, String>();
            formAttr.put(RestApi.USERNAME, j_username.getText().toString());
            formAttr.put(RestApi.PASSWORD, j_password.getText().toString());
            formAttr.put(RestApi.DEVICE_ID, CommonUtil.getDeviceInfo(context()).get("deviceId"));
            this.presenter.attemptSignIn(formAttr);
        }
    }

    private void attemptIdentifyUser() {
        if (isValidateForgotPasswordForm()) {
            Map<String, String> formAttr = new HashMap<String, String>();
            formAttr.put(RestApi.USERNAME, identifyUsername.getText().toString());
            formAttr.put(RestApi.DEVICE_ID, CommonUtil.getDeviceInfo(context()).get("deviceId"));
            this.presenter.attemptIdentifyUser(formAttr);
        }
    }

    @Override
    public void handleSignIn(UserDTO userDTO) {
        this.listener.onSignIn(userDTO);
        showToastMessage(userDTO.getName());
    }

    @Override
    public void handleIdentifyUser(StatusDTO statusDTO) {
        this.listener.onIdentify();
        showToastMessage(statusDTO.getCode() + "");
    }

    private boolean isValidateSignInForm() {
        boolean isValid = true;
        String username = this.j_username.getText().toString();
        String passwordText = this.j_password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            this.j_username.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!ValidatorUtil.isEmailValid(username) && !ValidatorUtil.isMobileValid(username)) {
            this.j_username.setError(getString(R.string.error_invalid_username));
            isValid = false;
        }
        if (TextUtils.isEmpty(passwordText) || !ValidatorUtil.isPasswordValid(passwordText)) {
            this.j_password.setError(getString(R.string.error_invalid_password));
            isValid = false;
        }
        return isValid;
    }


    private boolean isValidateForgotPasswordForm() {
        boolean isValid = true;
        String username = this.identifyUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            this.identifyUsername.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!ValidatorUtil.isEmailValid(username) && !ValidatorUtil.isMobileValid(username)) {
            this.identifyUsername.setError(getString(R.string.error_invalid_username));
            isValid = false;
        }
        return isValid;
    }

}
