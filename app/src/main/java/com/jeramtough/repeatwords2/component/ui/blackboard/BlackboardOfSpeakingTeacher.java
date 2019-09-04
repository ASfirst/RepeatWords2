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
    public void whileLearning(WordDto wordDto, TextView textView) {
        super.whileLearning(wordDto, textView);

        textView.setText(wordDto.getWord());
        getReader().speech(wordDto.getChExplain());
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView) {
        super.whileExposing(wordDto, textView);

        String content =
                wordDto.getFdId() + "-" + wordDto.getWord() + "-" + wordDto.getPhonetic() +
                        "\n" + wordDto.getChExplain();
        textView.setText(content);
        getReader().speech(wordDto.getWord());
    }
}
