package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

public class BlackboardOfLearningTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfLearningTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordDto wordDto, TextView textView) {
        super.whileLearning(wordDto, textView);

        textView.setText("(^ O ^)");
        getReader().speech(wordDto.getWord());
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView) {
        super.whileExposing(wordDto, textView);
        String content = wordDto.getFdId() + "-" + wordDto.getWord() + "-" + wordDto.getPhonetic() +
                "\n" + wordDto.getChExplain();
        textView.setText(content);
        getReader().speech(wordDto.getChExplain() + "," + wordDto.getWord());
    }
}
