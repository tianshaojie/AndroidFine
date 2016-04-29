package com.yuzhi.fine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private final static NumberFormat numberFormat = NumberFormat.getNumberInstance();

    static {
        numberFormat.setMaximumIntegerDigits(9);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * URL解码
     *
     * @param str
     * @return
     */
    public static String decodeURL(String str) {
        return URLDecoder.decode(str);
    }

    public static String enCodeRUL(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception ex) {
        }
        return str;
    }

    public static boolean isEmpty(Object str) {
        return str == null || str.toString().length() == 0;
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /* 去掉时间为00:00:00 */
    public static String replaceTimeZero(String date) {
        if (date != null) {
            if (date.indexOf("00:00:00") > 0) {
                date = date.replaceAll("00:00:00", "");
            } else if (date.indexOf(":00") == 16) {
                date = date.substring(0, 16);
            }
        }
        return date;
    }

    public static boolean startWithHttp(Object str) {
        return str != null
                && str.toString().toLowerCase().startsWith("http://");
    }

    /* 字符串截取 防止出现半个汉字 */
    public static String truncate(String str, int byteLength) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        if (byteLength < 0) {
            throw new IllegalArgumentException(
                    "Parameter byteLength must be great than 0");
        }
        int i = 0;
        int len = 0;
        int leng = 0;
        char[] chs = str.toCharArray();
        try {
            leng = str.getBytes("gbk").length;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (leng <= byteLength)
            return str;
        while ((len < byteLength) && (i < leng)) {
            len = (chs[i++] > 0xff) ? (len + 2) : (len + 1);
        }

        if (len > byteLength) {
            i--;
        }
        return new String(chs, 0, i) + "...";
    }

    /**
     * 分割keyword 按最后一个出现的@分割
     *
     * @param data
     * @return keyword
     */
    public static String splitKeyWord(String data) {
        if (data == null || data.length() == 0)
            return null;
        if (data.lastIndexOf("@") == -1)
            return data;
        return data.substring(0, data.lastIndexOf("@"));
    }

    /**
     * @param date (时间戳)
     * @return 年－月－日 (2013-03-01)
     */
    public static String convertDate(String date) {
        try {
            if (date == null || "".equals(date))
                return "";
            if (isNumeric(date))
                return computingTime(Long.parseLong(date));
            else
                return date;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 确定是否是时间戳
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;

    }

    /**
     * 计算时间1-59分钟前，
     *
     * @param date
     * @return
     */
    private static String computingTime(Long date) {
        if (date < 10000)
            return "";
        long currentTime = System.currentTimeMillis();
        float i = ((currentTime - date) / 3600 / 1000);
        if (i < 1) {
            int time = (int) Math.ceil(i * 60);
            return time + 1 + "分钟前";
        } else if (i < 24) {
            return (int) i + "小时前";
        } else if (i < 48)
            return "昨天";
        return toNYR(date);
    }

    /**
     * 截取年月日 如（2013-01-08）
     *
     * @param data
     * @return yyyy-MM-dd
     */
    private static String toNYR(long data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        try {
            return dateFormat.format(data);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 抽正文标题
     *
     * @param str
     * @return
     */
    public static String setReadabilityTitle(String str) {
        String res = null;
        if (str != null)
            if (str.length() > 9)
                res = str.substring(0, 3) + "..."
                        + str.substring(str.length() - 3, str.length());
        return res == null ? str : res;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将list中的字符串用split间隔开
     *
     * @param list
     * @param split
     * @return
     */
    public static String Join(List<String> list, String split) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i + 1 != list.size()) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static String toPriceStr(double price) {
        return "￥" + numberFormat.format(price);
    }

    public static String toDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }


    /**
     * 流转字符串方法
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
