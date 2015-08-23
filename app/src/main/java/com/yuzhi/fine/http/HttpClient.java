package com.yuzhi.fine.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yuzhi.fine.R;
import com.yuzhi.fine.common.AppContext;
import com.yuzhi.fine.model.SearchParam;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiansj on 15/2/27.
 */
public class HttpClient {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static final String USER_NAME = "tiansj";
    public static final String UTF_8 = "UTF-8";
    public static final int PAGE_SIZE = 50;

    private static final String HTTP_DOMAIN = "http://sye.zhongsou.com/ent/rest";
    private static final String SHOP_RECOMMEND = "dpSearch.recommendShop"; // 推荐商家

    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param httpResponseHandler
     */
    public static void enqueue(Request request, final AsyncHttpResponseHandler httpResponseHandler) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                httpResponseHandler.sendSuccessMessage(response);
            }

            @Override
            public void onFailure(Request request, IOException e) {
                httpResponseHandler.sendFailureMessage(request, e);
            }
        });
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     *
     * @param request
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response arg0) throws IOException {

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 这里使用了HttpClient的API。只是为了方便
     *
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, UTF_8);
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return url + "?" + formatParams(params);
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            Log.v("Connectivity", e.getMessage());
        }
        return false;
    }

    private static String encodeParams(Map<String, Object> params) {
        String param = "{}";
        if (params != null) {
            JSONObject json = new JSONObject();
            try {
                for (String key : params.keySet()) {
                    json.put(key, params.get(key));
                }
                Log.i("net_params", json.toString());
                param = Base64.encodeToString(json.toString().getBytes(UTF_8), Base64.DEFAULT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return param;
    }


    public static void get(String url, Map<String, Object> params, AsyncHttpResponseHandler httpResponseHandler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        List<BasicNameValuePair> rq = new ArrayList<BasicNameValuePair>();
        rq.add(new BasicNameValuePair("m", url));
        rq.add(new BasicNameValuePair("p", encodeParams(params)));

        Request request = new Request.Builder().url(attachHttpGetParams(HTTP_DOMAIN, rq)).build();
        enqueue(request, httpResponseHandler);
    }

    public static void getRecommendShops(SearchParam param, AsyncHttpResponseHandler httpResponseHandler) {
        param.setLat(39.982314);
        param.setLng(116.409671);
        param.setCity("beijing");
        param.setPsize(PAGE_SIZE);

        Map<String, Object> params = new HashMap<String, Object>();
        if (param.getCity() != null) {
            params.put("city", param.getCity());
        }
        if (param.getLat() != null) {
            params.put("lat", param.getLat());
        }
        if (param.getLng() != null) {
            params.put("lng", param.getLng());
        }
        if (param.getPno() != null) {
            params.put("pno", param.getPno());
        }
        if (param.getPsize() != null) {
            params.put("psize", param.getPsize());
        }
        get(SHOP_RECOMMEND, params, httpResponseHandler);
    }
}
