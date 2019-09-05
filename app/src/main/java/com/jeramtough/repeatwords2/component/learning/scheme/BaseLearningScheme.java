package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;

/**
 * Created on 2019-09-01 14:17
 * by @author JeramTough
 */
abstract class BaseLearningScheme implements LearningScheme {

    DictionaryMapper dictionaryMapper;
    OperateWordRecordMapperProvider operateWordRecordMapperProvider;
    TeacherType teacherType;

    public BaseLearningScheme(DictionaryMapper dictionaryMapper,
                              OperateWordRecordMapperProvider operateWordRecordMapperProvider) {
        this.dictionaryMapper = dictionaryMapper;
        this.operateWordRecordMapperProvider = operateWordRecordMapperProvider;
        this.teacherType = loadTeacherType();
    }

    abstract TeacherType loadTeacherType();

    @Override
    public void clearAllWordRecord() {
        for (WordCondition wordCondition : WordCondition.values()) {
            operateWordRecordMapperProvider.getOperateWordsMapper(teacherType,
                    wordCondition).clearAll();
        }
    }

    @Override
    public void removeWordRecords(WordCondition wordCondition) {
        operateWordRecordMapperProvider.getOperateWordsMapper(teacherType,
                wordCondition).clearAll();
    }

    @Override
    public void addLearningRecord(LearningRecord learningRecord,
                                  CommonCallback<RecordStateBean> callback) {
        RecordStateBean recordStateBean = new RecordStateBean();
        recordStateBean.setSum(
                learningRecord.getShallLearningWordRecords().size()
                        + learningRecord.getHaveGraspedWordRecords().size()
                        + learningRecord.getMarkedWordRecords().size()
                        + learningRecord.getDesertedLearningWordRecords().size());
        recordStateBean.setIndex(0);

        for (WordRecord wordRecord : learningRecord.getShallLearningWordRecords()) {
            operateWordRecordMapperProvider.getOperateWordsMapper(teacherType,
                    WordCondition.SHALL_LEARNING).addWordRecord(wordRecord);
            recordStateBean.setIndex(recordStateBean.getIndex() + 1);
            recordStateBean.setWordRecord(wordRecord);
            callback.callback(recordStateBean);
        }
        for (WordRecord wordRecord : learningRecord.getHaveGraspedWordRecords()) {
            operateWordRecordMapperProvider.getOperateWordsMapper(teacherType,
                    WordCondition.GRASPED).addWordRecord(wordRecord);
            recordStateBean.setIndex(recordStateBean.getIndex() + 1);
            recordStateBean.setWordRecord(wordRecord);
            callback.callback(recordStateBean);
        }
        for (WordRecord wordRecord : learningRecord.getMarkedWordRecords()) {
            operateWordRecordMapperProvider.getOperateWordsMapper(teacherType,
                    WordCondition.MARKED).addWordRecord(wordRecord);
            recordStateBean.setIndex(recordStateBean.getIndex() + 1);
            recordStateBean.setWordRecord(wordRecord);
            callback.callback(recordStateBean);
        }
        for (WordRecord wordRecord : learningRecord.getDesertedLearningWordRecords()) {
            operateWordRecordMapperProvider.getOperateWordsMapper(teacherType,
                    WordCondition.DESERTED).addWordRecord(wordRecord);
            recordStateBean.setIndex(recordStateBean.getIndex() + 1);
            recordStateBean.setWordRecord(wordRecord);
            callback.callback(recordStateBean);
        }
    }


}
