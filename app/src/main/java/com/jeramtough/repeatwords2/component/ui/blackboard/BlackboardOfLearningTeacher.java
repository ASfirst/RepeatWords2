package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.util.WordUtil;

public class BlackboardOfLearningTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfLearningTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileLearning(wordDto, textView, textViewBigBlackboard);

        textView.setText("(^ O ^)");
        getReader().speech(wordDto.getWord());
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileExposing(wordDto, textView, textViewBigBlackboard);
        textView.setText(wordDto.getWord());
        textViewBigBlackboard.setText(WordUtil.formatWordDto(wordDto));
        getReader().speech(
                wordDto.getChExplain() + "," + wordDto.getWord());
    }
}
