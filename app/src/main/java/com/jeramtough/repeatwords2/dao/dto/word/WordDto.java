package com.jeramtough.repeatwords2.dao.dto.word;

import java.io.Serializable;

/**
 * Created on 2019-09-02 23:05
 * by @author JeramTough
 */
public class WordDto implements Serializable {

    private Long fdId;
    private String word;
    private String phonetic;
    private String enExplain;
    private String chExplain;
    private String position;
    private Integer collins;
    private Boolean oxford;
    private String tag;
    private Integer bnc;
    private Integer frq;
    private String exchange;
    private Integer length;
    private Boolean isLearnedAtLeastTwiceToday;

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

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getEnExplain() {
        return enExplain;
    }

    public void setEnExplain(String enExplain) {
        this.enExplain = enExplain;
    }

    public String getChExplain() {
        return chExplain;
    }

    public void setChExplain(String chExplain) {
        this.chExplain = chExplain;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getCollins() {
        return collins;
    }

    public void setCollins(Integer collins) {
        this.collins = collins;
    }

    public Boolean getOxford() {
        return oxford;
    }

    public void setOxford(Boolean oxford) {
        this.oxford = oxford;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getBnc() {
        return bnc;
    }

    public void setBnc(Integer bnc) {
        this.bnc = bnc;
    }

    public Integer getFrq() {
        return frq;
    }

    public void setFrq(Integer frq) {
        this.frq = frq;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean isLearnedAtLeastTwiceToday() {
        return isLearnedAtLeastTwiceToday;
    }

    public void setLearnedAtLeastTwiceToday(Boolean learnedAtLeastTwiceToday) {
        isLearnedAtLeastTwiceToday = learnedAtLeastTwiceToday;
    }
}
