package com.jeramtough.repeatwords2.bean.record;

import com.jeramtough.repeatwords2.bean.word.WordRecord;

import java.io.Serializable;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 06 Sunday 17:46.
 */
public class LearningRecord implements Serializable {
    private List<WordRecord> haveGraspedWordRecords;
    private List<WordRecord> shallLearningWordRecords;
    private List<WordRecord> markedWordRecords;
    private List<WordRecord> desertedLearningWordRecords;

    public List<WordRecord> getHaveGraspedWordRecords() {
        return haveGraspedWordRecords;
    }

    public void setHaveGraspedWordRecords(List<WordRecord> haveGraspedWordRecords) {
        this.haveGraspedWordRecords = haveGraspedWordRecords;
    }

    public List<WordRecord> getShallLearningWordRecords() {
        return shallLearningWordRecords;
    }

    public void setShallLearningWordRecords(List<WordRecord> shallLearningWordRecords) {
        this.shallLearningWordRecords = shallLearningWordRecords;
    }

    public List<WordRecord> getMarkedWordRecords() {
        return markedWordRecords;
    }

    public void setMarkedWordRecords(List<WordRecord> markedWordRecords) {
        this.markedWordRecords = markedWordRecords;
    }

    public List<WordRecord> getDesertedLearningWordRecords() {
        return desertedLearningWordRecords;
    }

    public void setDesertedLearningWordRecords(List<WordRecord> desertedLearningWordRecords) {
        this.desertedLearningWordRecords = desertedLearningWordRecords;
    }
}
