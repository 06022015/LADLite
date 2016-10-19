package com.liquoratdoor.ladlite.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashqures on 8/7/16.
 */
public class StatusDTO {

    private Integer code;
    private String message;
    private List<String> errors;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error){
        if(null==getErrors())
            setErrors(new ArrayList<String>());
        getErrors().add(error);
    }
}
