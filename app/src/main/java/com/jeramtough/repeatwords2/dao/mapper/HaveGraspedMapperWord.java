package com.jeramtough.repeatwords2.dao.mapper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * @author 11718
 * on 2018  May 05 Saturday 10:05.
 */
@JtComponent
public abstract class HaveGraspedMapperWord extends OperateWordRecordMapper
{
	@IocAutowire
	public HaveGraspedMapperWord(MyDatabaseHelper myDatabaseHelper)
	{
		super(myDatabaseHelper);
	}
	
}
