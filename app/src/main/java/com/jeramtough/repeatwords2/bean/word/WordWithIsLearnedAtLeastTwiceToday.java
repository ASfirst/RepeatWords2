package com.jeramtough.repeatwords2.bean.word;

import java.io.Serializable;

/**
 * Created on 2018-12-09 18:53
 * by @author JeramTough
 */
public class WordWithIsLearnedAtLeastTwiceToday extends Word implements Serializable {

    private boolean isLearnedAtLeastTwiceToday;

    public boolean isLearnedAtLeastTwiceToday() {
        return isLearnedAtLeastTwiceToday;
    }

    public void setLearnedAtLeastTwiceToday(boolean learnedAtLeastTwiceToday) {
        isLearnedAtLeastTwiceToday = learnedAtLeastTwiceToday;
    }

}
