package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.DesertedLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.HaveGraspedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.HaveLearnedTodayMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.MarkedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ShallLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.WriteDesertedLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.WriteHaveGraspedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.WriteHaveLearnedTodayMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.WriteMarkedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.WriteShallLearningMapperWord;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:57.
 */
@JtComponent
public class WritingTeacherOperateWordsMapperFactory implements OperateWordsMapperFactory {
    private WriteHaveGraspedMapperWord writeHaveGraspedMapper;
    private WriteDesertedLearningMapperWord writeDesertedLearningMapper;
    private WriteMarkedMapperWord writeMarkedMapper;
    private WriteShallLearningMapperWord writeShallLearningMapper;
    private WriteHaveLearnedTodayMapperWord writeHaveLearnedTodayMapper;

    @IocAutowire
    public WritingTeacherOperateWordsMapperFactory(
            WriteHaveGraspedMapperWord writeHaveGraspedMapper, WriteDesertedLearningMapperWord writeDesertedLearningMapper, WriteMarkedMapperWord writeMarkedMapper, WriteShallLearningMapperWord writeShallLearningMapper, WriteHaveLearnedTodayMapperWord writeHaveLearnedTodayMapper) {
        this.writeHaveGraspedMapper = writeHaveGraspedMapper;
        this.writeDesertedLearningMapper = writeDesertedLearningMapper;
        this.writeMarkedMapper = writeMarkedMapper;
        this.writeShallLearningMapper = writeShallLearningMapper;
        this.writeHaveLearnedTodayMapper = writeHaveLearnedTodayMapper;
    }


    @Override
    public HaveGraspedMapperWord getHaveGraspedMapper() {
        return writeHaveGraspedMapper;
    }

    @Override
    public ShallLearningMapperWord getShallLearningMapper() {
        return writeShallLearningMapper;
    }

    @Override
    public MarkedMapperWord getMarkedMapper() {
        return writeMarkedMapper;
    }

    @Override
    public DesertedLearningMapperWord getDesertedLearningMapper() {
        return writeDesertedLearningMapper;
    }

    @Override
    public HaveLearnedTodayMapperWord getHaveLearnedTodayMapper() {
        return writeHaveLearnedTodayMapper;
    }
}
