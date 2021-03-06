package com.jeramtough.repeatwords2.component.learning.keeper;

import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.dao.dto.record.WordRecordDto;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * 管理所有的单词记录的使用情况
 *
 * @author 11718
 */
public interface RecordKeeper {


    WordDto[] getRandomNeedLearningWords();

    WordRecordDto[] getWordRecordDtosByWordCondition(WordCondition wordCondition);

    /**
     * 添加单词到单词记录列表里边
     */
    void addWordToRecordList(WordDto wordDto);

    /**
     * 从列表里面移除单词记录，各个子类自适应行为
     */
    void removeWordFromRecordList(WordDto wordDto);

    void removeWordFromRecordList(WordRecordDto wordRecordDto);


    void removeWordFromHaveLearnedTodayRecordList(Long wordId);

    /**
     * 不再学习该单词
     */
    boolean addWordToDesertedRecordList(WordDto wordDto);

    /**
     * 收藏该单词
     */
    boolean addWordToMarkedRecordList(WordDto wordDto);

    /**
     * 添加单词到今日已学记录
     */
    void addWordToHaveLearnedTodayRecordList(WordDto wordDto);

    /**
     * 恢复单词从放弃学习的单词记录列表里面
     */
    void removeWordFromDesertedRecordList(WordRecordDto wordRecordDto);

}
