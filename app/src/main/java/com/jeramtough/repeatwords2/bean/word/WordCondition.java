package com.jeramtough.repeatwords2.bean.word;

/**
 * Created on 2019-08-30 01:00
 * by @author JeramTough
 */
public enum WordCondition {

    /**
     * The word will be learning
     */
    SHALL_LEARNING(0),

    /**
     * The word has been learned in today
     */
    LEARNED_TODAY(1),

    /**
     * The word has been grasped
     */
    GRASPED(2),


    /**
     * The word has been marked
     */
    MARKED(3),

    /**
     * The word has been deserted learning
     */
    DESERTED(4);


    private int code;

    WordCondition(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static WordCondition getWordConditionByCode(int code) {
        for (WordCondition wordCondition : WordCondition.values()) {
            if (code == wordCondition.code) {
                return wordCondition;
            }
        }
        return null;
    }
}
