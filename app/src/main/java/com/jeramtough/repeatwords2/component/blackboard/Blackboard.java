package com.jeramtough.repeatwords2.component.blackboard;

import android.widget.TextView;
import com.jeramtough.repeatwords2.bean.word.Word;

/**
 * @author 11718
 */
public interface Blackboard
{
    
    void whileLearning(Word word, TextView textView);
    
    
    void whileExposing(Word word, TextView textView);
    
    void whileDismiss(TextView textView);
}
