package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;

/**
 * @author 11718
 * on 2018  May 03 Thursday 18:49.
 */
public interface LearningService {

    void initTeacher(BusinessCaller businessCaller);

    @Deprecated
    Word getPreviousWord();

    @Deprecated
    void processingNextWord(BusinessCaller businessCaller);

    @Deprecated
    Word getCurrentlyLearningWord();

    void graspWord(Word word, BusinessCaller businessCaller);

    void desertWord(Word word, BusinessCaller businessCaller);

    void markWord(Word word, BusinessCaller businessCaller);

    /**
     * 使用状态模式，当是复习模式或者收藏单词模式，自动适配行为
     */
    void removeWord(Word word, BusinessCaller businessCaller);

    void learnedWord(Word word);

    TeacherType getTeacherType();

    LearningMode getLearningMode();

}
