package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.graphics.Color;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;
import com.jeramtough.repeatwords2.util.WordUtil;

/**
 * @author 11718
 */
public class BlackboardOfReadingTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfReadingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordCardView wordCardView) {
        super.whileLearning(wordCardView);
        wordCardView.getTextViewContent().setText(wordCardView.getWordDto().getWord());
    }

    @Override
    public void whileExposing(WordCardView wordCardView) {
        super.whileExposing(wordCardView);
        wordCardView.getTextViewContent().setText(
                WordUtil.abbreviateChinese(wordCardView.getWordDto().getChExplain()));
    }

    @Override
    public void onSingleClickWord(WordCardView wordCardView) {
        if (!getReader().isReading()) {
            getReader().speech(wordCardView.getWordDto().getWord());
            wordCardView.getTextViewContent().setBackgroundColor(Color.BLUE);
        }
        else {
            getReader().stop();
            wordCardView.getTextViewContent().setBackgroundColor(Color.BLACK);
        }
    }
}
