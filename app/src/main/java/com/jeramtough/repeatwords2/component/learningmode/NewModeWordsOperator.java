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
 * on 2018  May 08 Tuesday 00:22.
 */
@JtComponent
public class NewModeWordsOperator implements WordsOperator {
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    @IocAutowire
    public NewModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }


    @Override
    public void removeWordFromList(int wordId) {
        WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                         .getShallLearningMapper().removeWordRecordById(wordId);
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getHaveGraspedMapper()
                                         .addWordRecord(wordRecord);
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

        //根据时间排序翻倍取出，然后取随机的一半
        int perLearningCount = size * 2;
        List<Integer> shallLearningIds = operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                                          .getShallLearningMapper().getIdsOrderByTime(
                        perLearningCount, noNeededIdsOfLearning);

        Collections.shuffle(shallLearningIds);

        //真实取出来的可能大于或者小鱼想取出的
        int finalSize = shallLearningIds.size() >= size ? size : shallLearningIds.size();

        List<Integer> shallLearningIds1 = new ArrayList<>();
        for (int i = 0; i < finalSize; i++) {
            shallLearningIds1.add(shallLearningIds.get(i));
        }

        return shallLearningIds1;
    }

    @Override
    public void learnWordToday(WordRecord wordRecord) {
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                         .getHaveLearnedTodayMapper().addWordRecord(wordRecord);
    }
}
