package com.jeramtough.repeatwords2.component.learning.mode.wordoperator;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
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
public class NewModeWordsOperator extends BaseWordsOperator {
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    @IocAutowire
    public NewModeWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        super(operateWordsMapperFactoryProvider);
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }


    @Override
    public void removeWordFromList(int wordId) {
        WordRecord wordRecord = new WordRecord(null, (long) wordId, DateTimeUtil.getDateTime(),null);
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                         .getShallLearningMapper().removeWordRecordById(
                wordId);
        operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getHaveGraspedMapper()
                                         .addWordRecord(wordRecord);
    }

    @Override
    public List<Integer> getWordIdsOfNeeding(int size) {
        //算出两次以上的今日已学的单词加入黑名单
        List<Integer> noNeededIdsOfLearning = getTodaysHaveLearnedWordsIdAtLeastTwice();

        //根据时间排序翻倍取出，然后取随机的一半
        int perLearningCount = size * 2;
        List<Integer> shallLearningIds = operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                                          .getShallLearningMapper().getWordIdsOrderByTime(
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
                                         .getHaveLearnedTodayMapper().addWordRecord(
                wordRecord);
    }
}
