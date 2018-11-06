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
public class SpeakHaveLearnedTodayMapper extends HaveLearnedTodayMapper {

    @IocAutowire
    public SpeakHaveLearnedTodayMapper(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
    }

    @Override
    protected String loadHavedLearnedWordTableName() {
        return DatabaseConstants.TABLE_NAME_B_5;
    }
}
