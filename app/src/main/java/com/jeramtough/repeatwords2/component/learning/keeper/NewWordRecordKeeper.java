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
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.util.WordUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created on 2019-09-02 23:02
 * by @author JeramTough
 */
@JtComponent
public class NewWordRecordKeeper extends BaseRecordKeeper {


    @IocAutowire
    public NewWordRecordKeeper(
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

        //找出所有需要学习的wordId
        List<Long> tempShallLearningIds = shallLearningOperateWordRecordMapper.getAllWordIdsOrderByLevel();

        // filtrate words while have learned twice in today
        List<Long> haveLearnedAtLeastTwiceWordIds = getHaveLearnedAtLeastTwiceIdsToday();
        for (Long haveLearnedAtLeastTwiceWordId : haveLearnedAtLeastTwiceWordIds) {
            tempShallLearningIds.remove(haveLearnedAtLeastTwiceWordId);
        }

        //真正用来装用户设置需要的单词
        List<Long> shallLearningIds = new ArrayList<>(
                super.myAppSetting.getPerLearningCount());

        for (Long tempShallLearningId : tempShallLearningIds) {
            shallLearningIds.add(tempShallLearningId);
            //拿出1.5倍的单词
            if (shallLearningIds.size() == super.myAppSetting.getPerLearningCount() * 1.5) {
                break;
            }
        }

        // random sort
        Collections.shuffle(shallLearningIds);

        //截取出真正设置的长度
        shallLearningIds = shallLearningIds.subList(0,
                super.myAppSetting.getPerLearningCount());

        return getWordDtosBywordId(shallLearningIds);
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
