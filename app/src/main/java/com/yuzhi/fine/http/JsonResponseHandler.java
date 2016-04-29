package com.yuzhi.fine.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tiansj on 15/8/28.
 */
public abstract class JsonResponseHandler extends HttpResponseHandler {

    public void onSuccess(JSONObject response) {}

    public void onSuccess(int statusCode, Headers headers, JSONObject response) {
        onSuccess(statusCode, response);
    }

    public void onSuccess(int statusCode, JSONObject response) {
        onSuccess(response);
    }

    public void onFailure(Throwable e) {
        e.printStackTrace();
    }

    @Override
    protected void sendSuccessMessage(Response response) {
        try {
            sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{new Integer(response.code()), response.headers(), response.body().string()}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleSuccessMessage(int statusCode, Headers headers, String responseBody) {
        try {
            JSONObject result = JSON.parseObject(responseBody);
            onSuccess(result);
        } catch (Exception e) {
            onFailure(e);
        }
    }

    @Override
    protected void handleFailureMessage(Request request, IOException e) {
        onFailure(e);
    }
}
