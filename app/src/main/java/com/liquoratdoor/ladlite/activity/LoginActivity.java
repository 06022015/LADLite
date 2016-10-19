package com.liquoratdoor.ladlite.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.liquoratdoor.ladlite.component.AuthComponent;
import com.liquoratdoor.ladlite.component.DaggerAuthComponent;
import com.liquoratdoor.ladlite.component.HasComponent;
import com.liquoratdoor.ladlite.dto.UserDTO;
import com.liquoratdoor.ladlite.fragment.AuthFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashqures on 8/17/16.
 */
public class LoginActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, HasComponent<AuthComponent>, AuthFragment.AuthListener {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private AuthComponent component;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        if(!sessionManager.isLoggedIn()){
            setContentView(R.layout.main_layout);
            this.initializeInjector();
            if (savedInstanceState == null)
                addFragment(R.id.fragmentLayoutContainer, new AuthFragment());
        }else{
           // navigator.navigateToHome(this);
        }
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


    private void initializeInjector() {
        this.component = DaggerAuthComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public AuthComponent getComponent() {
        return this.component;
    }

    @Override
    public void onSignIn(UserDTO userDTO) {
        handleLoginSession(userDTO);
       // this.navigator.navigateToHome(this);
    }

    private void onAuthSuccess(UserDTO userDTO){
        handleLoginSession(userDTO);
       // this.navigator.navigateToHome(this);
    }

    private void handleLoginSession(UserDTO userDTO){
        sessionManager.createLoginSession(userDTO);
    }


    @Override
    public void onIdentify() {
        //TODO: Need to implement the scenario where to navigate if identify user execution done.
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}
