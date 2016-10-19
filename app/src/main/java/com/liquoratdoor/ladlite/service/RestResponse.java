package com.liquoratdoor.ladlite.service;

/**
 * Created by ashqures on 8/7/16.
 */
public class RestResponse {

    private Integer code;
    private String message;
    private String response;

    public RestResponse(Builder builder){
        this.code = builder.code;
        this.message = builder.message;
        this.response = builder.response;
    }

    public static Builder builder(int code){
        return new Builder(code);
    }

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public static final class Builder{

        private Integer code;
        private String message;
        private String response;

        private Builder(int code){
            this.code = code;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder response(String response){
            this.response = response;
            return this;
        }

        public RestResponse build(){
            return new RestResponse(this);
        }

    }

}
