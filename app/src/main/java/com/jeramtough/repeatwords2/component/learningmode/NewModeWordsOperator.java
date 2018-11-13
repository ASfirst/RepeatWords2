package com.jeramtough.repeatwords2.component.learningmode;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:22.
 */
@JtComponent
public class NewModeWordsOperator implements WordsOperator {
    private OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider;

    @IocAutowire
    public NewModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider) {
        this.operateWordMapperFactoryProvider = operateWordMapperFactoryProvider;
    }


    @Override
    public void removeWordFromList(int wordId) {
        WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());
        operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                .getShallLearningMapper().removeWordRecordById(wordId);
        operateWordMapperFactoryProvider.getOperateWordsMapperFactory().getHaveGraspedMapper()
                .addWordRecord(wordRecord);
    }

    @Override
    public List<Integer> getWordIdsOfNeeding(int size, List<Integer> noNeededIdsOfLearning) {
        return operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                .getShallLearningMapper().getIdsOrderByTime(size,noNeededIdsOfLearning);
    }
}