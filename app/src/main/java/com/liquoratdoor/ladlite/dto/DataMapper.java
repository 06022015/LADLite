package com.liquoratdoor.ladlite.dto;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by ashqures on 10/19/16.
 */
public class DataMapper {

    //{"id":3,"user":{"id":1,"mobile":null,"name":"Ashif Qureshi","email":"ashifqureshi15@gmail.com","password":null,"confirmPassword":null,"numberVerified":false,"emailVerified":false},"token":"c912edf350772cb3b24d8ef73ef99bad539af88fcc6081df93b6fde03900450af69a9449d846b1b081d43c84212270d10654aa8f382eca146f3bc4ccd0f75f9c"}


    public DataMapper() {
    }

    public UserDTO transformUser(JSONObject json) throws JSONException {
        UserDTO userDTO = new UserDTO();
        JSONObject user = json.getJSONObject("user");
        userDTO.setId(user.getLong("id"));
        userDTO.setName(user.getString("name"));
        if (user.has("mobile"))
            userDTO.setMobile(user.getString("mobile"));
        if (user.has("email"))
            userDTO.setEmail(user.getString("email"));
        userDTO.setMobileVerified(user.getBoolean("numberVerified"));
        userDTO.setEmailVerified(user.getBoolean("emailVerified"));
        userDTO.setToken(json.getString("token"));
        return userDTO;
    }

    public StatusDTO transformStatus(JSONObject json) throws JSONException {
        StatusDTO statusDTO = new StatusDTO();
        return statusDTO;
    }

}
