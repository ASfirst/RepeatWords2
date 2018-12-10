package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;
import com.jeramtough.repeatwords2.bean.word.Word;

/**
 * @author 11718
 */
public interface Blackboard
{

    /**
     * 当处于正在学习时的状态
     */
    void whileLearning(Word word, TextView textView);


    /**
     * 当处于正在解释时的状态
     */
    void whileExposing(Word word, TextView textView);

    /**
     *
     */
    void whileDismiss(TextView textView);
}
