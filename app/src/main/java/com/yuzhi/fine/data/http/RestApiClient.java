package com.yuzhi.fine.data.http;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.base.http.HttpClient;
import com.yuzhi.fine.base.http.HttpResponseHandler;
import com.yuzhi.fine.module.home.model.SearchParam;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tiansj on 16/2/3.
 */
public class RestApiClient {

    private static final String UTF_8 = "UTF-8";
    public static final int PAGE_SIZE = 30;
    private static final String HTTP_DOMAIN = "http://sye.zhongsou.com/ent/rest";
    private static final String SHOP_RECOMMEND = "dpSearch.recommendShop"; // 推荐商家

    private static final String HTTP_DOMAIN_ = HTTP_DOMAIN + ":8083";
    private static final String LOGIN = HTTP_DOMAIN_ + "/v1/api1/login";        // 密码登录

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

        List<BasicNameValuePair> rq = new ArrayList<BasicNameValuePair>();
        rq.add(new BasicNameValuePair("m", SHOP_RECOMMEND));
        rq.add(new BasicNameValuePair("p", paramStr));

        String url = HTTP_DOMAIN + "?" + URLEncodedUtils.format(rq, UTF_8);
        HttpClient.get(url, rq, httpResponseHandler);
    }

    public static void loginPassword(String mobile, String password, final HttpResponseHandler handler) {
        List<BasicNameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobile", mobile));
        pairs.add(new BasicNameValuePair("password", password));
        HttpClient.post(LOGIN, pairs, handler);
    }
}
