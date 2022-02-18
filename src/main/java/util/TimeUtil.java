package util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author DeMon
 * Created on 2022/2/18.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
public class TimeUtil {
    public static final SimpleDateFormat date_Formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String getNowTimeStr() {
        return format(System.currentTimeMillis());
    }

    /**
     * 将时间戳转化为年月日格式
     */
    public static String format(long timestamp) {
        if (timestamp <= 0) {
            return "";
        }
        String str = "";
        try {
            str = date_Formater.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}
