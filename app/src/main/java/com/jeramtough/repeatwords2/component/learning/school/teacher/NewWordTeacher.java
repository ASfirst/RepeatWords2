package com.jeramtough.repeatwords2.component.learning.school.teacher;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.util.WordUtil;

import java.util.Iterator;
import java.util.List;


/**
 * Created on 2019-09-02 23:02
 * by @author JeramTough
 */
@JtComponent
public class NewWordTeacher extends BaseTeacher {


    @IocAutowire
    public NewWordTeacher(
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
        OperateWordRecordMapper shallLearningOperateWordRecordMapper =
                super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                        WordCondition.SHALL_LEARNING);

        //找出具体需要学习的wordId，用户设置超过就用剩下的
        List<Long> shallLearningIds =
                shallLearningOperateWordRecordMapper.getWordIdsForRandomOrderByLevel(
                        super.myAppSetting.getPerLearningCount());


        // filtrate words while have learned twice in today
        List<Long> haveLearnedAtLeastTwiceWordIds = getHaveLearnedAtLeastTwiceIdsToday();
        Iterator<Long> iterator = shallLearningIds.iterator();
        while (iterator.hasNext()) {
            Long shallLearningId = iterator.next();
            boolean isFiltrated = false;
            for (Long haveLearnedAtLeastTwiceWordId : haveLearnedAtLeastTwiceWordIds) {
                if (shallLearningId.equals(haveLearnedAtLeastTwiceWordId)) {
                    isFiltrated = true;
                    break;
                }
            }
            if (isFiltrated) {
                iterator.remove();
            }
        }
        return getWordDtosById(shallLearningIds);
    }

    /**
     * 在第一次启动app时能用得上
     */
    @Override
    public void addWordToRecordList(WordDto wordDto) {
        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.SHALL_LEARNING)
                                             .addWordRecord(wordRecord);
    }

    /**
     * 从将要学的单词列表里面移除单词，并添加到已掌握的单词里边
     */
    @Override
    public void removeWordFromRecordList(WordDto wordDto) {
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.SHALL_LEARNING)
                                             .removeWordRecordByWordId(wordDto.getFdId());
        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.GRASPED)
                                             .addWordRecord(wordRecord);
    }



    //************************

}
