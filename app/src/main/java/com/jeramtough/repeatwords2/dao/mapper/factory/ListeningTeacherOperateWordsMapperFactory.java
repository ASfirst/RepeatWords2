package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.DesertedLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.HaveGraspedMapper;
import com.jeramtough.repeatwords2.dao.mapper.HaveLearnedTodayMapper;
import com.jeramtough.repeatwords2.dao.mapper.ListenDesertedLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.ListenHaveGraspedMapper;
import com.jeramtough.repeatwords2.dao.mapper.ListenHaveLearnedTodayMapper;
import com.jeramtough.repeatwords2.dao.mapper.ListenMarkedMapper;
import com.jeramtough.repeatwords2.dao.mapper.ListenShallLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.MarkedMapper;
import com.jeramtough.repeatwords2.dao.mapper.ShallLearningMapper;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:57.
 */
@JtComponent
public class ListeningTeacherOperateWordsMapperFactory implements OperateWordsMapperFactory
{
	private ListenShallLearningMapper listenShallLearningMapper;
	private ListenHaveGraspedMapper listenHaveGraspedMapper;
	private ListenMarkedMapper listenMarkedMapper;
	private ListenDesertedLearningMapper listenDesertedLearningMapper;
	private ListenHaveLearnedTodayMapper listenHaveLearnedTodayMapper;
	
	@IocAutowire
	public ListeningTeacherOperateWordsMapperFactory(
			ListenShallLearningMapper listenShallLearningMapper,
			ListenHaveGraspedMapper listenHaveGraspedMapper,
			ListenMarkedMapper listenMarkedMapper,
			ListenDesertedLearningMapper listenDesertedLearningMapper, ListenHaveLearnedTodayMapper listenHaveLearnedTodayMapper)
	{
		this.listenShallLearningMapper = listenShallLearningMapper;
		this.listenHaveGraspedMapper = listenHaveGraspedMapper;
		this.listenMarkedMapper = listenMarkedMapper;
		this.listenDesertedLearningMapper = listenDesertedLearningMapper;
		this.listenHaveLearnedTodayMapper = listenHaveLearnedTodayMapper;
	}
	
	@Override
	public HaveGraspedMapper getHaveGraspedMapper()
	{
		return listenHaveGraspedMapper;
	}
	
	@Override
	public ShallLearningMapper getShallLearningMapper()
	{
		return listenShallLearningMapper;
	}
	
	@Override
	public MarkedMapper getMarkedMapper()
	{
		return listenMarkedMapper;
	}
	
	@Override
	public DesertedLearningMapper getDesertedLearningMapper()
	{
		return listenDesertedLearningMapper;
	}

    @Override
    public HaveLearnedTodayMapper getHaveLearnedTodayMapper() {
        return listenHaveLearnedTodayMapper;
    }
}
