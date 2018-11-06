package com.jeramtough.repeatwords2.dao.mapper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * @author 11718
 * on 2018  May 03 Thursday 23:38.
 */
@JtComponent
public class SpeakShallLearningMapper extends ShallLearningMapper
{
    @IocAutowire
    public SpeakShallLearningMapper(MyDatabaseHelper myDatabaseHelper)
    {
        super(myDatabaseHelper);
    }
    
    @Override
    protected String loadOperateWordTableName()
    {
        return DatabaseConstants.TABLE_NAME_B_3;
    }
    
    
}
