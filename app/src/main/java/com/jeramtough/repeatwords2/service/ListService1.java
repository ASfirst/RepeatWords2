package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.dao.dto.record.WordRecordDto;

/**
 * Created on 2019-09-04 18:17
 * by @author JeramTough
 */
public interface ListService1 {

    FutureTaskResponse getHaveGraspedWords(TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse getShallLearningWords(TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse getMarkedWords(TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse getDesertedWords(TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse getHaveLearnedWordRecordsInToday(TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse removeWordFromHaveLearnedTodayList(WordRecordDto wordRecordDto,
                                                          TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse removeWordFromHaveGraspedList(WordRecordDto wordRecordDto,
                                                     TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse removeWordFromShallLearningList(WordRecordDto wordRecordDto,
                                                       TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse removeWordFromMarkedList(WordRecordDto wordRecordDto,
                                                TaskCallbackInMain taskCallbackInMain);

    FutureTaskResponse removeWordFromDesertedList(WordRecordDto wordRecordDto,
                                                  TaskCallbackInMain taskCallbackInMain);
}
