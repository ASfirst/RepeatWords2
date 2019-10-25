package com.jeramtough.repeatwords2.dao.dto.record;

import java.io.Serializable;

/**
 * Created on 2019-09-04 14:20
 * by @author JeramTough
 */
public class WordRecordDto implements Serializable {
    private Long fdId;
    private Long wordId;
    private String word;
    private String tag;
    private String phonetic;
    private String time;
    private Integer level;
    private String chExplain;
    private String miniChExplain;

    public Long getFdId() {
        return fdId;
    }

    public void setFdId(Long fdId) {
        this.fdId = fdId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getChExplain() {
        return chExplain;
    }

    public void setChExplain(String chExplain) {
        this.chExplain = chExplain;
    }

    public String getMiniChExplain() {
        return miniChExplain;
    }

    public void setMiniChExplain(String miniChExplain) {
        this.miniChExplain = miniChExplain;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
}
