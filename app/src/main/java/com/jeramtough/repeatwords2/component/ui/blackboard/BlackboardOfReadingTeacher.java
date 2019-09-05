package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.widget.TextView;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.util.WordUtil;

/**
 * @author 11718
 */
public class BlackboardOfReadingTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfReadingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileLearning(wordDto, textView, textViewBigBlackboard);

        textView.setText(wordDto.getWord());
    }

    @Override
    public void whileExposing(WordDto wordDto, TextView textView,
                              TextView textViewBigBlackboard) {
        super.whileExposing(wordDto, textView, textViewBigBlackboard);
        textView.setText(WordUtil.abbreviateChinese(wordDto.getChExplain()));
    }
}
