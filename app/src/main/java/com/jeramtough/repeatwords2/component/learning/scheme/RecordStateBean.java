package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.repeatwords2.dao.entity.WordRecord;

/**
 * Created on 2019-09-05 23:38
 * by @author JeramTough
 */
public class RecordStateBean {
    private Integer sum;
    private Integer index;
    private WordRecord wordRecord;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public WordRecord getWordRecord() {
        return wordRecord;
    }

    public void setWordRecord(WordRecord wordRecord) {
        this.wordRecord = wordRecord;
    }
}
