package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.bean.word.Dictionary;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.dao.mapper.*;
import com.jeramtough.repeatwords2.component.dictionary.DictionaryManager;
import com.jeramtough.repeatwords2.component.record.LearningRecordManager;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.mapper.factory.ListeningTeacherOperateWordsMapperFactory;
import com.jeramtough.repeatwords2.dao.mapper.factory.SpeakingTeacherOperateWordsMapperFactory;
import com.jeramtough.repeatwords2.dao.mapper.factory.WritingTeacherOperateWordsMapperFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author 11718
 * on 2018  May 05 Saturday 20:35.
 */
@JtServiceImpl
class SettingServiceImpl implements SettingService {

    private MyAppSetting myAppSetting;
    private BaiduVoiceReader baiduVoiceReader;
    private DictionaryMapper dictionaryMapper;

    private ListeningTeacherOperateWordsMapperFactory listeningTeacherOperateWordsMapperFactory;
    private SpeakingTeacherOperateWordsMapperFactory speakingTeacherOperateWordsMapperFactory;
    private WritingTeacherOperateWordsMapperFactory writingTeacherOperateWordsMapperFactory;

    private LearningRecordManager learningRecordManager;
    private DictionaryManager dictionaryManager;

    private Executor executor;

    @IocAutowire
    private SettingServiceImpl(MyAppSetting myAppSetting, BaiduVoiceReader baiduVoiceReader,
                               DictionaryMapper dictionaryMapper, ListeningTeacherOperateWordsMapperFactory listeningTeacherOperateWordsMapperFactory, SpeakingTeacherOperateWordsMapperFactory speakingTeacherOperateWordsMapperFactory, WritingTeacherOperateWordsMapperFactory writingTeacherOperateWordsMapperFactory, LearningRecordManager learningRecordManager, DictionaryManager dictionaryManager) {
        this.myAppSetting = myAppSetting;
        this.baiduVoiceReader = baiduVoiceReader;
        this.dictionaryMapper = dictionaryMapper;
        this.listeningTeacherOperateWordsMapperFactory = listeningTeacherOperateWordsMapperFactory;
        this.speakingTeacherOperateWordsMapperFactory = speakingTeacherOperateWordsMapperFactory;
        this.writingTeacherOperateWordsMapperFactory = writingTeacherOperateWordsMapperFactory;
        this.learningRecordManager = learningRecordManager;
        this.dictionaryManager = dictionaryManager;


        executor = JtExecutors.newSingleThreadExecutor();
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
    public void backupTheLearningRecord(BusinessCaller businessCaller) {
        executor.execute(() -> {
            Dictionary dictionary = new Dictionary();
            dictionary.setWords(dictionaryMapper.getAllWordsOrderByEn());

            HashMap<String, LearningRecord> learningRecords = new HashMap<>();
            LearningRecord listenLearningRecord = new LearningRecord();
            LearningRecord speakLearningRecord = new LearningRecord();
            LearningRecord writeLearningRecord = new LearningRecord();

            listenLearningRecord.setHaveGraspedWordRecords(listeningTeacherOperateWordsMapperFactory.getHaveGraspedMapper().getWordRecordsOrderById());
            listenLearningRecord.setShallLearningWordRecords(listeningTeacherOperateWordsMapperFactory.getShallLearningMapper().getWordRecordsOrderById());
            listenLearningRecord.setMarkedWordRecords(listeningTeacherOperateWordsMapperFactory.getMarkedMapper().getWordRecordsOrderById());
            listenLearningRecord.setDesertedLearningWordRecords(listeningTeacherOperateWordsMapperFactory.getDesertedLearningMapper().getWordRecordsOrderById());

            speakLearningRecord.setHaveGraspedWordRecords(speakingTeacherOperateWordsMapperFactory.getHaveGraspedMapper().getWordRecordsOrderById());
            speakLearningRecord.setShallLearningWordRecords(speakingTeacherOperateWordsMapperFactory.getShallLearningMapper().getWordRecordsOrderById());
            speakLearningRecord.setMarkedWordRecords(speakingTeacherOperateWordsMapperFactory.getMarkedMapper().getWordRecordsOrderById());
            speakLearningRecord.setDesertedLearningWordRecords(speakingTeacherOperateWordsMapperFactory.getDesertedLearningMapper().getWordRecordsOrderById());

            writeLearningRecord.setHaveGraspedWordRecords(writingTeacherOperateWordsMapperFactory.getHaveGraspedMapper().getWordRecordsOrderById());
            writeLearningRecord.setShallLearningWordRecords(writingTeacherOperateWordsMapperFactory.getShallLearningMapper().getWordRecordsOrderById());
            writeLearningRecord.setMarkedWordRecords(writingTeacherOperateWordsMapperFactory.getMarkedMapper().getWordRecordsOrderById());
            writeLearningRecord.setDesertedLearningWordRecords(writingTeacherOperateWordsMapperFactory.getDesertedLearningMapper().getWordRecordsOrderById());

            learningRecords.put(learningRecordManager.getLearningRecordFileName(), listenLearningRecord);
            learningRecords.put(learningRecordManager.getSpeakingRecordFileName(), speakLearningRecord);
            learningRecords.put(learningRecordManager.getWritingRecordFileName(), writeLearningRecord);

            boolean isSuccessful = learningRecordManager.backup(learningRecords);
            boolean isSuccessful1 = dictionaryManager.backup(dictionary);
            businessCaller.setSuccessful(isSuccessful && isSuccessful1);
            businessCaller.callBusiness();
        });
    }

    @Override
    public void recoverTheLearningRecord(BusinessCaller businessCaller) {
        executor.execute(() -> {
            Map<String, LearningRecord> learningRecords = learningRecordManager.recover();
            Dictionary dictionary = dictionaryManager.recover();
            if (dictionary != null && learningRecords != null) {
                dictionaryMapper.deleteAllWord();
                dictionaryMapper.addWords(dictionary.getWords());

                listeningTeacherOperateWordsMapperFactory.getHaveGraspedMapper().clearAll();
                listeningTeacherOperateWordsMapperFactory.getShallLearningMapper().clearAll();
                listeningTeacherOperateWordsMapperFactory.getMarkedMapper().clearAll();
                listeningTeacherOperateWordsMapperFactory.getDesertedLearningMapper().clearAll();
                speakingTeacherOperateWordsMapperFactory.getHaveGraspedMapper().clearAll();
                speakingTeacherOperateWordsMapperFactory.getShallLearningMapper().clearAll();
                speakingTeacherOperateWordsMapperFactory.getMarkedMapper().clearAll();
                speakingTeacherOperateWordsMapperFactory.getDesertedLearningMapper().clearAll();
                writingTeacherOperateWordsMapperFactory.getHaveGraspedMapper().clearAll();
                writingTeacherOperateWordsMapperFactory.getShallLearningMapper().clearAll();
                writingTeacherOperateWordsMapperFactory.getMarkedMapper().clearAll();
                writingTeacherOperateWordsMapperFactory.getDesertedLearningMapper().clearAll();


                listeningTeacherOperateWordsMapperFactory.getHaveGraspedMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getLearningRecordFileName())
                                .getHaveGraspedWordRecords());
                listeningTeacherOperateWordsMapperFactory.getShallLearningMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getLearningRecordFileName())
                                .getShallLearningWordRecords());
                listeningTeacherOperateWordsMapperFactory.getMarkedMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getLearningRecordFileName())
                                .getMarkedWordRecords());
                listeningTeacherOperateWordsMapperFactory.getDesertedLearningMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getLearningRecordFileName())
                                .getDesertedLearningWordRecords());

                speakingTeacherOperateWordsMapperFactory.getHaveGraspedMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getSpeakingRecordFileName())
                                .getHaveGraspedWordRecords());
                speakingTeacherOperateWordsMapperFactory.getShallLearningMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getSpeakingRecordFileName())
                                .getShallLearningWordRecords());
                speakingTeacherOperateWordsMapperFactory.getMarkedMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getSpeakingRecordFileName())
                                .getMarkedWordRecords());
                speakingTeacherOperateWordsMapperFactory.getDesertedLearningMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getSpeakingRecordFileName())
                                .getDesertedLearningWordRecords());

                writingTeacherOperateWordsMapperFactory.getHaveGraspedMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getWritingRecordFileName())
                                .getHaveGraspedWordRecords());
                writingTeacherOperateWordsMapperFactory.getShallLearningMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getWritingRecordFileName())
                                .getShallLearningWordRecords());
                writingTeacherOperateWordsMapperFactory.getMarkedMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getWritingRecordFileName())
                                .getMarkedWordRecords());
                writingTeacherOperateWordsMapperFactory.getDesertedLearningMapper()
                        .addWordRecords(learningRecords.get(learningRecordManager.getWritingRecordFileName())
                                .getDesertedLearningWordRecords());

                businessCaller.setSuccessful(true);
            } else {
                businessCaller.setSuccessful(false);
            }
            businessCaller.callBusiness();
        });
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
        myAppSetting.setTeacherType(TeacherType.getLearningMode(teacherTypeId));
    }

    @Override
    public void setLearningMode(int learningMode) {
        myAppSetting.setLearningMode(learningMode);
    }

}
