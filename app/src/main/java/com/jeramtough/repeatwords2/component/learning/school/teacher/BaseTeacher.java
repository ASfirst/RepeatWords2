package com.jeramtough.repeatwords2.component.learning.school.teacher;

import com.alibaba.fastjson.JSON;
import com.jeramtough.oedslib.entity.LargeWord;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.util.WordUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019-09-02 22:57
 * by @author JeramTough
 */
abstract class BaseTeacher implements Teacher1 {

    OperateWordRecordMapperProvider operateWordRecordMapperProvider;
    MyAppSetting myAppSetting;
    WordsPool wordsPool;
    DictionaryMapper dictionaryMapper;


    public BaseTeacher(
            OperateWordRecordMapperProvider operateWordRecordMapperProvider,
            MyAppSetting myAppSetting,
            WordsPool wordsPool,
            DictionaryMapper dictionaryMapper) {
        this.operateWordRecordMapperProvider = operateWordRecordMapperProvider;
        this.myAppSetting = myAppSetting;
        this.wordsPool = wordsPool;
        this.dictionaryMapper = dictionaryMapper;


    }

    /**
     * 抛弃不戳想学的单词，三个模式处理方式都一样
     */
    @Override
    public void addWordToDesertedRecordList(WordDto wordDto) {
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.SHALL_LEARNING)
                                       .removeWordRecordByWordId(wordDto.getFdId());
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.GRASPED)
                                       .removeWordRecordByWordId(wordDto.getFdId());
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.MARKED)
                                       .removeWordRecordByWordId(wordDto.getFdId());

        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(WordCondition.DESERTED)
                                       .addWordRecord(wordRecord);
    }

    @Override
    public void recoverWordFromDesertedRecordList(WordDto wordDto) {
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.DESERTED)
                                       .removeWordRecordByWordId(wordDto.getFdId());

        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        wordRecord.setLevel(1);
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.SHALL_LEARNING)
                                       .addWordRecord(wordRecord);
    }


    WordDto[] getWordDtosById(List<Long> wordIds) {
        WordDto[] wordDtos = new WordDto[wordIds.size()];
        List<Long> haveLearnedAtLeastTwiceIdsToday = getHaveLearnedAtLeastTwiceIdsToday();
        for (int i = 0; i < wordIds.size(); i++) {
            Long wordId = wordIds.get(i);
            WordDto wordDto = wordsPool.getWordDTO(wordId);
            if (wordDto == null) {
                LargeWord largeWord = dictionaryMapper.selectOneByFdId(wordId);
                String beanJson = JSON.toJSONString(largeWord);
                wordDto = JSON.parseObject(beanJson, WordDto.class);
                if (haveLearnedAtLeastTwiceIdsToday.contains(wordDto.getFdId())) {
                    wordDto.setLearnedAtLeastTwiceToday(true);
                }
                else {
                    wordDto.setLearnedAtLeastTwiceToday(false);
                }
            }
            wordDtos[i] = wordDto;
        }
        return wordDtos;
    }

    List<Long> getHaveLearnedAtLeastTwiceIdsToday() {

        OperateWordRecordMapper learnedTodayOperateWordRecordMapper =
                operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                        WordCondition.LEARNED_TODAY);
        Map<Long, Integer> wordIdCountMap = new HashMap<>(50);

        List<Long> wordIds = learnedTodayOperateWordRecordMapper.getWordIds1();
        for (Long wordId : wordIds) {
            Integer count = wordIdCountMap.get(wordId);
            if (count == null) {
                count = 1;
                wordIdCountMap.put(wordId, count);
            }
            else {
                count = count + 1;
                wordIdCountMap.put(wordId, count);
            }
        }

        List<Long> ids = new ArrayList<>();
        final int atLeast = 2;
        for (Map.Entry<Long, Integer> entry : wordIdCountMap.entrySet()) {
            if (entry.getValue() > atLeast) {
                ids.add(entry.getKey());
            }
        }

        return ids;

    }


}
