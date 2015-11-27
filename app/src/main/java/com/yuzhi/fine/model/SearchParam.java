package com.yuzhi.fine.model;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.common.NotObfuscateInterface;

import java.io.Serializable;

public class SearchParam implements Serializable, NotObfuscateInterface {

    public static final int SORT_GRADE = 0;  // 评分由高到低（默认按评分）
    public static final int SORT_TIME = 1; // 人气由高到低（按人气）
    public static final int SORT_DISTANCE = 2; // 距离由近到远（按距离）
    public static final int SORT_COMMENT = 3;  // 评价数有多到少（按评价数）
    public static final int SORT_DEFAULT = 4;  // 默认排序

    public static final int TYPE_ALL = 0;
    public static final int TYPE_OUT = 1;
    public static final int TYPE_IN = 2;


    private String city; // 城市"beijing"
    private int sid;     // 商家ID
    private Double lng;  // 经度
    private Double lat;  // 纬度
    private String name; // 商家名称
    private Long cid1;   // 商家一级分类ID
    private Long cid2;   // 商家二级分类ID
    private Long aid1; // 商圈一级分类ID
    private Long aid2; // 商圈二级级分类ID
    private Long distance; // 距离
    private Integer sort; // 排序
    private Integer pno; // 页码
    private Integer psize; //分页大小
    private Integer type; // 商家类型，0默认，1普通商家，2合作商家
    private String cate_name;
    private String city_name;
    private String custom_type;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAid1() {
        return aid1;
    }

    public void setAid1(Long aid1) {
        this.aid1 = aid1;
    }

    public Long getAid2() {
        return aid2;
    }

    public void setAid2(Long aid2) {
        this.aid2 = aid2;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

    public Integer getPsize() {
        return psize;
    }

    public void setPsize(Integer psize) {
        this.psize = psize;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getCid2() {
        return cid2;
    }

    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


    public SearchParam copy() {
        return JSON.parseObject(JSON.toJSONString(this), SearchParam.class);
    }

    public String getCustom_type() {
        return custom_type;
    }

    public void setCustom_type(String custom_type) {
        this.custom_type = custom_type;
    }
}
