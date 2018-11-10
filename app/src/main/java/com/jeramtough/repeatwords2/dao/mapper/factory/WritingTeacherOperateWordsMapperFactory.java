package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.DesertedLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.HaveGraspedMapper;
import com.jeramtough.repeatwords2.dao.mapper.HaveLearnedTodayMapper;
import com.jeramtough.repeatwords2.dao.mapper.MarkedMapper;
import com.jeramtough.repeatwords2.dao.mapper.ShallLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.WriteDesertedLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.WriteHaveGraspedMapper;
import com.jeramtough.repeatwords2.dao.mapper.WriteHaveLearnedTodayMapper;
import com.jeramtough.repeatwords2.dao.mapper.WriteMarkedMapper;
import com.jeramtough.repeatwords2.dao.mapper.WriteShallLearningMapper;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:57.
 */
@JtComponent
public class WritingTeacherOperateWordsMapperFactory implements OperateWordsMapperFactory {
    private WriteHaveGraspedMapper writeHaveGraspedMapper;
    private WriteDesertedLearningMapper writeDesertedLearningMapper;
    private WriteMarkedMapper writeMarkedMapper;
    private WriteShallLearningMapper writeShallLearningMapper;
    private WriteHaveLearnedTodayMapper writeHaveLearnedTodayMapper;

    @IocAutowire
    public WritingTeacherOperateWordsMapperFactory(WriteHaveGraspedMapper writeHaveGraspedMapper, WriteDesertedLearningMapper writeDesertedLearningMapper, WriteMarkedMapper writeMarkedMapper, WriteShallLearningMapper writeShallLearningMapper, WriteHaveLearnedTodayMapper writeHaveLearnedTodayMapper) {
        this.writeHaveGraspedMapper = writeHaveGraspedMapper;
        this.writeDesertedLearningMapper = writeDesertedLearningMapper;
        this.writeMarkedMapper = writeMarkedMapper;
        this.writeShallLearningMapper = writeShallLearningMapper;
        this.writeHaveLearnedTodayMapper = writeHaveLearnedTodayMapper;
    }


    @Override
    public HaveGraspedMapper getHaveGraspedMapper() {
        return writeHaveGraspedMapper;
    }

    @Override
    public ShallLearningMapper getShallLearningMapper() {
        return writeShallLearningMapper;
    }

    @Override
    public MarkedMapper getMarkedMapper() {
        return writeMarkedMapper;
    }

    @Override
    public DesertedLearningMapper getDesertedLearningMapper() {
        return writeDesertedLearningMapper;
    }

    @Override
    public HaveLearnedTodayMapper getHaveLearnedTodayMapper() {
        return writeHaveLearnedTodayMapper;
    }
}
