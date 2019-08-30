package com.jeramtough.repeatwords2.dao.entity;

import java.io.Serializable;

public class WordRecord implements Serializable {

    private Integer fdId;
    private Integer wordId;
    private String time;
    private Integer level;

    public WordRecord() {
    }


    public WordRecord(Integer fdId, Integer wordId, String time, Integer level) {
        this.fdId = fdId;
        this.wordId = wordId;
        this.time = time;
        this.level = level;
    }

    public Integer getFdId() {
        return fdId;
    }

    public void setFdId(Integer fdId) {
        this.fdId = fdId;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "WordRecord33{" +
                "fdId=" + fdId +
                ", wordId=" + wordId +
                ", time='" + time + '\'' +
                ", level=" + level +
                '}';
    }
}
