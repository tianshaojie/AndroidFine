package com.yuzhi.fine.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.common.AppContext;
import com.yuzhi.fine.model.SearchParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by tiansj on 15/2/27.
 */
public class HttpClient {

    private static final int CONNECT_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;
    private static final int MAX_REQUESTS_PER_HOST = 10;
    private static final String TAG = HttpClient.class.getSimpleName();
    private static final String UTF_8 = "UTF-8";
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain;");
    private static OkHttpClient client;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new LoggingInterceptor());
        client = builder.build();
        client.dispatcher().setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            Log.v("ConnectivityManager", e.getMessage());
        }
        return false;
    }

    public static void get(String url, Map<String, String> param, final HttpResponseHandler httpResponseHandler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        if(param != null && param.size() > 0) {
            url = url + "?" + mapToQueryString(param);
        }
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                httpResponseHandler.sendSuccessMessage(response);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                httpResponseHandler.sendFailureMessage(call.request(), e);
            }
        });
    }

    public static void post(String url, Map<String, String> param, final HttpResponseHandler handler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        String paramStr = "";
        if(param != null && param.size() > 0) {
            paramStr = url += mapToQueryString(param);;
            url = url + "?" + paramStr;
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE, paramStr);
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendSuccessMessage(response);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendFailureMessage(call.request(), e);
            }
        });
    }

    public static String mapToQueryString(Map<String, String> map) {
        StringBuilder string = new StringBuilder();
        /*if(map.size() > 0) {
            string.append("?");
        }*/
        try {
            for(Map.Entry<String, String> entry : map.entrySet()) {
                string.append(entry.getKey());
                string.append("=");
                string.append(URLEncoder.encode(entry.getValue(), UTF_8));
                string.append("&");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string.toString();
    }

    //*************************************************************//
    public static final int PAGE_SIZE = 30;
    private static final String HTTP_DOMAIN = "http://sye.zhongsou.com/ent/rest";
    private static final String SHOP_RECOMMEND = "dpSearch.recommendShop"; // 推荐商家
    public static void getRecommendShops(SearchParam param, HttpResponseHandler httpResponseHandler) {
        param.setLat(39.982314);
        param.setLng(116.409671);
        param.setCity("beijing");
        param.setPsize(PAGE_SIZE);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("city", param.getCity());
        params.put("lat", param.getLat());
        params.put("lng", param.getLng());
        params.put("pno", param.getPno());
        params.put("psize", param.getPsize());
        String paramStr = JSON.toJSONString(param);
        paramStr = Base64.encodeToString(paramStr.getBytes(), Base64.DEFAULT);

        HashMap<String, String> rq = new HashMap<>();
        rq.put("m", SHOP_RECOMMEND);
        rq.put("p", paramStr);
//        String url = HTTP_DOMAIN + "?" + URLEncodedUtils.format(rq, UTF_8);
        get(HTTP_DOMAIN, rq, httpResponseHandler);
    }
    //*************************************************************//
}
