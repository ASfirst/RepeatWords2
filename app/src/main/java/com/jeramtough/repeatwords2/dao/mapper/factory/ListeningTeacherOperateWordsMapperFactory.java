package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.DesertedLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.HaveGraspedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.HaveLearnedTodayMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ListenDesertedLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ListenHaveGraspedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ListenHaveLearnedTodayMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ListenMarkedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ListenShallLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.MarkedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ShallLearningMapperWord;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:57.
 */
@JtComponent
public class ListeningTeacherOperateWordsMapperFactory implements OperateWordsMapperFactory
{
	private ListenShallLearningMapperWord listenShallLearningMapper;
	private ListenHaveGraspedMapperWord listenHaveGraspedMapper;
	private ListenMarkedMapperWord listenMarkedMapper;
	private ListenDesertedLearningMapperWord listenDesertedLearningMapper;
	private ListenHaveLearnedTodayMapperWord listenHaveLearnedTodayMapper;
	
	@IocAutowire
	public ListeningTeacherOperateWordsMapperFactory(
            ListenShallLearningMapperWord listenShallLearningMapper,
            ListenHaveGraspedMapperWord listenHaveGraspedMapper,
            ListenMarkedMapperWord listenMarkedMapper,
            ListenDesertedLearningMapperWord listenDesertedLearningMapper, ListenHaveLearnedTodayMapperWord listenHaveLearnedTodayMapper)
	{
		this.listenShallLearningMapper = listenShallLearningMapper;
		this.listenHaveGraspedMapper = listenHaveGraspedMapper;
		this.listenMarkedMapper = listenMarkedMapper;
		this.listenDesertedLearningMapper = listenDesertedLearningMapper;
		this.listenHaveLearnedTodayMapper = listenHaveLearnedTodayMapper;
	}
	
	@Override
	public HaveGraspedMapperWord getHaveGraspedMapper()
	{
		return listenHaveGraspedMapper;
	}
	
	@Override
	public ShallLearningMapperWord getShallLearningMapper()
	{
		return listenShallLearningMapper;
	}
	
	@Override
	public MarkedMapperWord getMarkedMapper()
	{
		return listenMarkedMapper;
	}
	
	@Override
	public DesertedLearningMapperWord getDesertedLearningMapper()
	{
		return listenDesertedLearningMapper;
	}

    @Override
    public HaveLearnedTodayMapperWord getHaveLearnedTodayMapper() {
        return listenHaveLearnedTodayMapper;
    }
}
