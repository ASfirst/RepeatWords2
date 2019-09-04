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
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.util.WordUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-09-03 12:03
 * by @author JeramTough
 */
@JtComponent
public class ReviewWordTeacher extends BaseTeacher {

    @IocAutowire
    public ReviewWordTeacher(
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

        //拿出今日已学超过两次的单词四分之一
        int sizeFromHaveLearnedAtLeastTwiceToday = super.myAppSetting.getPerLearningCount() / 4;
        //能提供的
        List<Long> haveLearnedAtLeastTwiceIdsToday = getHaveLearnedAtLeastTwiceIdsToday();
        //当能提供的小于需要学的，按能提供的大小计算
        if (haveLearnedAtLeastTwiceIdsToday.size() < sizeFromHaveLearnedAtLeastTwiceToday) {
            sizeFromHaveLearnedAtLeastTwiceToday = haveLearnedAtLeastTwiceIdsToday.size();
        }
        haveLearnedAtLeastTwiceIdsToday = haveLearnedAtLeastTwiceIdsToday.subList(0,
                sizeFromHaveLearnedAtLeastTwiceToday - 1);

        //从已掌握的单词里边拿出剩下的3/4或则更多
        List<Long> haveGraspedWordIds =
                super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                        WordCondition.GRASPED).getWordIdsForRandom1(
                        super.myAppSetting.getPerLearningCount() - sizeFromHaveLearnedAtLeastTwiceToday);

        //加起来就是要复习的单词
        List<Long> needReviewedWordIds = new ArrayList<>();
        needReviewedWordIds.addAll(haveLearnedAtLeastTwiceIdsToday);
        needReviewedWordIds.addAll(haveGraspedWordIds);
        return getWordDtosById(needReviewedWordIds);
    }

    /**
     * 用不上，因为在新学单词模式下，掌握的单词直接添加了
     */
    @Override
    public void addWordToRecordList(WordDto wordDto) {

    }

    /**
     * 复习模式下，又把以掌握的单词恢复到需要学习的单词列表里面，并且等级加1
     */
    @Override
    public void removeWordFromRecordList(WordDto wordDto) {
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.GRASPED)
                                             .removeWordRecordByWordId(wordDto.getFdId());
        WordRecord wordRecord = WordUtil.wordDtoToWordRecord(wordDto);
        wordRecord.setLevel(1);
        super.operateWordRecordMapperProvider.getCurrentOperateWordsMapper(
                WordCondition.SHALL_LEARNING)
                                             .addWordRecord(wordRecord);
    }
}
