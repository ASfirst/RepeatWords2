package com.jeramtough.repeatwords2.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 11718
 * on 2017  九月 18 星期一 19:58.
 */

public class SqlUtil {
    public static String[] processSqls(InputStream inputStream) {
        String[] newSqls = null;
        try {
            String content = IOUtils.toString(inputStream, "UTF-8");
            content = content.trim();
            String[] sqls = content.split(";");
            newSqls = new String[sqls.length];

            for (int i = 0; i < sqls.length; i++) {
                newSqls[i] = sqls[i] + ";";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return newSqls;
    }
}
