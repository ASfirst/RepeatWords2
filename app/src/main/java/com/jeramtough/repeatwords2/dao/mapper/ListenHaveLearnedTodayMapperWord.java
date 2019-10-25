package com.jeramtough.repeatwords2.dao.mapper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * Created on 2018-11-05 21:52
 * by @author JeramTough
 */
@JtComponent
public class ListenHaveLearnedTodayMapperWord extends HaveLearnedTodayMapperWord {

    @IocAutowire
    public ListenHaveLearnedTodayMapperWord(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
    }

    @Override
    protected String loadOperateWordTableName() {
        return DatabaseConstants.TABLE_NAME_A_5;
    }

}
