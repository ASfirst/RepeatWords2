package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.*;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:57.
 */
@JtComponent
public class SpeakingTeacherOperateWordsMapperFactory implements OperateWordsMapperFactory
{
    private SpeakMarkedMapperWord speakMarkedMapper;
    private SpeakDesertedLearningMapperWord speakDesertedLearningMapper;
    private SpeakHaveGraspedMapperWord speakHaveGraspedMapper;
    private SpeakShallLearningMapperWord speakShallLearningMapper;
    private SpeakHaveLearnedTodayMapperWord speakHaveLearnedTodayMapper;
    
    @IocAutowire
    public SpeakingTeacherOperateWordsMapperFactory(SpeakMarkedMapperWord speakMarkedMapper,
                                                    SpeakDesertedLearningMapperWord speakDesertedLearningMapper,
                                                    SpeakHaveGraspedMapperWord speakHaveGraspedMapper,
                                                    SpeakShallLearningMapperWord speakShallLearningMapper, SpeakHaveLearnedTodayMapperWord speakHaveLearnedTodayMapper)
    {
        this.speakMarkedMapper = speakMarkedMapper;
        this.speakDesertedLearningMapper = speakDesertedLearningMapper;
        this.speakHaveGraspedMapper = speakHaveGraspedMapper;
        this.speakShallLearningMapper = speakShallLearningMapper;
        this.speakHaveLearnedTodayMapper = speakHaveLearnedTodayMapper;
    }
    
    @Override
    public HaveGraspedMapperWord getHaveGraspedMapper()
    {
        return speakHaveGraspedMapper;
    }
    
    @Override
    public ShallLearningMapperWord getShallLearningMapper()
    {
        return speakShallLearningMapper;
    }
    
    @Override
    public MarkedMapperWord getMarkedMapper()
    {
        return speakMarkedMapper;
    }
    
    @Override
    public DesertedLearningMapperWord getDesertedLearningMapper()
    {
        return speakDesertedLearningMapper;
    }

    @Override
    public HaveLearnedTodayMapperWord getHaveLearnedTodayMapper() {
        return speakHaveLearnedTodayMapper;
    }
}
