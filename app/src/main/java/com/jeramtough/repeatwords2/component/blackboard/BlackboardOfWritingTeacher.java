package com.jeramtough.repeatwords2.component.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.baidu.Reader;

/**
 * @author 11718
 */
public class BlackboardOfWritingTeacher extends BlackboardOfTeacher {

    public BlackboardOfWritingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(Word word, TextView textView) {
        super.whileLearning(word, textView);
        if (word.getCh().length() > 6) {
            String ch1 = word.getCh().substring(0, 5);
            String ch2 = word.getCh().substring(5, word.getCh().length() );
            textView.setText(ch1 + "\n" + ch2);
        } else {
            textView.setText(word.getCh());
        }
    }

    @Override
    public void whileExposing(Word word, TextView textView) {
        super.whileExposing(word, textView);

        String content = word.getId() + "-" + word.getEn() + "-" + word.getPhonetic() + "\n" + word.getCh();
        textView.setText(content);
        getReader().speech(word.getEn());
    }
}
