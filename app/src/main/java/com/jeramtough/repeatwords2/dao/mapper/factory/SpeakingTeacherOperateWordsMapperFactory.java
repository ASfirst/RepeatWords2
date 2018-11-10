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
    private SpeakMarkedMapper speakMarkedMapper;
    private SpeakDesertedLearningMapper speakDesertedLearningMapper;
    private SpeakHaveGraspedMapper speakHaveGraspedMapper;
    private SpeakShallLearningMapper speakShallLearningMapper;
    private SpeakHaveLearnedTodayMapper speakHaveLearnedTodayMapper;
    
    @IocAutowire
    public SpeakingTeacherOperateWordsMapperFactory(SpeakMarkedMapper speakMarkedMapper,
                                                    SpeakDesertedLearningMapper speakDesertedLearningMapper,
                                                    SpeakHaveGraspedMapper speakHaveGraspedMapper,
                                                    SpeakShallLearningMapper speakShallLearningMapper, SpeakHaveLearnedTodayMapper speakHaveLearnedTodayMapper)
    {
        this.speakMarkedMapper = speakMarkedMapper;
        this.speakDesertedLearningMapper = speakDesertedLearningMapper;
        this.speakHaveGraspedMapper = speakHaveGraspedMapper;
        this.speakShallLearningMapper = speakShallLearningMapper;
        this.speakHaveLearnedTodayMapper = speakHaveLearnedTodayMapper;
    }
    
    @Override
    public HaveGraspedMapper getHaveGraspedMapper()
    {
        return speakHaveGraspedMapper;
    }
    
    @Override
    public ShallLearningMapper getShallLearningMapper()
    {
        return speakShallLearningMapper;
    }
    
    @Override
    public MarkedMapper getMarkedMapper()
    {
        return speakMarkedMapper;
    }
    
    @Override
    public DesertedLearningMapper getDesertedLearningMapper()
    {
        return speakDesertedLearningMapper;
    }

    @Override
    public HaveLearnedTodayMapper getHaveLearnedTodayMapper() {
        return speakHaveLearnedTodayMapper;
    }
}
