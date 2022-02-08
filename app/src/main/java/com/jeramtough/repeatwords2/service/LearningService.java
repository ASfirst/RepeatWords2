package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * @author 11718
 * on 2018  May 03 Thursday 18:49.
 */
public interface LearningService {

    FutureTaskResponse initTeacher(
            TaskCallbackInMain taskCallback);

    /**
     * 使用状态模式，当是学习模式、复习模式或者收藏单词模式，自动适配行为
     */
    FutureTaskResponse graspOrRemoveWord(WordDto wordDto, TaskCallbackInMain taskCallback);

    FutureTaskResponse desertWord(WordDto wordDto, TaskCallbackInMain taskCallback);

    FutureTaskResponse markWord(WordDto wordDto, TaskCallbackInMain taskCallback);

    FutureTaskResponse learnedWordInToday(WordDto wordDto);
}
