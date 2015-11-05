package com.example.changzhenjie.mynote.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by changzhenjie on 11/5/15.
 */
public class DateUtil {
    private static final String TAG = LogUtils.makeLogTag(UiUtils.class);

    /**
     * 将String转换为Date
     *
     * @param strDate
     * @return
     */
    public static Date parseDataFromString(String strDate) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            LogUtils.LOGD(TAG,e.getMessage());
        }
        return date;
    }

    /**
     * 将时间转换为String类型
     *
     * @param date
     * @return
     */
    public static String parseStringFromDate(Date date) {
        String dateStr = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateStr = dateFormat.format(date);
        return dateStr;
    }
}
