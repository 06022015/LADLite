package com.liquoratdoor.ladlite.service;

import java.util.List;
import java.util.Map;

/**
 * Created by ashqures on 8/4/16.
 */
public interface RestApi {

    /*rest api URL*/
    final static String RESPONSE_TYPE_JSON = "application/json";

    //final static String BASE_URL = "http://www.liquoratdoor.com/hangover/services";
    final static String BASE_URL = "http://104.155.202.8/hangover/services";
    //final static String BASE_URL = "http://10.0.2.2:8080/hangover/services";

    final static String USER_BASE_URL = BASE_URL+ "/user";
    final static String USER_ADDRESS_URL = USER_BASE_URL+ "/address";

    final static String BASE_ANN_URL = BASE_URL+"/ann";
    final static String LOGIN_URL = BASE_ANN_URL+"/login";
    final static String LOGOUT_URL = BASE_ANN_URL+"/logout";
    final static String IDENTIFY_USER_URL = BASE_ANN_URL+"/help/identify";


    final static String STORE_BASE_URL = BASE_URL+"/store";

    final static String STORE_OPEN_ORDER = STORE_BASE_URL+"/order";
    final static String STORE_ARCHIVE_ORDER = STORE_BASE_URL+"/order/archive";
    final static String STORE_ORDER_DETAIL = STORE_BASE_URL+"/order/PATH_PARAM";

    final static String STORE_ORDER_PROCESS = STORE_BASE_URL+"/order/process";


    /*rest header param*/
    final static String HEADER_PARAM_AUTHORIZATION = "Authorization";

    /*rest param name*/
    final static String NAME = "name";
    final static String MOBILE_NUMBER = "mobile";
    final static String USER_EMAIL = "email";
    final static String USERNAME = "username";
    final static String PASSWORD = "password";
    final static String DEVICE_ID = "deviceId";
    final static String DEVICE_PUSH_TOKEN = "pushToken";
    final static String ORDER_SATE = "state";
    final static String ORDER_ID = "orderId";

}
