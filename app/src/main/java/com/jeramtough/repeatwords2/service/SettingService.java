package com.jeramtough.repeatwords2.service;

import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;

/**
 * @author 11718
 * on 2018  May 05 Saturday 20:35.
 */
public interface SettingService {
    void setPerLearningCount(int count);

    int getPerLearningCount();

    void setReadEnglishSpeed(int degree);

    int getReadEnglishSpeed();

    void setRepeatIntervalTime(long time);

    long getRepeatIntervalTime();


    int getTeacherType();

    int getLearningMode();

    void setTeacherType(int teacherTypeId);

    void setLearningMode(int learningMode);

    void backupTheLearningRecord(TaskCallbackInMain taskCallbackInMain);

    void recoverTheLearningRecord(TaskCallbackInMain taskCallbackInMain);

    void clearHaveLearnedWordToday(TaskCallbackInMain taskCallbackInMain);
}
