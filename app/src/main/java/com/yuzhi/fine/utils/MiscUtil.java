//////////////////////////////////////////////////////////////////////
//
// MiscUtil.java: implementation of the MiscUtil class.
//
///////////////////////////////////////////////////////////////////////////////

package com.yuzhi.fine.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiscUtil {

	/**
	 * 分割字符串，原理：检测字符串中的分割字符串，然后取子串
	 * 
	 * @param original
	 *            需要分割的字符串
	 * @param regex
	 *            分割字符串
	 * @return 分割后生成的字符串数组
	 */
	public static String[] StringSplit(String original, String regex) {
		if (original == null || regex == null) {
			return null;
		}

		// 取子串的起始位置
		int startIndex = 0;

		// 将结果数据先放入Vector中
		Vector<String> v = new Vector<String>();

		// 返回的结果字符串数组
		String[] str = new String[1];

		// 存储取子串时起始位置
		int index = 0;

		// 获得匹配子串的位置
		startIndex = original.indexOf(regex);

		// ece.tool.Tools.log("startIndex : " + startIndex);
		if (startIndex == -1) {
			str[0] = original;
			return str;
		}

		// 如果起始字符串的位置小于字符串的长度，则证明没有取到字符串末尾。-1代表取到了末尾
		while (startIndex < original.length() && startIndex != -1) {
			// 取子串
			v.addElement(original.substring(index, startIndex));
			// 设置取子串的起始位置
			index = startIndex + regex.length();
			// 获得匹配子串的位置
			startIndex = original.indexOf(regex, startIndex + regex.length());
		}

		// 取结束的子串
		v.addElement(original.substring(index));

		// 将Vector对象转换成数组
		str = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			str[i] = (String) v.elementAt(i);
		}

		// 返回生成的数组
		return str;
	}

	public static int COLOR_RGB(int r, int g, int b) {
		return ((int) (((char) (r) | ((short) ((char) (g)) << 8)) | (((int) (char) (b)) << 16)));
	}

	public static int COLOR_R(int rgb) {
		return (rgb << 24) >>> 24;
	}

	public static int COLOR_G(int rgb) {
		return (rgb << 16) >>> 24;
	}

	public static int COLOR_B(int rgb) {
		return (rgb << 8) >>> 24;
	}

	public static long getDateTimeNow() {
		long nTimeNow = 0;

		try {
			Calendar cal = Calendar.getInstance();

			nTimeNow = cal.getTimeInMillis();

			cal = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nTimeNow;
	}

	public static long getDateTimeNowSecond() {
		return getDateTimeNow() / 1000;
	}

	public static long getDateTimeFromString(String strDate) {
		long nTimeNow = 0;

		if (strDate == null) {
			return 0;
		}

		try {
			SimpleDateFormat myFmt2 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			myFmt2.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date dt = myFmt2.parse(strDate.trim());

			nTimeNow = dt.getTime();
		} catch (Exception ex) {
		}

		return nTimeNow;
	}

	public static String getSimpleTimeString(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time);

		Date dateNow = new Date();
		dateNow.setHours(0);
		dateNow.setMinutes(0);
		dateNow.setSeconds(0);

		if (dateCreate.before(dateNow)) {
			SimpleDateFormat myFmt2 = new SimpleDateFormat("MM-dd");
			myFmt2.setTimeZone(TimeZone.getDefault());
			strTemp = myFmt2.format(dateCreate);
		} else {
			SimpleDateFormat myFmt2 = new SimpleDateFormat("HH:mm");
			myFmt2.setTimeZone(TimeZone.getDefault());
			strTemp = myFmt2.format(dateCreate);
		}

		return strTemp;
	}

	public static String getSimpleTimeString2(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("HH:mm/MMdd");
		myFmt2.setTimeZone(TimeZone.getDefault());
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}

	public static String getSimpleTimeString3(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("MM-dd HH:mm:ss");
		myFmt2.setTimeZone(TimeZone.getDefault());
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}

	public static String getSimpleTimeString4(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		myFmt2.setTimeZone(TimeZone.getDefault());
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}

	public static String getSimpleTimeStringHMS(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("HH:mm:ss");
		myFmt2.setTimeZone(TimeZone.getDefault());
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}

	public static String getUtcTimeString(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		myFmt2.setTimeZone(TimeZone.getTimeZone("UTC"));
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}
	
	public static String getUnixTimeString(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time*1000);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}
	
	public static String getUnixTimeStringMonthToDay(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time*1000);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}
	
	public static String getContractTimeString(long time) {
		String strTemp = "";

		Date dateCreate = new Date(time*1000);

		SimpleDateFormat myFmt2 = new SimpleDateFormat("MMdd");
		strTemp = myFmt2.format(dateCreate);

		return strTemp;
	}

	public static String getTimeString(Date date) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);

		String strZero = "0000";
		String strDateTime = "";
		String strTemp = "";

		strTemp = String.valueOf(Calendar.YEAR);
		strTemp = strZero.substring(0, 4 - strTemp.length()) + strTemp;
		strDateTime += strTemp;

		strTemp = String.valueOf(Calendar.MONTH);
		strTemp = strZero.substring(0, 2 - strTemp.length()) + strTemp;
		strDateTime += strTemp;

		strTemp = String.valueOf(Calendar.DAY_OF_MONTH);
		strTemp = strZero.substring(0, 2 - strTemp.length()) + strTemp;
		strDateTime += strTemp;

		strTemp = String.valueOf(Calendar.HOUR_OF_DAY);
		strTemp = strZero.substring(0, 2 - strTemp.length()) + strTemp;
		strDateTime += strTemp;

		strTemp = String.valueOf(Calendar.MINUTE);
		strTemp = strZero.substring(0, 2 - strTemp.length()) + strTemp;
		strDateTime += strTemp;

		strTemp = String.valueOf(Calendar.SECOND);
		strTemp = strZero.substring(0, 2 - strTemp.length()) + strTemp;
		strDateTime += strTemp;

		return strDateTime;
	}

	public static String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer(
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			int nTemp = r.nextInt(range);
			sb.append(buffer.charAt(nTemp));
		}
		return sb.toString();
	}

	public static int getIntValue(String strTemp) {
		int nRet = 0;

		try {
			if (strTemp == null) {
				return nRet;
			}

			nRet = Integer.parseInt(strTemp);
		} catch (Exception ex) {
			nRet = 0;
		}

		return nRet;
	}

	public static BigDecimal getBigDecimalValue(String strTemp) {
		BigDecimal temp = new BigDecimal("0.0");

		try {
			if (strTemp == null || strTemp.equals("")) {
				return temp;
			}

			return new BigDecimal(strTemp);
		} catch (Exception ex) {
		}

		return temp;
	}

	public static long getLongValue(String strTemp) {
		long nRet = 0;

		try {
			if (strTemp == null) {
				return nRet;
			}

			nRet = Long.parseLong(strTemp);
		} catch (Exception ex) {
			nRet = 0;
		}

		return nRet;
	}

	public static int getIntValue(String strTemp, int arg1) {
		int nRet = 0;

		try {
			if (strTemp == null) {
				return nRet;
			}

			nRet = Integer.parseInt(strTemp, arg1);
		} catch (Exception ex) {
		}

		return nRet;
	}

	public static double getDoubleValue(String strTemp) {
		double nRet = 0.0;

		try {
			nRet = Double.valueOf(strTemp);
		} catch (Exception ex) {
		}

		return nRet;
	}

	public static float getFloatValue(String strTemp) {
		float nRet = 0.0f;

		try {
			nRet = Float.parseFloat(strTemp);
		} catch (Exception ex) {
			nRet = 0.0f;
		}

		return nRet;
	}

	public static String getMD5(byte[] bytes) {
		String strTemp = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(bytes);
			return toHexString(algorithm.digest(), "");
		} catch (Exception e) {
			strTemp = "";
		}

		return strTemp;
	}

	public static String toHexString(byte[] bytes, String separator) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			if (Integer.toHexString(0xFF & b).length() == 1)
				hexString.append("0").append(Integer.toHexString(0xFF & b));
			else
				hexString.append(Integer.toHexString(0xFF & b));
		}
		return hexString.toString();
	}

	public static boolean CopyStream(InputStream is, OutputStream os) {
		boolean bRet = false;

		int nTotalSize = 0;

		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1) {
					bRet = true;
					break;
				}
				os.write(bytes, 0, count);

				nTotalSize += count;
			}
		} catch (Exception ex) {
			bRet = false;
		}

		if (nTotalSize <= 0) {
			bRet = false;
		}

		return bRet;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	// decodes image and scales it to reduce memory consumption
	public static Drawable decodeDrawableFile(String strFilename,
			int inImageSize) {
		try {
			File f = new File(strFilename);

			Bitmap b = decodeBitmapFile(f, inImageSize);
			f = null;

			BitmapDrawable draw = null;
			if (b != null) {
				draw = new BitmapDrawable(b);
			}

			return (draw);
		} catch (Exception ex) {
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	public static Bitmap decodeBitmapFile(String strFilename, int inImageSize) {
		try {
			File f = new File(strFilename);

			Bitmap b = decodeBitmapFile(f, inImageSize);

			f = null;

			return (b);
		} catch (Exception ex) {
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	public static Bitmap decodeBitmapFile(File f, int inImageSize) {
		try {
			if (f == null) {
				return null;
			}

			FileInputStream tempFile = new FileInputStream(f);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(tempFile, null, options);

			options.inSampleSize = MiscUtil.computeSampleSize(options, -1,
					inImageSize);
			options.inJustDecodeBounds = false;

			try {
				Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f),
                        null, options);
				return (b);
			} catch (OutOfMemoryError err) {
				err.printStackTrace();
			}

			options = null;

			tempFile.close();
			tempFile = null;

			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	public static Bitmap decodeBitmapFile(InputStream tempFile, int inImageSize) {
		try {
			if (tempFile == null) {
				return null;
			}

			if (inImageSize <= 0) {
				Bitmap b = BitmapFactory.decodeStream(tempFile);
				return (b);
			} else {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(tempFile, null, options);

				options.inSampleSize = MiscUtil.computeSampleSize(options, -1,
						inImageSize);
				options.inJustDecodeBounds = false;

				try {
					Bitmap b = BitmapFactory.decodeStream(tempFile, null,
                            options);
					return (b);
				} catch (OutOfMemoryError err) {
					err.printStackTrace();
				} catch (Exception err) {
					err.printStackTrace();
				}

				options = null;
			}

			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	// 判断存储卡是否存在
	public static boolean existSDcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	// json数据操作
	public static JSONObject getNodeJSonItem(JSONObject jsonObject, String key) {
		JSONObject temp = null;
		try {
			if (jsonObject == null) {
				return null;
			}

			temp = (JSONObject) jsonObject.get(key);
		} catch (Exception e) {
			temp = null;
		}

		return temp;
	}

	public static JSONObject getNodeJSonItem(JSONArray jsonObject, int index) {
		JSONObject temp = null;
		try {
			if (jsonObject == null) {
				return null;
			}

			temp = (JSONObject) jsonObject.getJSONObject(index);
		} catch (Exception e) {
			temp = null;
		}

		return temp;
	}

	public static JSONArray getNodeJSonArray(JSONObject jsonObject, String key) {
		JSONArray temp = null;
		try {
			if (jsonObject == null) {
				return null;
			}

			temp = jsonObject.getJSONArray(key);
		} catch (Exception e) {
			temp = null;
		}

		return temp;
	}

	public static JSONObject getNodeJSonObject(JSONObject jsonObject, String key) {
		JSONObject temp = null;
		try {
			if (jsonObject == null) {
				return null;
			}

			temp = jsonObject.getJSONObject(key);
		} catch (Exception e) {
			temp = null;
		}

		return temp;
	}

	public static String getNodeJSonValue(JSONObject jsonObject, String key) {
		String temp = "";
		try {
			if (jsonObject == null) {
				return temp;
			}

			temp = jsonObject.getString(key);
		} catch (Exception e) {
		}

		if (temp == null) {
			temp = "";
		}

		return temp;
	}

	public static boolean deleteFile(String strFile) {
		boolean bRet = false;
		try {
			if (strFile == null || strFile.equals("")) {
				return true;
			}

			File file = new File(strFile);
			if (file.exists()) {
				file.delete();
			}
			file = null;

			bRet = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			bRet = false;
		}

		return bRet;
	}

	public static String getLowerString(String str) {
		if (str == null || str.equals("")) {
			return "";
		}

		int size = str.length();
		char[] chs = str.toCharArray();
		for (int i = 0; i < size; i++) {
			if (chs[i] <= 'Z' && chs[i] >= 'A') {
				chs[i] = (char) (chs[i] + 32);
			} else if (chs[i] <= 'z' && chs[i] >= 'a') {
				chs[i] = (char) (chs[i] - 32);
			}
		}

		return new String(chs);
	}

	public static ArrayList<String> arrayStringToArrayList(String str) {
		ArrayList<String> tempList = new ArrayList<String>();

		if (str == null || str.equals("")) {
			return tempList;
		}

		try {
			String[] tempArray = null;

			// select id
			String strTemp = str;
			List<String> arrayList = null;
			if (strTemp != null && strTemp.length() > 0) {
				strTemp = strTemp.substring(1, strTemp.length() - 1);
				if (strTemp.length() > 0) {
					strTemp = strTemp.replace(" ", "");
					tempArray = strTemp.split(",");
					arrayList = Arrays.asList(tempArray);
				}
			}

			if (arrayList != null) {
				tempList.addAll(arrayList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tempList;
	}

	static public String vectorToString(Vector<String> vector, String delimiter) {
		String strRet = "";
		try {
			if (vector == null) {
				return strRet;
			}

			StringBuilder vcTostr = new StringBuilder();
			if (vector.size() > 0) {
				vcTostr.append(vector.get(0));
				for (int i = 1; i < vector.size(); i++) {
					vcTostr.append(delimiter);
					vcTostr.append(vector.get(i));
				}
			}
			strRet = vcTostr.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			strRet = "";
		}

		return strRet;
	}

	static public String listToString(ArrayList<String> vector, String delimiter) {
		String strRet = "";
		try {
			if (vector == null) {
				return strRet;
			}

			StringBuilder vcTostr = new StringBuilder();
			if (vector.size() > 0) {
				vcTostr.append(vector.get(0));
				for (int i = 1; i < vector.size(); i++) {
					vcTostr.append(delimiter);
					vcTostr.append(vector.get(i));
				}
			}
			strRet = vcTostr.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			strRet = "";
		}

		return strRet;
	}

	static public Vector<String> stringToVector(String str, String delimiter) {
		Vector<String> tempList = new Vector<String>();

		try {
			if (str == null || str.equals("") || delimiter == null
					|| delimiter.equals("")) {
				return tempList;
			}

			int step = delimiter.length();
			int index = 0, offset = 0;
			while ((index = str.indexOf(delimiter, index + step)) != -1) {
				tempList.add(str.substring(offset, index));
				offset = index + step;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tempList;
	}

	static public String roundFormat(double num, int len) {
		String strTemp = "";

		try {
			BigDecimal b = new BigDecimal(String.valueOf(num));
			BigDecimal f1 = b.setScale(len, BigDecimal.ROUND_DOWN);
			strTemp = f1.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			strTemp = "";
		}

		return strTemp;
	}

	static public String roundFormat(float num, int len) {
		String strTemp = "";

		try {
			BigDecimal b = new BigDecimal(String.valueOf(num));
			BigDecimal f1 = b.setScale(len, BigDecimal.ROUND_DOWN);
			strTemp = f1.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			strTemp = "";
		}

		return strTemp;
	}

	static public String roundFormat(String num, int len) {
		String strTemp = "";

		try {
			if(num == null || num.equals(""))
			{
				return strTemp;
			}
			
			BigDecimal b = new BigDecimal(num);
			BigDecimal f1 = b.setScale(len, BigDecimal.ROUND_DOWN);
			strTemp = f1.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			strTemp = "";
		}

		return strTemp;
	}

	static public String roundFormat(BigDecimal b, int len) {
		String strTemp = "";

		try {
			BigDecimal f1 = b.setScale(len, BigDecimal.ROUND_DOWN);
			strTemp = f1.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			strTemp = "";
		}

		return strTemp;
	}

	public static boolean isEmail(String strEmail) {
		boolean bRet = false;
		try {
			String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z0-9][a-zA-Z0-9\\.]*[a-zA-Z0-9]$";

			Pattern p = Pattern.compile(strPattern);
			Matcher m = p.matcher(strEmail);
			bRet = m.matches();
		} catch (Exception ex) {
			ex.printStackTrace();
			bRet = false;
		}

		return bRet;
	}

	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}
	

//	public static float getMaxAmount(AccuAmount bids, AccuAmount asks, int size) {
//		float price = 1;
//		try {
//			boolean bSet = false;
//
//			int length = bids.getAmount().length > size ? size : bids.getAmount().length;
//			for (int i = 0; i < length; i++) {
//				if (i == 0) {
//					price = bids.getAmount()[0];
//					bSet = true;
//				} else {
//					if (price < bids.getAmount()[i]) {
//						price = bids.getAmount()[i];
//					}
//				}
//			}
//
//			length = asks.getAmount().length > size ? size : asks.getAmount().length;
//			for (int i = 0; i < length; i++) {
//				if(!bSet)
//				{
//					if (i == 0) {
//						price = asks.getAmount()[0];
//						bSet = true;
//					}
//				}
//				else
//				{
//					if (price < asks.getAmount()[i]) {
//						price = asks.getAmount()[i];
//					}
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return price;
//	}
	
}
