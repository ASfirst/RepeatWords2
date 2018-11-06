package com.jeramtough.repeatwords2.dao.mapper;

import android.database.Cursor;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018-11-04 22:56
 * by @author JeramTough
 */
public abstract class HaveLearnedTodayMapper extends DaoMapper {

    private String tableName;

    public HaveLearnedTodayMapper(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
        tableName = loadHavedLearnedWordTableName();
    }

    protected abstract String loadHavedLearnedWordTableName();

    public List<Integer> getHaveLearnedWordIdsToday() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM " + tableName;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            ids.add(id);
        }
        cursor.close();
        return ids;
    }

    public void clearAll() {
        String sql = "DELETE FROM " + tableName;
        getSqLiteDatabase().execSQL(sql);
    }

    public void addWordIdInToday(int wordId) {
        String sql = "INSERT INTO " + tableName + " VALUES(?)";
        this.getSqLiteDatabase().execSQL(sql, new Object[]{wordId});
    }

}
