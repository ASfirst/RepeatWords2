package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.youdao.YoudaoQueryResult;
import com.jeramtough.repeatwords2.component.youdao.YoudaoTranslator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@JtServiceImpl
class WordServiceImpl implements WordService {

    private YoudaoTranslator youdaoTranslator;
    private Executor executor;

    @IocAutowire
    WordServiceImpl(YoudaoTranslator youdaoTranslator) {
        this.youdaoTranslator = youdaoTranslator;
        executor = Executors.newCachedThreadPool();
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
