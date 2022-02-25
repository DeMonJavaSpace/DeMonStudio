package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author DeMon
 * Created on 2022/2/18.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
public class TimeUtils {
    public static final SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat sdf_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String getNowDate() {
        return format(System.currentTimeMillis(), sdf_yyyyMMdd);
    }


    public static String getNowTimeStr() {
        return format(System.currentTimeMillis());
    }

    /**
     * 将时间戳转化为年月日格式
     */
    public static String format(long timestamp) {
        return format(timestamp, sdf_yyyyMMddHHmmss);
    }

    /**
     * 将时间戳转化为年月日格式
     */
    public static String format(long timestamp, SimpleDateFormat sdf) {
        if (timestamp <= 0) {
            return "";
        }
        String str = "";
        try {
            str = sdf.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 将时间戳转化为年月日格式
     */
    public static long parse(String time) {
        try {
            Date date = sdf_yyyyMMddHHmmss.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static Integer getHour() {
        return new Date().getHours();
    }

    public static Integer getMinutes() {
        return new Date().getMinutes();
    }

    public static Integer getSeconds() {
        return new Date().getSeconds();
    }
}
