package com.jeramtough.repeatwords2.component.learningmode;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:24.
 */
@JtComponent
public class MarkModeWordsOperator implements WordsOperator {
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    @IocAutowire
    public MarkModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }

    @Override
    public void removeWordFromList(int wordId) {
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getMarkedMapper()
                .removeWordRecordById(wordId);
    }

    @Override
    public List<Integer> getWordIdsOfNeeding(int size, List<Integer> noNeededIdsOfLearning) {
        return operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                .getMarkedMapper().getIdsOrderById(size, noNeededIdsOfLearning);
    }
}