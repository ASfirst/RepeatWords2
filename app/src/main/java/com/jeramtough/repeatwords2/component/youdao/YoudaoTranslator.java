package com.jeramtough.repeatwords2.component.youdao;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.youdao.YoudaoQueryResult;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 11718
 */
@JtComponent
public class YoudaoTranslator {

    private OkHttpClient okHttpClient;

    @IocAutowire
    public YoudaoTranslator() {
        okHttpClient = new OkHttpClient();
    }

    public YoudaoQueryResult translate(String queryText, Languages fromLanguages, Languages toLanguages) {
        YoudaoRequestParams params = new YoudaoRequestParams(queryText, fromLanguages.getName(), toLanguages.getName());

        Request request = new Request.Builder().url(params.getUrlWithQueryString()).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return QueryResultParser.parsing(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public YoudaoQueryResult translate(String queryText) {
        return translate(queryText, Languages.AUTO, Languages.AUTO);
    }
}
