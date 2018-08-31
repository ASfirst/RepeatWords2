package com.jeramtough.repeatwords2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JeramTough
 */
public class DateTimeUtil {

    public static String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String dateTime = simpleDateFormat.format(new Date()).toString();
        return dateTime;
    }

}
