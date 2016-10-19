package com.liquoratdoor.ladlite.service;


import com.liquoratdoor.ladlite.exception.RepositoryErrorBundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by ashqures on 1/24/16.
 */
public class ApiConnection {

    private static final String CONTENT_TYPE_ACCEPT_LABEL = "Accept";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
    private static final String CONTENT_TYPE_VALUE_FORM_URL_ENCODING = "application/x-www-form-urlencoded";

    private ArrayList<NameValuePair> params;
    private ArrayList <NameValuePair> headers;

    private String url;


    public ApiConnection(String url){
        this.url = url;
        params = new ArrayList<NameValuePair>();
        headers = new ArrayList<NameValuePair>();
        addHeader(CONTENT_TYPE_ACCEPT_LABEL, CONTENT_TYPE_VALUE_JSON);
    }

    public void addParam(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }

    public void addParams(Map<String,String> paramMap){
        for(String key : paramMap.keySet()){
            params.add(new BasicNameValuePair(key, paramMap.get(key)));
        }
    }

    public void addHeader(String name, String value){
        headers.add(new BasicNameValuePair(name, value));
    }

    public RestResponse execute(RequestMethod method)throws RepositoryErrorBundle {
        RestResponse restResponse;
        switch(method) {
            case GET:
            {
                //add parameters
                String combinedParams = "";
                if(!params.isEmpty()){
                    combinedParams += "?";
                    for(NameValuePair p : params){
                        final String encoding = "UTF-8";
                        String paramString = null;
                        try {
                            paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), encoding);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if(combinedParams.length() > 1){
                            combinedParams  +=  "&" + paramString;
                        } else{
                            combinedParams += paramString;
                        }
                    }
                }
                HttpGet request = new HttpGet(url + combinedParams);
                //add headers
                for(NameValuePair h : headers){
                    request.addHeader(h.getName(), h.getValue());
                }
                restResponse = executeRequest(request);
                break;
            }
            case POST:
            {
                addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_FORM_URL_ENCODING);
                HttpPost request = new HttpPost(url);
                //add headers
                for(NameValuePair h : headers){
                    request.addHeader(h.getName(), h.getValue());
                }
                if(!params.isEmpty()){
                    try {
                        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                restResponse = executeRequest(request);
                break;
            }
            default: restResponse = RestResponse.builder(HttpStatus.SC_METHOD_NOT_ALLOWED).build();
        }
        return restResponse;
    }

    private RestResponse executeRequest(HttpUriRequest request)throws RepositoryErrorBundle {
        HttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse;
        InputStream instream = null;
        RestResponse restResponse;
        try {
            httpResponse = client.execute(request);
            RestResponse.Builder responseBuilder = RestResponse.builder(httpResponse.getStatusLine().getStatusCode())
                    .message(httpResponse.getStatusLine().getReasonPhrase());
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                instream = entity.getContent();
                responseBuilder.response(convertStreamToString(instream));
            }
            restResponse = responseBuilder.build();
        } catch (ClientProtocolException e)  {
            throw new RepositoryErrorBundle(e, HttpStatus.SC_BAD_GATEWAY);
        }catch (IOException e) {
            throw new RepositoryErrorBundle(e, HttpStatus.SC_BAD_GATEWAY);
        } finally {
            client.getConnectionManager().shutdown();
            if(null!= instream)
                try {
                    instream.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
        }
        return restResponse;
    }


    /*private void connectToApi() {
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .get()
                .build();

        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient createClient() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
        okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);

        return okHttpClient;
    }*/


    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

}