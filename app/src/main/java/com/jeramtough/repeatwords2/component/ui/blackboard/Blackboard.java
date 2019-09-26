package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

/**
 * @author 11718
 */
public interface Blackboard
{

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
    void whileDismiss(TextView textView);
}
