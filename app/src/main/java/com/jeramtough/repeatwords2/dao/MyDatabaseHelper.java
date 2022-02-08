package com.jeramtough.repeatwords2.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.repeatwords2.util.SqlUtil;

import java.io.IOException;

/**
 * @author 11718
 * on 2018  May 03 Thursday 14:39.
 */
@JtComponent
public class MyDatabaseHelper extends SQLiteOpenHelper implements WithLogger {
    private Context context;

    @IocAutowire
    public MyDatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, 1);
        this.context = context;
    }

    /**
     * @deprecated :必须插入一条数据才能创建巨辣鸡
     */
    @Override
    @Deprecated
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTables() {
        //create tables
        executeSqlFile(context, DatabaseConstants.SQL_FILE_1, getWritableDatabase());
        executeSqlFile(context, DatabaseConstants.SQL_FILE_2, getWritableDatabase());
        executeSqlFile(context, DatabaseConstants.SQL_FILE_3, getWritableDatabase());
        executeSqlFile(context, DatabaseConstants.SQL_FILE_4, getWritableDatabase());
    }

    @Deprecated
    public int getDictionaryWordsCount() {
        Cursor cursor = this.getReadableDatabase()
                            .rawQuery("SELECT count(*) FROM " + DatabaseConstants.TABLE_NAME_Z,
                                    null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }


    //*****************************

    private void executeSqlFile(Context context, String sqlFileName,
                                SQLiteDatabase sqLiteDatabase) {
        String[] sqls = new String[0];
        try {
            sqls = SqlUtil.processSqls(context.getAssets().open(sqlFileName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        for (String sql : sqls) {
            getLogger().debug(sql);
            sqLiteDatabase.execSQL(sql);
        }
    }
}
