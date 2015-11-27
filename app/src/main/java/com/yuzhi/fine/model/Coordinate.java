package com.yuzhi.fine.model;

import com.yuzhi.fine.common.NotObfuscateInterface;

import java.io.Serializable;

/**
 * Desc: 商家坐标
 * User: tiansj
 * DateTime: 14-1-3 下午4:49
 */
public class Coordinate implements Serializable, NotObfuscateInterface {

    private double lng;
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
