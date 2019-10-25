package com.jeramtough.repeatwords2.component.youdao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeramtough.repeatwords2.component.youdao.bean.YoudaoQueryResult;

public class QueryResultParser {


    public static YoudaoQueryResult parsing(String resultText) {
        YoudaoQueryResult youdaoQueryResult = new YoudaoQueryResult();
        JSONObject jsonObject = JSON.parseObject(resultText);

        youdaoQueryResult=JSON.parseObject(resultText,YoudaoQueryResult.class);

        return youdaoQueryResult;
    }
}
