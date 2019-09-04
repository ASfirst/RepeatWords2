package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.graphics.Color;
import android.widget.TextView;

import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * @author 11718
 */
public abstract class BaseBlackboardOfTeacher implements Blackboard {
    private Reader reader;

    public BaseBlackboardOfTeacher(Reader reader) {
        this.reader = reader;
    }


    protected Reader getReader() {
        return reader;
    }

    @Override
    public void whileLearning(WordDto wordDto, TextView textView) {
        textView.setBackgroundResource(R.drawable.blackboard_background);
        if (wordDto.isLearnedAtLeastTwiceToday()) {
            textView.setTextColor(Color.BLUE);
        }
        else {
            textView.setTextColor(Color.WHITE);
        }
        reader.stop();
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView) {
        textView.setBackgroundResource(R.color.transparent);
        textView.setTextColor(Color.BLACK);
        reader.stop();
    }

    @Override
    public void whileDismiss(TextView textView) {
        reader.stop();
        textView.setTextColor(Color.RED);
    }
}
