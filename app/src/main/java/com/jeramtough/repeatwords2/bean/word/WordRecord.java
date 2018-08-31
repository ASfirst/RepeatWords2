package com.jeramtough.repeatwords2.bean.word;

public class WordRecord {
    private int wordId;
    private String time;

    public WordRecord() {
    }

    public WordRecord(int wordId, String time) {
        this.wordId = wordId;
        this.time = time;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
