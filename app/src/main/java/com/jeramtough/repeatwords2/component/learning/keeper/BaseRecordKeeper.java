package com.jeramtough.repeatwords2.component.learning.keeper;

import com.alibaba.fastjson.JSON;
import com.jeramtough.oedslib.entity.LargeWord;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.dao.dto.record.WordRecordDto;
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
abstract class BaseRecordKeeper implements RecordKeeper {

    OperateWordRecordMapperProvider operateWordRecordMapperProvider;
    MyAppSetting myAppSetting;
    WordsPool wordsPool;
    DictionaryMapper dictionaryMapper;


    public BaseRecordKeeper(
            OperateWordRecordMapperProvider operateWordRecordMapperProvider,
            MyAppSetting myAppSetting,
            WordsPool wordsPool,
            DictionaryMapper dictionaryMapper) {
        this.operateWordRecordMapperProvider = operateWordRecordMapperProvider;
        this.myAppSetting = myAppSetting;
        this.wordsPool = wordsPool;
        this.dictionaryMapper = dictionaryMapper;


    }


    @Override
    public WordRecordDto[] getWordRecordDtosByWordCondition(WordCondition wordCondition) {
        OperateWordRecordMapper operateWordRecordMapper =
                operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                        wordCondition);
        List<WordRecord> wordRecordList = operateWordRecordMapper.getWordRecordsOrderByLevel();
        WordRecordDto[] wordRecordDtos = new WordRecordDto[wordRecordList.size()];
        for (int i = 0; i < wordRecordList.size(); i++) {
            WordRecord wordRecord = wordRecordList.get(i);
            LargeWord largeWord = dictionaryMapper.selectOneByFdId(wordRecord.getWordId());
            WordRecordDto wordRecordDto = new WordRecordDto();
            wordRecordDto.setFdId(wordRecord.getFdId());
            wordRecordDto.setLevel(wordRecord.getLevel());
            wordRecordDto.setTag(largeWord.getTag());
            wordRecordDto.setTime(wordRecord.getTime());
            wordRecordDto.setWord(largeWord.getWord());
            wordRecordDto.setWordId(largeWord.getFdId());
            wordRecordDto.setPhonetic(largeWord.getPhonetic());
            wordRecordDto.setChExplain(largeWord.getChExplain());
            wordRecordDto.setMiniChExplain(
                    WordUtil.abbreviateChinese(largeWord.getChExplain()));
            wordRecordDtos[i] = wordRecordDto;
        }
        return wordRecordDtos;
    }

    @Override
    public void removeWordFromRecordList(WordRecordDto wordRecordDto) {
        String wordRecordDtoJson = JSON.toJSONString(wordRecordDto);
        WordDto wordDto = JSON.parseObject(wordRecordDtoJson, WordDto.class);
        wordDto.setFdId(wordRecordDto.getWordId());
        removeWordFromRecordList(wordDto);
    }

    @Override
    public void removeWordFromHaveLearnedTodayRecordList(Long wordId) {
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.LEARNED_TODAY).removeWordRecordByWordId(wordId);
    }

    /**
     * 抛弃不戳想学的单词，三个模式处理方式都一样
     */
    @Override
    public boolean addWordToDesertedRecordList(WordDto wordDto) {

        WordRecord wordRecord = operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.DESERTED).getWordRecordByWordId(wordDto.getFdId());
        if (wordRecord == null) {
            operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                    WordCondition.SHALL_LEARNING)
                                           .removeWordRecordByWordId(wordDto.getFdId());
            operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                    WordCondition.GRASPED)
                                           .removeWordRecordByWordId(wordDto.getFdId());
            operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                    WordCondition.MARKED)
                                           .removeWordRecordByWordId(wordDto.getFdId());

            wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
            operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                    WordCondition.DESERTED)
                                           .addWordRecord(wordRecord);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public boolean addWordToMarkedRecordList(WordDto wordDto) {
        WordRecord wordRecord = operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.MARKED).getWordRecordByWordId(wordDto.getFdId());
        if (wordRecord == null) {
            wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
            operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                    WordCondition.MARKED).addWordRecord(wordRecord);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void addWordToHaveLearnedTodayRecordList(WordDto wordDto) {
        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.LEARNED_TODAY).addWordRecord(wordRecord);

    }

    @Override
    public void removeWordFromDesertedRecordList(WordRecordDto wordRecordDto) {
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.DESERTED)
                                       .removeWordRecordByWordId(wordRecordDto.getWordId());

        WordRecord wordRecord = WordUtil.wordRecordDtoToWordRecord(wordRecordDto);
        wordRecord.setLevel(1);
        operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.SHALL_LEARNING)
                                       .addWordRecord(wordRecord);
    }


    //--------------------------------------------

    WordDto getWordDtoByWordId(Long wordId, List<Long> haveLearnedAtLeastTwiceIdsToday) {
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
            wordDto.setChExplain(wordDto.getChExplain());
        }
        return wordDto;
    }

    WordDto[] getWordDtosBywordId(List<Long> wordIds) {
        WordDto[] wordDtos = new WordDto[wordIds.size()];
        List<Long> haveLearnedAtLeastTwiceIdsToday = getHaveLearnedAtLeastTwiceIdsToday();
        for (int i = 0; i < wordIds.size(); i++) {
            Long wordId = wordIds.get(i);
            WordDto wordDto = getWordDtoByWordId(wordId, haveLearnedAtLeastTwiceIdsToday);
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
