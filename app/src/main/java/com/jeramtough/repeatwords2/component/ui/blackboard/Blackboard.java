package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.app.Activity;

import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

/**
 * @author 11718
 */
public interface Blackboard {

    /**
     * 当处于正在学习时的状态
     */
    void whileLearning(WordCardView wordCardView);


    /**
     * 当处于正在解释时的状态
     */
    void whileExposing(WordCardView wordCardView);

    /**
     *
     */
    void whileDismiss(WordCardView wordCardView);

    /**
     * 短击单词
     */
    void onSingleClickWord(WordCardView wordCardView);

    /**
     * 长按单词
     */
    void onLongClickWord(WordCardView wordCardView, Activity activity);
}
