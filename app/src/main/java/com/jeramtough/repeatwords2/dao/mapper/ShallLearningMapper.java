package com.jeramtough.repeatwords2.dao.mapper;

import android.database.Cursor;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 03 Thursday 23:38.
 */
@JtComponent
public abstract class ShallLearningMapper extends OperateWordsMapper {
    @IocAutowire
    public ShallLearningMapper(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
    }




}