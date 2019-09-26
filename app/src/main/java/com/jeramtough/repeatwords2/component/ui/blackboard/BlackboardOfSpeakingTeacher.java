package com.jeramtough.repeatwords2.component.ui.blackboard;

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
        getReader().speech(wordCardView.getWordDto().getChExplain());
    }

    @Override
    public void whileExposing(WordCardView wordCardView) {
        super.whileExposing(wordCardView);
        wordCardView.getTextViewContent().setText(wordCardView.getWordDto().getPhonetic());
        getReader().speech(wordCardView.getWordDto().getWord());
    }
}
