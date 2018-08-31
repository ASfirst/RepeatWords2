package com.jeramtough.repeatwords2.bean.word;

import java.io.Serializable;

public class WordWithRecordTime extends Word implements Serializable {
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
