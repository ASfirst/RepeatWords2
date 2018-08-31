package com.jeramtough.repeatwords2.component.blackboard;

import android.graphics.Color;
import android.widget.TextView;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.baidu.Reader;

/**
 * @author 11718
 */
public abstract class BlackboardOfTeacher implements Blackboard
{
    private Reader reader;
    
    public BlackboardOfTeacher(Reader reader)
    {
        this.reader = reader;
    }
    
    
    protected Reader getReader()
    {
        return reader;
    }
    
    @Override
    public void whileLearning(Word word, TextView textView)
    {
        textView.setBackgroundResource(R.drawable.blackboard_background);
        textView.setTextColor(Color.WHITE);
        reader.stop();
    }
    
    @Override
    public void whileExposing(Word word, TextView textView)
    {
        textView.setBackgroundResource(R.color.transparent);
        textView.setTextColor(Color.BLACK);
        reader.stop();
    }
    
    @Override
    public void whileDismiss(TextView textView)
    {
        reader.stop();
        textView.setTextColor(Color.RED);
    }
}
