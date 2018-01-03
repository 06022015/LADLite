package com.liquoratdoor.ladlite.activity;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.liquoratdoor.ladlite.dto.StatusDTO;
import com.liquoratdoor.ladlite.dto.UserDTO;
import com.liquoratdoor.ladlite.presenter.AuthPresenter;
import com.liquoratdoor.ladlite.service.RestApi;
import com.liquoratdoor.ladlite.util.CommonUtil;
import com.liquoratdoor.ladlite.util.FlipAnimationUtil;
import com.liquoratdoor.ladlite.util.ValidatorUtil;
import com.liquoratdoor.ladlite.view.AuthView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ashqures on 8/17/16.
 */
public class LoginActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, AuthView {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private AuthPresenter presenter;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        if(!sessionManager.isLoggedIn()){
            setContentView(R.layout.main_layout);
            FrameLayout container = (FrameLayout) findViewById(R.id.fragmentLayoutContainer);
            if(null!=container){
                View inflatedLayout= getLayoutInflater().inflate(R.layout.fragment_auth_flipper, null, false);
                container.addView(inflatedLayout);
            }
            ButterKnife.bind(this);

            if (savedInstanceState == null)
                setUpView();
        }else{
            navigator.navigateToHome(this);
        }
    }

    @Override
    public void onPostCreate( Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        this.presenter = new AuthPresenter();
        this.presenter.setView(this);
        checkSelfPermission(Manifest.permission.READ_PHONE_STATE, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection, AutoCompleteTextView mEmailView) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private void onAuthSuccess(UserDTO userDTO){
        handleLoginSession(userDTO);
        this.navigator.navigateToHome(this);
    }

    private void handleLoginSession(UserDTO userDTO){
        sessionManager.createLoginSession(userDTO);
    }

    @Override
    public void handleSignIn(UserDTO userDTO) {
        showToastMessage(userDTO.getName());
        handleLoginSession(userDTO);
        this.navigator.navigateToHome(this);
    }

    @Override
    public void handleIdentifyUser(StatusDTO statusDTO) {
        //TODO: Need to implement the scenario where to navigate if identify user execution done.
        TextView textView = (TextView)findViewById(R.id.action_message);
        textView.setVisibility(View.VISIBLE);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
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
        this.flipper.setDisplayedChild(flipper.indexOfChild(this.findViewById(R.id.login)));
    }

    private void showForgotPasswordForm() {
        this.flipper.setInAnimation(FlipAnimationUtil.inFromRightAnimation());
        this.flipper.setOutAnimation(FlipAnimationUtil.outToLeftAnimation());
        this.flipper.setDisplayedChild(flipper.indexOfChild(this.findViewById(R.id.forgot_password)));
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
            FirebaseMessaging.getInstance().subscribeToTopic("Liquor");
            String token = FirebaseInstanceId.getInstance().getToken();
            Map<String, String> formAttr = new HashMap<String, String>();
            formAttr.put(RestApi.USERNAME, j_username.getText().toString());
            formAttr.put(RestApi.PASSWORD, j_password.getText().toString());
            formAttr.put(RestApi.DEVICE_ID, CommonUtil.getDeviceInfo(getApplicationContext()).get("deviceId"));
            formAttr.put(RestApi.DEVICE_PUSH_TOKEN, token);
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
