package com.jeramtough.repeatwords2.service.impl;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.youdao.YoudaoTranslator;
import com.jeramtough.repeatwords2.component.youdao.bean.YoudaoQueryResult;
import com.jeramtough.repeatwords2.service.WordService;

import java.util.concurrent.Executor;

@JtServiceImpl
class WordServiceImpl implements WordService {

    private YoudaoTranslator youdaoTranslator;
    private Executor executor;

    @IocAutowire
    WordServiceImpl(YoudaoTranslator youdaoTranslator) {
        this.youdaoTranslator = youdaoTranslator;
        executor = JtExecutors.newCachedThreadPool();
    }

    @Override
    public void queryWordDetail(Word word, BusinessCaller businessCaller) {
        executor.execute(() -> {
            YoudaoQueryResult youdaoQueryResult = youdaoTranslator.translate(word.getEn());
            businessCaller.getData().putSerializable("youdaoQueryResult", youdaoQueryResult);
            businessCaller.callBusiness();
        });
    }
}
