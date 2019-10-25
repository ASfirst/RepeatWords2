package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.graphics.Color;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

/**
 * @author 11718
 */
public class BlackboardOfSpeakingTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfSpeakingTeacher(Reader reader) {
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
        wordCardView.getTextViewContent().setText(wordCardView.getWordDto().getPhonetic());
        getReader().speech(wordCardView.getWordDto().getWord());
    }

    @Override
    public void onSingleClickWord(WordCardView wordCardView) {
        if (!getReader().isReading()) {
            getReader().speech(wordCardView.getWordDto().getChExplain());
            wordCardView.getTextViewContent().setBackgroundColor(Color.BLUE);
        }
        else {
            getReader().stop();
            wordCardView.getTextViewContent().setBackgroundColor(Color.BLACK);
        }
    }
}
