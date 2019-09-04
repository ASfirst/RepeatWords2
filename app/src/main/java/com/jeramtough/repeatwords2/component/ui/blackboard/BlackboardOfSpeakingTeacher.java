package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * @author 11718
 */
public class BlackboardOfSpeakingTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfSpeakingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileLearning(wordDto, textView, textViewBigBlackboard);

        textView.setText(wordDto.getWord());
        getReader().speech(wordDto.getChExplain());
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileExposing(wordDto, textView, textViewBigBlackboard);

        String content =
                wordDto.getFdId() + "-" + wordDto.getWord() + "-" + wordDto.getPhonetic() +
                        "\n" + wordDto.getChExplain();
        textView.setText(content);
        getReader().speech(wordDto.getWord());
    }
}
