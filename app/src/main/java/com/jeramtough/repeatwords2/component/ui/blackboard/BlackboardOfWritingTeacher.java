package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * @author 11718
 */
public class BlackboardOfWritingTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfWritingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordDto wordDto, TextView textView) {
        super.whileLearning(wordDto, textView);
        if (wordDto.getChExplain().length() > 6) {
            String ch1 = wordDto.getChExplain().substring(0, 5);
            String ch2 = wordDto.getChExplain().substring(5, wordDto.getChExplain().length());
            textView.setText(ch1 + "\n" + ch2);
        }
        else {
            textView.setText(wordDto.getChExplain());
        }
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
