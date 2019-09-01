package com.jeramtough.repeatwords2.dao.mapper;

import android.database.Cursor;

import com.jeramtough.repeatwords2.bean.word.WordWithRecordTime;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 05 Saturday 11:03.
 */
public class OperateWordsMapper extends DaoMapper {
    private String tableName;

    OperateWordsMapper(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
        this.tableName = loadOperateWordTableName();
    }

    public OperateWordsMapper(MyDatabaseHelper myDatabaseHelper, String tableName) {
        super(myDatabaseHelper);
        this.tableName = tableName;
    }

    protected String loadOperateWordTableName() {
        return null;
    }

    public List<Integer> getWordIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT word_id FROM " + tableName;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("word_id"));
            ids.add(id);
        }
        cursor.close();
        return ids;
    }

    public void removeWordRecordById(int wordId) {
        String sql = "DELETE FROM " + tableName + " WHERE word_id =" + wordId;
        this.getSqLiteDatabase().execSQL(sql);
    }

    @Deprecated
    public void insertIdOfWord(int wordId) {
        String sql = "INSERT INTO " + tableName + " VALUES(null,?,datetime('now'))";
        this.getSqLiteDatabase().execSQL(sql, new Object[]{wordId});
    }

    public void addWordRecord(WordRecord wordRecord) {
        String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?)";
        this.getSqLiteDatabase().execSQL(sql,
                new Object[]{null, wordRecord.getWordId(), wordRecord.getTime(),
                        wordRecord.getLevel()});
    }

    @Deprecated
    public List<WordRecord> getWordRecordsOrderById() {
        List<WordRecord> wordRecords = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " ORDER BY id";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            long fdId = cursor.getInt(cursor.getColumnIndex("fd_id"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));
            long wordId = cursor.getInt(cursor.getColumnIndex("word_id"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            wordRecords.add(new WordRecord(fdId, wordId, time, level));
        }
        cursor.close();
        return wordRecords;
    }

    public List<WordRecord> getWordRecordsOrderByLevel() {
        List<WordRecord> wordRecords = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " ORDER BY level";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            long fdId = cursor.getInt(cursor.getColumnIndex("fd_id"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));
            long wordId = cursor.getInt(cursor.getColumnIndex("word_id"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            wordRecords.add(new WordRecord(fdId, wordId, time, level));
        }
        cursor.close();
        return wordRecords;
    }

    public List<Integer> getWordIdsOrderByWordId(int limit) {
        ArrayList<Integer> list = new ArrayList<>();

        String sql = "SELECT word_id FROM " + tableName + " ORDER BY id LIMIT " + limit + ";";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                int wordId = cursor.getInt(cursor.getColumnIndex("wordId"));
                list.add(wordId);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<Integer> getWordIdsOrderByTime(int limit, List<Integer> noNeededIds) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT word_id FROM " + getTableName();

        if (noNeededIds.size() > 0) {
            sql = sql + " WHERE ";
            for (int id : noNeededIds) {
                sql = sql + "(word_id!=" + id + ") AND ";
            }
            sql = sql.substring(0, sql.length() - 5);
        }

        sql = sql + " ORDER BY time DESC LIMIT " + limit + ";";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int wordId = cursor.getInt(cursor.getColumnIndex("word_id"));
            ids.add(wordId);
        }
        cursor.close();
        return ids;
    }


    public void clearAll() {
        String sql = "DELETE FROM " + tableName;
        getSqLiteDatabase().execSQL(sql);
    }


    public void addWordRecords(List<WordRecord> wordRecords) {
        for (WordRecord wordRecord : wordRecords) {
            addWordRecord(wordRecord);
        }
    }


    public String getTableName() {
        return tableName;
    }

    public List<Integer> getWordIdsForRandom(int limit) {

        ArrayList<Integer> list = new ArrayList<>();

        String sql =
                "SELECT word_id FROM " + getTableName() + " ORDER BY RANDOM() LIMIT " + limit;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int wordId = cursor.getInt(cursor.getColumnIndex("word_id"));
            list.add(wordId);
        }
        cursor.close();
        return list;
    }

    @Deprecated
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
        String sql = "SELECT * FROM " + tableName + " WHERE word_id =" + wordId;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        boolean has = cursor.getCount() > 0;
        cursor.close();
        return has;
    }
}
