package com.jeramtough.repeatwords2.dao.mapper;

import android.database.Cursor;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

/**
 * @author 11718
 * on 2018  May 05 Saturday 10:07.
 */
@JtComponent
public abstract class MarkedMapper extends OperateWordsMapper
{
	@IocAutowire
	public MarkedMapper(MyDatabaseHelper myDatabaseHelper)
	{
		super(myDatabaseHelper);
	}
	
	/*@Override
	public void insertIdOfWord(int wordId)
	{
		String sql = "SELECT * FROM " + DatabaseConstants.TABLE_NAME_3 + " WHERE id=" + wordId;
		Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
		if (cursor.getCount() == 0)
		{
			super.insertIdOfWord(wordId);
		}
		cursor.close();
	}*/
}
