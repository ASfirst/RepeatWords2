package com.jeramtough.repeatwords2.service.impl;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.school.DefaultSchool;
import com.jeramtough.repeatwords2.component.learning.school.School;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.dao.dto.record.WordRecordDto;
import com.jeramtough.repeatwords2.service.ListService1;

/**
 * Created on 2019-09-04 18:18
 * by @author JeramTough
 */
@JtServiceImpl
public class ListService1Impl implements ListService1 {

    private School school;

    @IocAutowire
    public ListService1Impl(
            @InjectComponent(impl = DefaultSchool.class) School school) {
        this.school = school;
    }

    @Override
    public FutureTaskResponse getHaveGraspedWords(TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    WordRecordDto[] wordRecordDtos =
                            school.getCurrentTeacher().getWordRecordDtosByWordCondition(
                                    WordCondition.GRASPED);
                    preTaskResult.putPayload("wordRecordDtos", wordRecordDtos);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse getShallLearningWords(TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    WordRecordDto[] wordRecordDtos =
                            school.getCurrentTeacher().getWordRecordDtosByWordCondition(
                                    WordCondition.SHALL_LEARNING);
                    preTaskResult.putPayload("wordRecordDtos", wordRecordDtos);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse getMarkedWords(TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    WordRecordDto[] wordRecordDtos =
                            school.getCurrentTeacher().getWordRecordDtosByWordCondition(
                                    WordCondition.MARKED);
                    preTaskResult.putPayload("wordRecordDtos", wordRecordDtos);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse getDesertedWords(TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    WordRecordDto[] wordRecordDtos =
                            school.getCurrentTeacher().getWordRecordDtosByWordCondition(
                                    WordCondition.DESERTED);
                    preTaskResult.putPayload("wordRecordDtos", wordRecordDtos);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse getHaveLearnedWordRecordsInToday(
            TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    WordRecordDto[] wordRecordDtos =
                            school.getCurrentTeacher().getWordRecordDtosByWordCondition(
                                    WordCondition.LEARNED_TODAY);
                    preTaskResult.putPayload("wordRecordDtos", wordRecordDtos);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse removeWordFromHaveLearnedTodayList(WordRecordDto wordRecordDto,
                                                                 TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    school.getCurrentTeacher().removeWordFromHaveLearnedTodayRecordList(
                            wordRecordDto.getWordId());
                    return true;
                });
    }

    @Override
    public FutureTaskResponse removeWordFromHaveGraspedList(WordRecordDto wordRecordDto,
                                                            TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    //在复习模式下移除已掌握单词，会添加到新学单词里边
                    school.getReviewWordTeacher().removeWordFromRecordList(wordRecordDto);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse removeWordFromShallLearningList(WordRecordDto wordRecordDto,
                                                              TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    //在新学模式下移除单词，会添加到已掌握单词里边
                    school.getNewWordTeacher().removeWordFromRecordList(wordRecordDto);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse removeWordFromMarkedList(WordRecordDto wordRecordDto,
                                                       TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    school.getMarkWordTeacher().removeWordFromRecordList(wordRecordDto);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse removeWordFromDesertedList(WordRecordDto wordRecordDto,
                                                         TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    school.getCurrentTeacher().removeWordFromDesertedRecordList(wordRecordDto);
                    return true;
                });
    }
}
