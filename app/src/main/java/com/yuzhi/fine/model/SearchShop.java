package com.yuzhi.fine.model;

import com.yuzhi.fine.common.NotObfuscateInterface;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 * User: tiansj
 * DateTime: 14-1-3 下午4:49
 */
public class SearchShop implements Serializable, NotObfuscateInterface {

    public static final int TYPE_OUTSIDE = 0;  // 外部商家
    public static final int TYPE_INSIDE = 1; // 内部商家
    public static final int SHOW_SUBSCRIBE_COUNT = 1; // 显示外部商家订阅数
    public static final int HIDE_SUBSCRIBE_COUNT = 0; // 隐藏外部商家订阅数

    private long _id;
    private int t;  //0-大众点评导入商家  1-合作商家
    private long sid;    // 商家Id
    private List<Long> aid1; // 商圈一级分类ID  TODO
    private long aid2; // 商圈二级级分类ID
    private String name; // 商家名称
    private String logo; // 合作商家logo
    private String addr; // 商家地址
    private List<Long> ncid1;   // 商家一级分类ID
    private long ncid2;   // 商家二级分类ID
    private int goodCmt;    //好评数
    private int midCmt;     //中评数
    private int badCmt;    //差评数
    private int totalCmt;   //总评数
    private long gid;   //分店分组ID
    private double dis;  //距离
    private int subscribeCount;   //订阅数
    private double score;   //综合评分
    private String phone;
    private String card;     //开卡
    private int couponEnabled;
    private Coordinate coordinate;  //坐标
    private int show;  //坐标
    private int capi;       //人均
    private double tast;    //口味
    private double effe;    //效果
    private double prod;    //产品
    private double room;    //房间
    private double cond;    //环境
    private double serv;    //服务

    public int getCapi() {
        return capi;
    }

    public void setCapi(int capi) {
        this.capi = capi;
    }

    public double getTast() {
        return tast;
    }

    public void setTast(double tast) {
        this.tast = tast;
    }

    public double getEffe() {
        return effe;
    }

    public void setEffe(double effe) {
        this.effe = effe;
    }

    public double getProd() {
        return prod;
    }

    public void setProd(double prod) {
        this.prod = prod;
    }

    public double getRoom() {
        return room;
    }

    public void setRoom(double room) {
        this.room = room;
    }

    public double getCond() {
        return cond;
    }

    public void setCond(double cond) {
        this.cond = cond;
    }

    public double getServ() {
        return serv;
    }

    public void setServ(double serv) {
        this.serv = serv;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getAid2() {
        return aid2;
    }

    public void setAid2(long aid2) {
        this.aid2 = aid2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public long getNcid2() {
        return ncid2;
    }

    public void setNcid2(long ncid2) {
        this.ncid2 = ncid2;
    }

    public int getGoodCmt() {
        return goodCmt;
    }

    public void setGoodCmt(int goodCmt) {
        this.goodCmt = goodCmt;
    }

    public int getMidCmt() {
        return midCmt;
    }

    public void setMidCmt(int midCmt) {
        this.midCmt = midCmt;
    }

    public int getBadCmt() {
        return badCmt;
    }

    public void setBadCmt(int badCmt) {
        this.badCmt = badCmt;
    }

    public int getTotalCmt() {
        return totalCmt;
    }

    public void setTotalCmt(int totalCmt) {
        this.totalCmt = totalCmt;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public double getDis() {
        return dis;
    }

    public void setDis(double dis) {
        this.dis = dis;
    }

    public int getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(int subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Long> getAid1() {
        return aid1;
    }

    public void setAid1(List<Long> aid1) {
        this.aid1 = aid1;
    }

    public List<Long> getNcid1() {
        return ncid1;
    }

    public void setNcid1(List<Long> ncid1) {
        this.ncid1 = ncid1;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public boolean isShowSubscribeCount() {
        return getShow() == SHOW_SUBSCRIBE_COUNT || getT() == TYPE_INSIDE;
    }

    public int getCouponEnabled() {
        return couponEnabled;
    }

    public void setCouponEnabled(int couponEnabled) {
        this.couponEnabled = couponEnabled;
    }
}
