package com.jeramtough.repeatwords2.component.learningmode;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:23.
 */
@JtComponent
public class ReviewModeWordsOperator implements WordsOperator {
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    @IocAutowire
    public ReviewModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }


    @Override
    public void removeWordFromList(int wordId) {
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getHaveGraspedMapper()
                .removeWordRecordById(wordId);

        WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());

        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                .getShallLearningMapper().addWordRecord(wordRecord);
    }

    @Override
    public List<Integer> getWordIdsOfNeeding(int size, List<Integer> noNeededIdsOfLearning) {
        return operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                .getHaveGraspedMapper().getIdsForRandom(size);
    }
}
