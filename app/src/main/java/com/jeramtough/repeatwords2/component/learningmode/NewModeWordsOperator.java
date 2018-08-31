package com.jeramtough.repeatwords2.component.learningmode;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:22.
 */
@JtComponent
public class NewModeWordsOperator implements WordsOperator
{
	private OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider;
	
	@IocAutowire
	public NewModeWordsOperator(
			OperateWordsMapperFactoryProvider operateWordMapperFactoryProvider)
	{
		this.operateWordMapperFactoryProvider = operateWordMapperFactoryProvider;
	}
	
	
	@Override
	public void removeWordFromList(int wordId)
	{
		operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
				.getShallLearningMapper().removeWordRecordById(wordId);
		operateWordMapperFactoryProvider.getOperateWordsMapperFactory().getHaveGraspedMapper()
				.insertIdOfWord(wordId);
	}
	
	@Override
	public List<Integer> getWordIdsOfNeeding(int size)
	{
		return operateWordMapperFactoryProvider.getOperateWordsMapperFactory()
				.getShallLearningMapper().getIdsOrderByTime(size);
	}
}
