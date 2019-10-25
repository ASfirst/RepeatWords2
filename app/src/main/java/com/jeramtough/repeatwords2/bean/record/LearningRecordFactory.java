package com.jeramtough.repeatwords2.bean.record;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;

/**
 * Created on 2019-09-05 22:55
 * by @author JeramTough
 */
@JtComponent(pattern = JtBeanPattern.Prototype)
public class LearningRecordFactory {

    private OperateWordRecordMapperProvider operateWordRecordMapperProvider;

    @IocAutowire
    public LearningRecordFactory(
            @InjectComponent(impl = DefaultOperateWordRecordMapperProvider.class)
                    OperateWordRecordMapperProvider operateWordRecordMapperProvider) {
        this.operateWordRecordMapperProvider = operateWordRecordMapperProvider;
    }


    public LearningRecord getListenLearningRecord() {
        return getLearningRecord(TeacherType.LISTENING_TEACHER);
    }

    public LearningRecord getSpeakLearningRecord() {
        return getLearningRecord(TeacherType.SPEAKING_TEACHER);
    }

    public LearningRecord getWriteLearningRecord() {
        return getLearningRecord(TeacherType.WRITING_TEACHER);
    }

    public LearningRecord getReadLearningRecord() {
        return getLearningRecord(TeacherType.READING_TEACHER);
    }

    //*************************

    private LearningRecord getLearningRecord(TeacherType teacherType) {
        LearningRecord learningRecord = new LearningRecord();
        learningRecord.setShallLearningWordRecords(
                operateWordRecordMapperProvider.getOperateWordsMapper(
                        teacherType,
                        WordCondition.SHALL_LEARNING).getWordRecords());
        learningRecord.setHaveGraspedWordRecords(
                operateWordRecordMapperProvider.getOperateWordsMapper(
                        teacherType,
                        WordCondition.GRASPED).getWordRecords());
        learningRecord.setMarkedWordRecords(
                operateWordRecordMapperProvider.getOperateWordsMapper(
                        teacherType,
                        WordCondition.MARKED).getWordRecords());
        learningRecord.setDesertedLearningWordRecords(
                operateWordRecordMapperProvider.getOperateWordsMapper(
                        teacherType,
                        WordCondition.DESERTED).getWordRecords());

        return learningRecord;
    }

}
