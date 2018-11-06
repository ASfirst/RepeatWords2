package com.jeramtough.repeatwords2.dao.mapper;

import android.database.Cursor;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.dao.DatabaseConstants;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 03 Thursday 23:33.
 */
@JtComponent
public class DictionaryMapper extends DaoMapper {

    @IocAutowire
    public DictionaryMapper(MyDatabaseHelper myDatabaseHelper) {
        super(myDatabaseHelper);
    }

    public void addNewWord(Word word) {
        addNewWord(word.getCh(), word.getEn(), word.getPhonetic());
    }

    public void addWord(Word word) {
        addWord(word.getId(), word.getEn(), word.getCh(), word.getPhonetic());
    }

    public void addWord(int wordId, String en, String ch, String phonetic) {
        deleteWordById(wordId);
        String sql = "INSERT INTO " + DatabaseConstants.TABLE_NAME_Z + " VALUES(?,?,?,?);";
        getSqLiteDatabase().execSQL(sql, new Object[]{wordId, en, ch, phonetic});
    }

    public void addNewWord(String ch, String en, String phonetic) {
        String sql = "INSERT INTO " + DatabaseConstants.TABLE_NAME_Z + " VALUES(null,?,?,?);";
        getSqLiteDatabase().execSQL(sql, new Object[]{en, ch, phonetic});
    }

    public Word getWord(int wordId) {
        String sql = "SELECT * FROM " + DatabaseConstants.TABLE_NAME_Z + " WHERE id=" + wordId;
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);
        List<Word> words = processWordsForCursor(cursor);
        cursor.close();
        return words.get(0);
    }

    public Word getWordByEn(String en) {
        String sql = "SELECT * FROM " + DatabaseConstants.TABLE_NAME_Z + " WHERE en=?";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, new String[]{en});
        List<Word> words = processWordsForCursor(cursor);
        cursor.close();
        return words.get(0);
    }

    public Word getWordByEn(String en, int exceptId) {
        String sql = "SELECT * FROM " + DatabaseConstants.TABLE_NAME_Z + " WHERE en=? AND id != ?";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, new String[]{en, exceptId + ""});
        List<Word> words = processWordsForCursor(cursor);
        cursor.close();
        if (words.size() > 0) {
            return words.get(0);
        } else {
            return null;
        }
    }


    public List<Word> getAllWordsOrderByEn() {
        List<Word> words = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseConstants.TABLE_NAME_Z + " ORDER BY en";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);

        words = processWordsForCursor(cursor);

        cursor.close();
        return words;
    }

    public List<Word> getAllWordsOrderById() {
        List<Word> words;
        String sql = "SELECT * FROM " + DatabaseConstants.TABLE_NAME_Z + " ORDER BY id";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, null);

        words = processWordsForCursor(cursor);

        cursor.close();
        return words;
    }

    public int getWordCountByEn(String en) {
        String sql = "SELECT COUNT(*) FROM " + DatabaseConstants.TABLE_NAME_Z + " WHERE en = ?";
        Cursor cursor = getSqLiteDatabase().rawQuery(sql, new String[]{en});

        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public void deleteWordById(int wordId) {
        String sql = "DELETE FROM " + DatabaseConstants.TABLE_NAME_Z + " WHERE id=" + wordId;
        getSqLiteDatabase().execSQL(sql);
    }

    public void deleteWordByEn(String en) {
        String sql = "DELETE FROM " + DatabaseConstants.TABLE_NAME_Z + " WHERE en='" + en + "'";
        getSqLiteDatabase().execSQL(sql);
    }

    public void deleteAllWord() {
        String sql = "DELETE FROM " + DatabaseConstants.TABLE_NAME_Z;
        getSqLiteDatabase().execSQL(sql);
    }

    public void addWords(List<Word> words) {
        for (Word word : words) {
            String sql = "INSERT INTO " + DatabaseConstants.TABLE_NAME_Z + " VALUES(?,?,?,?);";
            getSqLiteDatabase()
                    .execSQL(sql, new Object[]{word.getId(), word.getEn(), word.getCh(), word.getPhonetic()});
        }
    }

    //*******************************
    private List<Word> processWordsForCursor(Cursor cursor) {
        List<Word> words = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String ch = cursor.getString(cursor.getColumnIndex("ch"));
            String en = cursor.getString(cursor.getColumnIndex("en"));
            String phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
            Word word = new Word();
            word.setId(id);
            word.setCh(ch);
            word.setEn(en);
            word.setPhonetic(phonetic);
            words.add(word);
        }
        return words;
    }
}
