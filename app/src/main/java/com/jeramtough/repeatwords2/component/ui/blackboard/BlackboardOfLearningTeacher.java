package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.view.View;
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
        textViewBigBlackboard.setVisibility(View.INVISIBLE);
        getReader().speech(wordDto.getWord());
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileExposing(wordDto, textView, textViewBigBlackboard);
        String content = wordDto.getFdId() + "-" + wordDto.getWord() + "-" + wordDto.getPhonetic() +
                "\n" + wordDto.getChExplain();
        textView.setText(wordDto.getWord());
        textViewBigBlackboard.setVisibility(View.VISIBLE);
        textViewBigBlackboard.setText(WordUtil.formatWordDto(wordDto));
        getReader().speech(
                wordDto.getChExplain() + "," + wordDto.getWord());
    }
}
