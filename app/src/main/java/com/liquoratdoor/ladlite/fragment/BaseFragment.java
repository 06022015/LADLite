/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.liquoratdoor.ladlite.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;


import com.liquoratdoor.ladlite.util.FlipAnimationUtil;
import com.liquoratdoor.ladlite.view.LoadDataView;

/*import javax.inject.Inject;*/

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment implements LoadDataView {

    protected static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 200;


   /* @Inject
    SessionManager sessionManager;*/

    protected Dialog dialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showSnackBar(view);
    }

    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean checkSelfPermission(String permission, int resultCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{permission}, resultCode);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        showToastMessage("Some Permission is Denied");
                    }
                }
            }
            break;
            case MY_PERMISSIONS_REQUEST_READ_LOCATION:{
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        showToastMessage("Some Permission is Denied");
                    }
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void slideUp(View view) {
        view.setAnimation(FlipAnimationUtil.slideUp());
        //view.setVisibility(View.GONE);
    }

    protected void slideDown(View view) {
        view.setAnimation(FlipAnimationUtil.slideDown());
        //view.setVisibility(View.VISIBLE);
    }





    private void showSnackBar(View view){
        /*Snackbar snackbar = Snackbar
                .make(view, "Delivery ZipCode is "+ sessionManager.getStringValue(SessionManager.KEY_ZIP_CODE), Snackbar.LENGTH_LONG)
                .setAction("Change", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLocationDialog("560075");
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.show();*/
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }


    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
