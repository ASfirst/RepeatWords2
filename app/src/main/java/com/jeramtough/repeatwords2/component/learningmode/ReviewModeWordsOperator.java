package com.jeramtough.repeatwords2.component.learningmode;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.Collections;
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
    public List<Integer> getWordIdsOfNeeding(int size) {

        //拿出今日已学三分之一单词
        int sizeFromHavedLearnedToday = size / 3;
        List<Integer> haveLearnedWordIdsToday = operateWordsMapperFactoryProvider
                .getOperateWordsMapperFactory().getHaveLearnedTodayMapper()
                .getIdsForRandom(sizeFromHavedLearnedToday);

        List<Integer> shallLearningIds = operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                                          .getHaveGraspedMapper()
                                                                          .getIdsForRandom(
                                                                                  size - sizeFromHavedLearnedToday);

        shallLearningIds.addAll(haveLearnedWordIdsToday);
        Collections.shuffle(shallLearningIds);
        return shallLearningIds;
    }
}
