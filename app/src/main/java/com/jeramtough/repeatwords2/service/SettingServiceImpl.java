package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.container.JtBeanContainer;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.bean.record.LearningRecordFactory;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.learning.scheme.LearningSchemeProvider;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.component.record.LearningRecordManager;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 11718
 * on 2018  May 05 Saturday 20:35.
 */
@JtServiceImpl
public class SettingServiceImpl implements SettingService {

    private MyAppSetting myAppSetting;
    private BaiduVoiceReader baiduVoiceReader;
    private LearningRecordManager learningRecordManager;
    private LearningSchemeProvider learningSchemeProvider;

    @IocAutowire
    public SettingServiceImpl(MyAppSetting myAppSetting,
                              BaiduVoiceReader baiduVoiceReader,
                              LearningRecordManager learningRecordManager,
                              LearningSchemeProvider learningSchemeProvider) {
        this.myAppSetting = myAppSetting;
        this.baiduVoiceReader = baiduVoiceReader;
        this.learningRecordManager = learningRecordManager;
        this.learningSchemeProvider = learningSchemeProvider;
    }


    @Override
    public void setPerLearningCount(int count) {
        myAppSetting.setPerLearningCount(count);
    }

    @Override
    public int getPerLearningCount() {
        return myAppSetting.getPerLearningCount();
    }

    @Override
    public void setReadEnglishSpeed(int degree) {
        baiduVoiceReader.getBaiduVoiceSetting().setReadEnglishSpeed(degree);
    }

    @Override
    public int getReadEnglishSpeed() {
        return baiduVoiceReader.getBaiduVoiceSetting().getReadEnglishSpeed();
    }

    @Override
    public void setRepeatIntervalTime(long time) {
        baiduVoiceReader.getBaiduVoiceSetting().setRepeatIntervalTime(time);
    }

    @Override
    public long getRepeatIntervalTime() {
        return baiduVoiceReader.getBaiduVoiceSetting().getRepeatIntervalTime();
    }

    @Override
    public int getTeacherType() {
        return myAppSetting.getTeacherType().getTeacherTypeId();
    }

    @Override
    public int getLearningMode() {
        return myAppSetting.getLearningMode();
    }

    @Override
    public void setTeacherType(int teacherTypeId) {
        myAppSetting.setTeacherType(TeacherType.getTeacherType(teacherTypeId));
    }

    @Override
    public void setLearningMode(int learningMode) {
        myAppSetting.setLearningMode(learningMode);
    }

    @Override
    public FutureTaskResponse backupTheLearningRecord(TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    HashMap<String, LearningRecord> learningRecords = new HashMap<>();
                    JtBeanContainer.getInstance().registerBean(LearningRecordFactory.class);
                    LearningRecordFactory learningRecordFactory =
                            JtBeanContainer.getInstance().getBean(LearningRecordFactory.class);
                    LearningRecord listenLearningRecord = learningRecordFactory.getListenLearningRecord();
                    LearningRecord speakLearningRecord =
                            learningRecordFactory.getSpeakLearningRecord();
                    LearningRecord writeLearningRecord = learningRecordFactory.getWriteLearningRecord();
                    LearningRecord readLearningRecord = learningRecordFactory.getReadLearningRecord();

                    learningRecords.put(learningRecordManager.getLearningRecordFileName(),
                            listenLearningRecord);
                    learningRecords.put(learningRecordManager.getSpeakingRecordFileName(),
                            speakLearningRecord);
                    learningRecords.put(learningRecordManager.getWritingRecordFileName(),
                            writeLearningRecord);
                    learningRecords.put(learningRecordManager.getReadingRecordFileName(),
                            readLearningRecord);

                    return learningRecordManager.backup(learningRecords);
                });
    }

    @Override
    public FutureTaskResponse recoverTheLearningRecord(TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    int denominator = 3;
                    preTaskResult.setMessage("Reading data from the json files");
                    runningTaskCallback.onTaskRunning(preTaskResult, 1, denominator);
                    Map<TeacherType, LearningRecord> learningRecordMap = learningRecordManager.recover();

                    preTaskResult.setMessage("Clear all word record");
                    runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
                    for (TeacherType teacherType : TeacherType.values()) {
                        learningSchemeProvider.getLearningScheme(
                                teacherType).clearAllWordRecord();
                    }

                    preTaskResult.setMessage("Start to recover the listening word record");
                    runningTaskCallback.onTaskRunning(preTaskResult, 3, denominator);
                    for (TeacherType teacherType : TeacherType.values()) {
                        learningSchemeProvider.getLearningScheme(
                                teacherType).addLearningRecord(
                                learningRecordMap.get(teacherType),
                                recordStateBean -> {
                                    preTaskResult.setMessage(String.format("%d-%d [%s]",
                                            recordStateBean.getIndex(),
                                            recordStateBean.getSum(),
                                            recordStateBean.getWordRecord().toString()));
                                    runningTaskCallback.onTaskRunning(preTaskResult, 3,
                                            denominator);
                                });
                    }

                    return true;
                });
    }

    @Override
    public FutureTaskResponse clearHaveLearnedWordToday(
            TaskCallbackInMain taskCallbackInMain) {
        return ResponseFactory.asyncDoing(taskCallbackInMain.get(),
                (preTaskResult, runningTaskCallback) -> {
                    learningSchemeProvider.getCurrentLearningScheme().removeWordRecords(
                            WordCondition.LEARNED_TODAY);
                    return true;
                });
    }


}
