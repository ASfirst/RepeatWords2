package com.jeramtough.repeatwords2.component.learning.mode.wordoperator;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;

import java.util.Collections;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:23.
 */
@JtComponent
public class ReviewModeWordsOperator extends BaseWordsOperator {
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    @IocAutowire
    public ReviewModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        super(operateWordsMapperFactoryProvider);
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }


    @Override
    public void removeWordFromList(int wordId) {

        boolean has = operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                       .getHaveGraspedMapper().hasWordId(
                        wordId);
        if (has) {
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getHaveGraspedMapper()
                                             .removeWordRecordById(wordId);
//            WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getShallLearningMapper().addWordRecord(
                    null);
        }
    }

    @Override
    public List<Integer> getWordIdsOfNeeding(int size) {

        List<Integer> todaysHaveLearnedWordsAtLeastTwice = getTodaysHaveLearnedWordsIdAtLeastTwice();

        //拿出今日已学三分之一单词
        int sizeFromHavedLearnedToday = size / 4;
        sizeFromHavedLearnedToday = todaysHaveLearnedWordsAtLeastTwice.size()
                > sizeFromHavedLearnedToday ? sizeFromHavedLearnedToday : todaysHaveLearnedWordsAtLeastTwice
                .size();

        List<Integer> shallLearningIds = operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                                          .getHaveGraspedMapper()
                                                                          .getWordIdsForRandom(
                                                                                  size - sizeFromHavedLearnedToday);


        for (int i = 0; i < sizeFromHavedLearnedToday; i++) {
            shallLearningIds.add(todaysHaveLearnedWordsAtLeastTwice.get(i));
        }

        Collections.shuffle(shallLearningIds);
        return shallLearningIds;
    }

    @Override
    public void learnWordToday(WordRecord wordRecord) {
        //这个模式下今日已学就不加入数据库了
    }
}
