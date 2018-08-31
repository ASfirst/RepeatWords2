package com.jeramtough.repeatwords2;

import com.jeramtough.repeatwords2.bean.youdao.YoudaoQueryResult;
import com.jeramtough.repeatwords2.component.youdao.Languages;
import com.jeramtough.repeatwords2.component.youdao.QueryResultParser;
import com.jeramtough.repeatwords2.component.youdao.YoudaoRequestParams;
import com.jeramtough.repeatwords2.component.youdao.YoudaoTranslator;

import org.junit.Test;

public class YoudaoTest {
    @Test
    public void test() {
        YoudaoTranslator youdaoTranslator = new YoudaoTranslator();
        YoudaoQueryResult youdaoQueryResult = youdaoTranslator.translate("what", Languages.ENGLISH, Languages.CHINESE);
        System.out.println(youdaoQueryResult);
    }
}
