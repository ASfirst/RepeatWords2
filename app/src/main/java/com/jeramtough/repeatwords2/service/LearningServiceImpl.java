package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordWithIsLearnedAtLeastTwiceToday;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.learningmode.wordoperator.WordsOperateProvider;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.component.teacher.WordsTeacher;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.DictionaryMapper1;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author 11718
 * on 2018  May 03 Thursday 19:04.
 */
@JtServiceImpl
public class LearningServiceImpl implements LearningService {
    private DictionaryMapper1 dictionaryMapper1;
    private WordsTeacher wordsTeacher;
    private WordsPool wordsPool;
    private MyAppSetting myAppSetting;
    private OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider;
    private WordsOperateProvider wordsOperateProvider;


    private ExecutorService executorService;

    @IocAutowire
    private LearningServiceImpl(DictionaryMapper1 dictionaryMapper1, WordsTeacher wordsTeacher,
                                WordsPool wordsPool, MyAppSetting myAppSetting,
                                OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider,
                                WordsOperateProvider wordsOperateProvider) {
        this.operateWordMapperFactoryProvider = operateWordMapperFactoryProvider;
        this.dictionaryMapper1 = dictionaryMapper1;
        this.wordsTeacher = wordsTeacher;
        this.wordsPool = wordsPool;
        this.myAppSetting = myAppSetting;
        this.wordsOperateProvider = wordsOperateProvider;

        executorService = JtExecutors.newSingleThreadExecutor();
    }

    @Override
    public void initTeacher(final BusinessCaller businessCaller) {
        executorService.submit(() -> {

            int perLearningCount = myAppSetting.getPerLearningCount();

            List<Integer> shallLearningIds = wordsOperateProvider.getWordsOperator()
                                                                 .getWordIdsOfNeeding(
                                                                         perLearningCount);
            List<Integer> todaysHaveLearnedWordsIdAtLeastTwice = wordsOperateProvider
                    .getWordsOperator()
                    .getTodaysHaveLearnedWordsIdAtLeastTwice();

            for (Integer id : shallLearningIds) {
                Word word = wordsPool.getWord(id);
                if (word == null) {
                    word = dictionaryMapper1.getWord(id);
                    wordsPool.addWord(word);
                }
                wordsTeacher.addWordToList(word);
            }

            Word[] shallLearningWords = wordsTeacher.getAllRandomNeedLearningWords();
            WordWithIsLearnedAtLeastTwiceToday[]
                    wordWithIsLearnedAtLeastTwiceTodays = wordsTeacher
                    .getAllRandomNeedLearningWordsWithIsLearedTodayAtLeastTwice
                            (todaysHaveLearnedWordsIdAtLeastTwice);

            businessCaller.getData()
                          .putInt("shallLearningSize", shallLearningWords.length);
            businessCaller.getData().putSerializable("shallLearningWords", shallLearningWords);
            businessCaller.getData().putSerializable("wordWithIsLearnedAtLeastTwiceTodays",
                    wordWithIsLearnedAtLeastTwiceTodays);

            wordsTeacher.clear();

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
                                                .getHaveGraspedMapper().removeWordRecordById(
                        id);
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                                                .getShallLearningMapper().removeWordRecordById(
                        id);
                WordRecord wordRecord = new WordRecord(null, null, DateTimeUtil.getDateTime(),
                        null);
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                                                .getDesertedLearningMapper().addWordRecord(
                        wordRecord);

                //learnedWordInToday(word);
                wordsTeacher.removeWordFromList(word);

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
                WordRecord wordRecord = new WordRecord(null, null, DateTimeUtil.getDateTime(),
                        null);
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                                                .getHaveGraspedMapper().addWordRecord(
                        wordRecord);
                operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                                                .getShallLearningMapper().removeWordRecordById(
                        id);

                learnedWordInToday(word);

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
                boolean has = operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                                                              .getMarkedMapper().hasWordId(id);
                if (!has) {
                    WordRecord wordRecord = new WordRecord(null, id,
                            DateTimeUtil.getDateTime(), null);
                    operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
                                                    .getMarkedMapper().addWordRecord(
                            wordRecord);

                    businessCaller.setSuccessful(true);
                }
                else {
                    businessCaller.setSuccessful(false);
                }


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
    public void learnedWordInToday(Word word) {
        wordsTeacher.removeWordFromList(word);

        executorService.submit(() -> {
            WordRecord wordRecord = new WordRecord(null, null, DateTimeUtil.getDateTime(),
                    null);
            wordsOperateProvider.getWordsOperator().learnWordToday(wordRecord);
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
