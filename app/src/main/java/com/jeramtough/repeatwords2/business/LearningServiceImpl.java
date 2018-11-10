package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.learningmode.WordsOperateProvider;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.component.teacher.WordsTeacher;
import com.jeramtough.repeatwords2.dao.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author 11718
 * on 2018  May 03 Thursday 19:04.
 */
@JtServiceImpl
class LearningServiceImpl implements LearningService {
    private DictionaryMapper dictionaryMapper;
    private WordsTeacher wordsTeacher;
    private WordsPool wordsPool;
    private MyAppSetting myAppSetting;
    private OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider;
    private WordsOperateProvider wordsOperateProvider;


    private ExecutorService executorService;

    @IocAutowire
    private LearningServiceImpl(DictionaryMapper dictionaryMapper, WordsTeacher wordsTeacher,
                                WordsPool wordsPool, MyAppSetting myAppSetting,
                                OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider,
                                WordsOperateProvider wordsOperateProvider) {
        this.operateWordMapperFactoryProvider = operateWordMapperFactoryProvider;
        this.dictionaryMapper = dictionaryMapper;
        this.wordsTeacher = wordsTeacher;
        this.wordsPool = wordsPool;
        this.myAppSetting = myAppSetting;
        this.wordsOperateProvider = wordsOperateProvider;

        executorService = JtExecutors.newSingleThreadExecutor();
    }

    @Override
    public void initTeacher(final BusinessCaller businessCaller) {
        L.arrive();
        executorService.submit(() -> {

            wordsTeacher.clear();

            List<Integer> haveLearnedWordIds = operateWordMapperFactoryProvider
                    .getOperateWordsMapperFactory().getHaveLearnedTodayMapper()
                    .getHaveLearnedWordIdsToday();

            List<Integer> noNeededIdsOfLearning = wordsTeacher.processNoNeededIdsOfLearning(haveLearnedWordIds);


            List<Integer> shallLearningIds = wordsOperateProvider.getWordsOperator()
                    .getWordIdsOfNeeding(myAppSetting.getPerLearningCount(), noNeededIdsOfLearning);

            for (Integer id : shallLearningIds) {
                Word word = wordsPool.getWord(id);
                if (word == null) {
                    word = dictionaryMapper.getWord(id);
                    wordsPool.addWord(word);
                }
                wordsTeacher.addWordToList(word);
            }
            businessCaller.getData()
                    .putInt("shallLearningSize", wordsTeacher.getShallLearningSize());
            Word[] shallLearningWords = wordsTeacher.getAllRandomNeedLearningWords();
            businessCaller.getData().putSerializable("shallLearningWords", shallLearningWords);
            businessCaller.callBusiness();
        });
    }

    @Override
    public Word getPreviousWord() {
        return wordsTeacher.getPreviousWord();
    }

    @Override
    public void processingNextWord(BusinessCaller businessCaller) {
        executorService.submit(() -> {
            Word word = wordsTeacher.getNextNeedLearningWord();
            businessCaller.getData().putSerializable("word", word);
            businessCaller.callBusiness();
        });
    }

    @Override
    public Word getCurrentlyLearningWord() {
        return wordsTeacher.getCurrentlyLearningWord();
    }

    @Override
    public void desertWord(Word word, BusinessCaller businessCaller) {
        executorService.submit(() -> {
            if (word != null) {
                int id = word.getId();
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                        .getHaveGraspedMapper().removeWordRecordById(id);
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                        .getShallLearningMapper().removeWordRecordById(id);
                WordRecord wordRecord = new WordRecord(id, DateTimeUtil.getDateTime());
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                        .getDesertedLearningMapper().addWordRecord(wordRecord);

                learnedWord(word);

                businessCaller.callBusiness();
            }
        });
    }

    @Override
    public void graspWord(Word word, BusinessCaller businessCaller) {
        executorService.submit(() -> {
            if (word != null) {
                businessCaller.getData().putSerializable("word", word);

                int id = word.getId();
                WordRecord wordRecord = new WordRecord(id, DateTimeUtil.getDateTime());
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                        .getHaveGraspedMapper().addWordRecord(wordRecord);
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                        .getShallLearningMapper().removeWordRecordById(id);

                learnedWord(word);

                businessCaller.callBusiness();
            }
        });
    }

    @Override
    public void markWord(Word word, BusinessCaller businessCaller) {
        executorService.submit(() -> {
            if (word != null) {
                businessCaller.getData().putSerializable("word", word);

                int id = word.getId();
                WordRecord wordRecord = new WordRecord(id, DateTimeUtil.getDateTime());
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                        .getMarkedMapper().addWordRecord(wordRecord);

                businessCaller.callBusiness();
            }
        });
    }

    @Override
    public void removeWord(Word word, BusinessCaller businessCaller) {
        executorService.execute(() -> {
            if (word != null) {
                businessCaller.getData().putSerializable("word", word);

                wordsOperateProvider.getWordsOperator().removeWordFromList(word.getId());
                businessCaller.callBusiness();
            }
        });
    }


    @Override
    public void learnedWord(Word word) {
        wordsTeacher.removeWordFromList(word);
        executorService.submit(() -> {
            operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                    .getHaveLearnedTodayMapper().addWordIdInToday(word.getId());
        });
    }

    @Override
    public TeacherType getTeacherType() {
        return myAppSetting.getTeacherType();
    }

    @Override
    public LearningMode getLearningMode() {
        return LearningMode.getLearningMode(myAppSetting.getLearningMode());
    }


}
