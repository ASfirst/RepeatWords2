package com.jeramtough.repeatwords2.component.learningmode.wordoperator;

import android.util.SparseIntArray;

import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018-12-09 18:36
 * by @author JeramTough
 */
public abstract class BaseWordsOperator implements WordsOperator {

    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;

    protected BaseWordsOperator(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
    }


    @Override
    public List<Integer> getTodaysHaveLearnedWordsIdAtLeastTwice() {
        //拿出今日已学所有单词
        List<Integer> haveLearnedWordIds = operateWordsMapperFactoryProvider
                .getOperateWordsMapperFactory().getHaveLearnedTodayMapper()
                .getWordIds();

        //算出两次以上的今日已学的单词加入黑名单
        SparseIntArray countMap = new SparseIntArray();
        List<Integer> haveLearnedWordAtLeastTwice = new ArrayList<>();
        for (Integer integer : haveLearnedWordIds) {
            int count = countMap.get(integer);
            count++;
            if (count >= 2) {
                haveLearnedWordAtLeastTwice.add(integer);
            }
            else {
                countMap.put(integer, count);
            }
        }
        return haveLearnedWordAtLeastTwice;
    }
}
