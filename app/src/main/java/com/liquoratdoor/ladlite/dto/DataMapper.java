package com.liquoratdoor.ladlite.dto;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by ashqures on 10/19/16.
 */
public class DataMapper {


    @Inject
    public DataMapper(){}

    public UserDTO transformUser(JSONObject json)throws JSONException{
        UserDTO userDTO = new UserDTO();
        return userDTO;
    }

    public StatusDTO transformStatus(JSONObject json)throws JSONException{
        StatusDTO statusDTO = new StatusDTO();
        return statusDTO;
    }

}
