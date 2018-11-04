package com.jeramtough.repeatwords2.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog.with.WithJtLogger;
import com.jeramtough.repeatwords2.util.SqlUtil;

import java.io.IOException;

/**
 * @author 11718
 * on 2018  May 03 Thursday 14:39.
 */
@JtComponent
public class MyDatabaseHelper extends SQLiteOpenHelper implements WithJtLogger {
    private Context context;

    @IocAutowire
    public MyDatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        executeSqlFile(context, DatabaseConstants.SQL_FILE_1, sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getDictionaryWordsCount() {
        Cursor cursor = this.getReadableDatabase()
                .rawQuery("SELECT count(*) FROM " + DatabaseConstants.TABLE_NAME_1, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void initTables() {
        executeSqlFile(context, DatabaseConstants.SQL_FILE_2, getWritableDatabase());
    }

    //*****************************
    private void executeSqlFile(Context context, String sqlFileName,
                                SQLiteDatabase sqLiteDatabase) {
        String[] sqls = new String[0];
        try {
            sqls = SqlUtil.processSqls(context.getAssets().open(sqlFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String sql : sqls) {
            sqLiteDatabase.execSQL(sql);
        }
    }
}
