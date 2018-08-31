package com.jeramtough.repeatwords2.dao.mapper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * @author 11718
 * on 2018  May 05 Saturday 10:08.
 */
@JtComponent
public abstract class DesertedLearningMapper extends OperateWordsMapper
{
	@IocAutowire
	public DesertedLearningMapper(MyDatabaseHelper myDatabaseHelper)
	{
		super(myDatabaseHelper);
	}
	
}
