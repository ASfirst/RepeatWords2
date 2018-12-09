package com.jeramtough.repeatwords2.component.learningmode;

import android.util.SparseIntArray;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.ArrayList;
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

        //拿出今日已学所有单词
        List<Integer> haveLearnedWordIds = operateWordsMapperFactoryProvider
                .getOperateWordsMapperFactory().getHaveLearnedTodayMapper()
                .getWordIds();

        //算出两次以上的今日已学的单词加入黑名单
        SparseIntArray countMap = new SparseIntArray();
        List<Integer> noNeededIdsOfLearning = new ArrayList<>();
        for (Integer integer : haveLearnedWordIds) {
            int count = countMap.get(integer);
            count++;
            if (count >= 2) {
                noNeededIdsOfLearning.add(integer);
            }
            else {
                countMap.put(integer, count);
            }
        }

        //拿出今日已学三分之一单词
        int sizeFromHavedLearnedToday = size / 3;
        sizeFromHavedLearnedToday = noNeededIdsOfLearning.size()
                > sizeFromHavedLearnedToday ? sizeFromHavedLearnedToday : noNeededIdsOfLearning
                .size();

        List<Integer> shallLearningIds = operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                                          .getHaveGraspedMapper()
                                                                          .getIdsForRandom(
                                                                                  size - sizeFromHavedLearnedToday);


        for (int i = 0; i < sizeFromHavedLearnedToday; i++) {
            shallLearningIds.add(noNeededIdsOfLearning.get(i));
        }

        Collections.shuffle(shallLearningIds);
        return shallLearningIds;
    }

    @Override
    public void learnWordToday(WordRecord wordRecord) {
        //这个模式下今日已学就不加入数据库了
    }
}
