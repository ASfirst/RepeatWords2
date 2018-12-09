package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.bean.youdao.YoudaoQueryResult;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.component.youdao.YoudaoTranslator;
import com.jeramtough.repeatwords2.dao.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.List;
import java.util.concurrent.Executor;

@JtServiceImpl class DictionaryServiceImpl implements DictionaryService {

    private DictionaryMapper dictionaryMapper;
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;
    private YoudaoTranslator youdaoTranslator;
    private WordsPool wordsPool;

    private Executor executor;

    @IocAutowire
    DictionaryServiceImpl(DictionaryMapper dictionaryMapper,
                          OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider,
                          YoudaoTranslator youdaoTranslator,
                          WordsPool wordsPool) {
        this.dictionaryMapper = dictionaryMapper;
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
        this.youdaoTranslator = youdaoTranslator;
        this.wordsPool = wordsPool;

        executor = JtExecutors.newCachedThreadPool();
    }

    @Override
    public void getDictionaryWords(BusinessCaller businessCaller) {
        executor.execute(() -> {
            List<Word> words = dictionaryMapper.getAllWordsOrderById();
            Word[] words1 = words.toArray(new Word[words.size()]);

            wordsPool.clear();
            wordsPool.addWords(words);

            businessCaller.getData().putSerializable("words", words1);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void addNewWordIntoDictionary(String ch, String en, BusinessCaller businessCaller) {
        executor.execute(() -> {
            Word word = new Word();
            word.setCh(ch);
            word.setEn(en);
            YoudaoQueryResult youdaoQueryResult = youdaoTranslator.translate(en);
            if (youdaoQueryResult.getBasic() != null) {
                word.setPhonetic(youdaoQueryResult.getBasic().getUkPhonetic());
            }
            dictionaryMapper.addNewWord(word);
            Word newWord = dictionaryMapper.getWordByEn(en);
            operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory().getShallLearningMapper().addWordRecord(
                    new WordRecord(newWord.getId(), DateTimeUtil.getDateTime()));
            operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory().getShallLearningMapper().addWordRecord(
                    new WordRecord(newWord.getId(), DateTimeUtil.getDateTime()));
            operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory().getShallLearningMapper().addWordRecord(
                    new WordRecord(newWord.getId(), DateTimeUtil.getDateTime()));

            getDictionaryWords(businessCaller);
        });
    }

    @Override
    public void addNewWordIntoDictionary(Word word, BusinessCaller businessCaller) {
        executor.execute(() -> {
            if (word.getPhonetic().length() == 0 || word.getPhonetic() == null) {
                YoudaoQueryResult youdaoQueryResult = youdaoTranslator.translate(word.getEn());
                if (youdaoQueryResult.getBasic() != null) {
                    word.setPhonetic(youdaoQueryResult.getBasic().getUkPhonetic());
                }
            }

            dictionaryMapper.addNewWord(word);
            //n ew word just have the id
            Word newWord = dictionaryMapper.getWordByEn(word.getEn());
            operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory().getShallLearningMapper().addWordRecord(
                    new WordRecord(newWord.getId(), DateTimeUtil.getDateTime()));
            operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory().getShallLearningMapper().addWordRecord(
                    new WordRecord(newWord.getId(), DateTimeUtil.getDateTime()));
            operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory().getShallLearningMapper().addWordRecord(
                    new WordRecord(newWord.getId(), DateTimeUtil.getDateTime()));

            getDictionaryWords(businessCaller);
        });
    }

    @Override
    public void deleteWordFromDictionary(int wordId, BusinessCaller businessCaller) {
        executor.execute(() -> {
            dictionaryMapper.deleteWordById(wordId);

            operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory().getHaveGraspedMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory().getShallLearningMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory().getMarkedMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory().getDesertedLearningMapper().removeWordRecordById(
                    wordId);

            operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory().getHaveGraspedMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory().getShallLearningMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory().getMarkedMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory().getDesertedLearningMapper().removeWordRecordById(
                    wordId);

            operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory().getHaveGraspedMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory().getShallLearningMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory().getMarkedMapper().removeWordRecordById(
                    wordId);
            operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory().getDesertedLearningMapper().removeWordRecordById(
                    wordId);

            getDictionaryWords(businessCaller);
        });
    }

    @Override
    public void modifyWordOfDictionary(Word word, BusinessCaller businessCaller) {
        executor.execute(() -> {
            if (word.getPhonetic().length() == 0 || word.getPhonetic() == null) {
                YoudaoQueryResult youdaoQueryResult = youdaoTranslator.translate(word.getEn());
                if (youdaoQueryResult.getBasic() != null) {
                    word.setPhonetic(youdaoQueryResult.getBasic().getUkPhonetic());
                }
            }
            dictionaryMapper.addWord(word);
            getDictionaryWords(businessCaller);

        });
    }

}
