package com.jeramtough.repeatwords2.component.learning.keeper;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.util.WordUtil;

import java.util.List;

/**
 * Created on 2019-09-03 18:30
 * by @author JeramTough
 */
@JtComponent
public class MarkWordRecordKeeper extends BaseRecordKeeper {

    @IocAutowire
    public MarkWordRecordKeeper(
            @InjectComponent(impl = DefaultOperateWordRecordMapperProvider.class)
                    OperateWordRecordMapperProvider operateWordRecordMapperProvider,
            MyAppSetting myAppSetting,
            WordsPool wordsPool,
            DictionaryMapper dictionaryMapper) {
        super(operateWordRecordMapperProvider, myAppSetting, wordsPool,
                dictionaryMapper);
    }

    @Override
    public WordDto[] getRandomNeedLearningWords() {
        List<Long> needLearnedWordIds =
                super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                        WordCondition.MARKED).getWordIdsForRandom1(
                        super.myAppSetting.getPerLearningCount());

        return getWordDtosBywordId(needLearnedWordIds);
    }

    @Override
    public void addWordToRecordList(WordDto wordDto) {
        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.MARKED)
                                             .addWordRecord(wordRecord);
    }

    @Override
    public void removeWordFromRecordList(WordDto wordDto) {
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.MARKED).removeWordRecordByWordId(wordDto.getFdId());
    }
}
