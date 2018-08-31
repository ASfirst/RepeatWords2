package com.jeramtough.repeatwords2.dao.mapper;

import android.database.sqlite.SQLiteDatabase;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * @author 11718
 * on 2018  May 03 Thursday 23:39.
 */
public class DaoMapper
{
	private MyDatabaseHelper myDatabaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	
	public DaoMapper(MyDatabaseHelper myDatabaseHelper)
	{
		this.myDatabaseHelper = myDatabaseHelper;
		sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
	}
	
	
	protected SQLiteDatabase getSqLiteDatabase()
	{
		return sqLiteDatabase;
	}
}
