package com.jeramtough.repeatwords2.component.blackboard;

import android.graphics.Color;
import android.widget.TextView;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.baidu.Reader;

/**
 * @author 11718
 */
public class BlackboardOfSpeakingTeacher extends BlackboardOfTeacher {

    public BlackboardOfSpeakingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(Word word, TextView textView) {
        super.whileLearning(word, textView);

        textView.setText(word.getEn());
        getReader().speech(word.getCh());
    }

    @Override
    public void whileExposing(Word word, TextView textView) {
        super.whileExposing(word, textView);

        String content = word.getId() + "-" + word.getEn() + "-" + word.getPhonetic() + "\n" + word.getCh();
        textView.setText(content);
        getReader().speech(word.getEn());
    }
}
