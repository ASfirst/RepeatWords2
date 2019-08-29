package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.bean.word.WordWithRecordTime;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.List;
import java.util.concurrent.Executor;

@JtServiceImpl final class ListServiceImpl implements ListService {

    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;
    private Executor executor;

    @IocAutowire
    private ListServiceImpl(
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider) {
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
        executor = JtExecutors.newCachedThreadPool();
    }

    @Override
    public void getHaveGraspedWords(BusinessCaller businessCaller) {
        executor.execute(() -> {
            List<WordWithRecordTime> wordWithRecordTimeList =
                    operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                     .getHaveGraspedMapper().getWordWithRecordTimeListOrderByTime();
            WordWithRecordTime[] wordWithRecordTimes = wordWithRecordTimeList
                    .toArray(new WordWithRecordTime[wordWithRecordTimeList.size()]);
            businessCaller.getData()
                          .putSerializable("wordWithRecordTimes", wordWithRecordTimes);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void getShallLearningWords(BusinessCaller businessCaller) {
        executor.execute(() -> {
            List<WordWithRecordTime> wordWithRecordTimeList =
                    operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                     .getShallLearningMapper().getWordWithRecordTimeListOrderByTime();
            WordWithRecordTime[] wordWithRecordTimes = wordWithRecordTimeList
                    .toArray(new WordWithRecordTime[wordWithRecordTimeList.size()]);
            businessCaller.getData()
                          .putSerializable("wordWithRecordTimes", wordWithRecordTimes);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void getMarkedWords(BusinessCaller businessCaller) {
        executor.execute(() -> {
            List<WordWithRecordTime> wordWithRecordTimeList =
                    operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                     .getMarkedMapper().getWordWithRecordTimeListOrderByTime();
            WordWithRecordTime[] wordWithRecordTimes = wordWithRecordTimeList
                    .toArray(new WordWithRecordTime[wordWithRecordTimeList.size()]);
            businessCaller.getData()
                          .putSerializable("wordWithRecordTimes", wordWithRecordTimes);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void getDesertedWords(BusinessCaller businessCaller) {
        executor.execute(() -> {
            List<WordWithRecordTime> wordWithRecordTimeList =
                    operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                     .getDesertedLearningMapper()
                                                     .getWordWithRecordTimeListOrderByTime();
            WordWithRecordTime[] wordWithRecordTimes = wordWithRecordTimeList
                    .toArray(new WordWithRecordTime[wordWithRecordTimeList.size()]);
            businessCaller.getData()
                          .putSerializable("wordWithRecordTimes", wordWithRecordTimes);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void getTodaysHaveLearnedWords(BusinessCaller businessCaller) {
        executor.execute(() -> {
            List<WordWithRecordTime> wordWithRecordTimeList =
                    operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                                     .getHaveLearnedTodayMapper()
                                                     .getWordWithRecordTimeListOrderByTime();
            WordWithRecordTime[] wordWithRecordTimes = wordWithRecordTimeList
                    .toArray(new WordWithRecordTime[wordWithRecordTimeList.size()]);
            businessCaller.getData()
                          .putSerializable("wordWithRecordTimes", wordWithRecordTimes);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void removeWordFromTodaysHaveLearnedList(int wordId,
                                                    BusinessCaller businessCaller) {
        executor.execute(() -> {
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getHaveLearnedTodayMapper()
                                             .removeWordRecordById(
                                                     wordId);
            getTodaysHaveLearnedWords(businessCaller);
        });
    }

    @Override
    public void removeWordFromHaveGraspedList(int wordId, BusinessCaller businessCaller) {
        executor.execute(() -> {
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getHaveGraspedMapper().removeWordRecordById(
                    wordId);
            WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getShallLearningMapper().addWordRecord(
                    wordRecord);
            getHaveGraspedWords(businessCaller);
        });
    }

    @Override
    public void removeWordFromShallLearningList(int wordId, BusinessCaller businessCaller) {
        executor.execute(() -> {
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getShallLearningMapper().removeWordRecordById(
                    wordId);
            WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getHaveGraspedMapper().addWordRecord(wordRecord);
            getShallLearningWords(businessCaller);
        });
    }

    @Override
    public void removeWordFromMarkedList(int wordId, BusinessCaller businessCaller) {
        executor.execute(() -> {
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory().getMarkedMapper()
                                             .removeWordRecordById(wordId);
            getMarkedWords(businessCaller);
        });
    }

    @Override
    public void removeWordFromDesertedList(int wordId, BusinessCaller businessCaller) {
        executor.execute(() -> {
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getDesertedLearningMapper().removeWordRecordById(
                    wordId);
            WordRecord wordRecord = new WordRecord(wordId, DateTimeUtil.getDateTime());
            operateWordsMapperFactoryProvider.getOperateWordsMapperFactory()
                                             .getShallLearningMapper().addWordRecord(
                    wordRecord);
            getDesertedWords(businessCaller);
        });
    }
}
