package com.jeramtough.repeatwords2.component.learning.mode.wordoperator;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:24.
 */
@JtComponent
public class MarkModeWordsOperator extends BaseWordsOperator {
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    @IocAutowire
    public MarkModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        super(operateWordsMapperFactoryProvider);
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }

    @Override
    public void removeWordFromList(int wordId) {
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getMarkedMapper()
                .removeWordRecordById(wordId);
    }

    @Override
    public List<Integer> getWordIdsOfNeeding(int size) {

        return operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                .getMarkedMapper().getWordIdsOrderByWordId(size);
    }

    @Override
    public void learnWordToday(WordRecord wordRecord) {
        //这个模式下今日已学就不加入数据库了
    }
}
