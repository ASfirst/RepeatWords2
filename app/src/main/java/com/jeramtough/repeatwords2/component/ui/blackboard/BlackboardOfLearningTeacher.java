package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.baidu.Reader;

public class BlackboardOfLearningTeacher extends BaseBlackboardOfTeacher
{
    
    public BlackboardOfLearningTeacher(Reader reader)
    {
        super(reader);
    }
    
    
    @Override
    public void whileLearning(Word word, TextView textView)
    {
        super.whileLearning(word, textView);
        
        textView.setText("(^ O ^)");
        getReader().speech(word.getEn());
    }
    
    @Override
    public void whileExposing(Word word, TextView textView)
    {
        super.whileExposing(word, textView);
        String content = word.getId()+"-"+word.getEn() + "-" + word.getPhonetic() + "\n" + word.getCh();
        textView.setText(content);
        getReader().speech(word.getCh()+","+word.getEn());
    }
}
