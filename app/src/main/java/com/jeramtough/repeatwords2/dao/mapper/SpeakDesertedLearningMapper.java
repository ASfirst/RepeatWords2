package com.jeramtough.repeatwords2.dao.mapper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * @author 11718
 * on 2018  May 05 Saturday 10:08.
 */
@JtComponent
public class SpeakDesertedLearningMapper extends DesertedLearningMapper
{
	@IocAutowire
	public SpeakDesertedLearningMapper(MyDatabaseHelper myDatabaseHelper)
	{
		super(myDatabaseHelper);
	}
	
	@Override
	protected String loadOperateWordTableName()
	{
		return DatabaseConstants.TABLE_NAME_9;
	}
}
