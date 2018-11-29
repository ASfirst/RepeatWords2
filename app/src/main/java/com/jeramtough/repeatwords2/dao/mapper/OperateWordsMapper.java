package com.jeramtough.repeatwords2.dao.mapper;

import android.database.Cursor;

import com.jeramtough.repeatwords2.bean.word.WordRecord;
import com.jeramtough.repeatwords2.bean.word.WordWithRecordTime;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 05 Saturday 11:03.
 */
public abstract class OperateWordsMapper extends DaoMapper {
    private String tableName;

    public OperateWordsMapper(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
        this.tableName = loadOperateWordTableName();
    }

    protected abstract String loadOperateWordTableName();

    public void removeWordRecordById(int wordId) {
        String sql = "DELETE FROM " + tableName + " WHERE id =" + wordId;
        this.getSqLiteDatabase().execSQL(sql);
    }

    @Deprecated
    public void insertIdOfWord(int wordId) {
        String sql = "INSERT INTO " + tableName + " VALUES(?,datetime('now'))";
        this.getSqLiteDatabase().execSQL(sql, new Object[]{wordId});
    }

    public void addWordRecord(WordRecord wordRecord) {
        String sql = "INSERT INTO " + tableName + " VALUES(?,?)";
        this.getSqLiteDatabase().execSQL(sql,
                new Object[]{wordRecord.getWordId(), wordRecord.getTime()});
    }

    public List<Integer> getIdsOrderById() {
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

    public List<WordRecord> getWordRecordsOrderById() {
        List<WordRecord> wordRecords = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " ORDER BY id";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            wordRecords.add(new WordRecord(id, time));
        }
        cursor.close();
        return wordRecords;
    }

    public List<Integer> getIdsOrderById(int limit) {
        ArrayList<Integer> list = new ArrayList<>();

        String sql = "SELECT id FROM " + tableName + " ORDER BY id LIMIT " + limit + ";";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                list.add(id);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<Integer> getIdsOrderById(int limit, List<Integer> noNeededIds) {

        ArrayList<Integer> list = new ArrayList<>();
        String sql = "SELECT id FROM " + getTableName();

        if (noNeededIds.size() > 0) {
            sql = sql + " WHERE ";
            for (int id : noNeededIds) {
                sql = sql + "(id!=" + id + ") AND ";
            }
            sql = sql.substring(0, sql.length() - 5);
        }

        sql = sql + " ORDER BY id LIMIT " + limit + ";";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                list.add(id);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<Integer> getIdsOrderByTime(int limit, List<Integer> noNeededIds) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM " + getTableName();

        if (noNeededIds.size() > 0) {
            sql = sql + " WHERE ";
            for (int id : noNeededIds) {
                sql = sql + "(id!=" + id + ") AND ";
            }
            sql = sql.substring(0, sql.length() - 5);
        }

        sql = sql + " ORDER BY time DESC LIMIT " + limit + ";";
//        L.debug(sql);
//        String sql = "SELECT id FROM " + tableName + " ORDER BY time DESC LIMIT " + limit;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            ids.add(id);
        }
        cursor.close();
        return ids;
    }

    public List<Integer> getIdsOrderByTime(int limit) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM " + tableName + " ORDER BY time DESC LIMIT " + limit;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            ids.add(id);
        }
        cursor.close();
        return ids;
    }

    public List<Integer> getIdsOrderByTime() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM " + tableName + " ORDER BY time DESC";
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

    public void addIds(List<Integer> wordIds) {
        for (Integer id : wordIds) {
            insertIdOfWord(id);
        }
    }

    public void addWordRecords(List<WordRecord> wordRecords) {
        for (WordRecord wordRecord : wordRecords) {
            addWordRecord(wordRecord);
        }
    }

    public int getDictionaryWordsCount() {
        Cursor cursor = getSqLiteDatabase()
                .rawQuery("SELECT count(*) FROM " + tableName, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Integer> getIdsForRandom(int limit) {

        ArrayList<Integer> list = new ArrayList<>();

        String sql = "SELECT id FROM " + getTableName() + " ORDER BY RANDOM() LIMIT " + limit;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            list.add(id);
        }
        cursor.close();
        return list;
    }

    public List<WordWithRecordTime> getWordWithRecordTimeListOrderByTime() {

        List<WordWithRecordTime> wordWithRecordTimeList = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName + " LEFT JOIN " + DatabaseConstants.TABLE_NAME_Z + " ON " + DatabaseConstants.TABLE_NAME_Z + ".id = " + tableName + ".id ORDER BY time DESC";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String ch = cursor.getString(cursor.getColumnIndex("ch"));
            String en = cursor.getString(cursor.getColumnIndex("en"));
            String phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
            String time = cursor.getString(cursor.getColumnIndex("time"));

            WordWithRecordTime wordWithRecordTime = new WordWithRecordTime();
            wordWithRecordTime.setTime(time);
            wordWithRecordTime.setPhonetic(phonetic);
            wordWithRecordTime.setId(id);
            wordWithRecordTime.setCh(ch);
            wordWithRecordTime.setEn(en);

            wordWithRecordTimeList.add(wordWithRecordTime);
        }
        cursor.close();
        return wordWithRecordTimeList;
    }

    public boolean hasWordId(int wordId) {
        String sql = "SELECT * FROM " + tableName + " WHERE id =" + wordId;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        boolean has = cursor.getCount() > 0 ? true : false;
        cursor.close();
        return has;
    }
}
