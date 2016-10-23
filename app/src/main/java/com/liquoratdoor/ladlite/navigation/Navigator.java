/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liquoratdoor.ladlite.navigation;

import android.content.Context;
import android.content.Intent;

import com.liquoratdoor.ladlite.activity.HomeActivity;
import com.liquoratdoor.ladlite.activity.LoginActivity;
import com.liquoratdoor.ladlite.activity.OrderDetailActivity;

import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    public Navigator() {
        //empty
    }

    public void navigateToLogin(Context context) {
        if (null != context) {
            Intent intentToLaunch = LoginActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToHome(Context context) {
        if(null!=context){
            Intent intentToLaunch = HomeActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }


    public void navigateToOrderDetail(Context context, Long orderId) {
        if(null!=context){
            Intent intentToLaunch = OrderDetailActivity.getCallingIntent(context, orderId);
            context.startActivity(intentToLaunch);
        }
    }


}
