package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * @author 11718
 */
public interface Blackboard
{

    /**
     * 当处于正在学习时的状态
     */
    void whileLearning(WordDto wordDto, TextView textView,
                       TextView textViewBigBlackboard);


    /**
     * 当处于正在解释时的状态
     */
    void whileExposing(WordDto wordDto, TextView textView,
                       TextView textViewBigBlackboard);

    /**
     *
     */
    void whileDismiss(TextView textView);
}
