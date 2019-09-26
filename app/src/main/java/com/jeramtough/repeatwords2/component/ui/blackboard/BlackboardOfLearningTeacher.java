package com.jeramtough.repeatwords2.component.ui.blackboard;

import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

public class BlackboardOfLearningTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfLearningTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordCardView wordCardView) {
        super.whileLearning(wordCardView);

        wordCardView.getTextViewContent().setText("(^ O ^)");
        getReader().speech(wordCardView.getWordDto().getWord());
    }

    @Override
    public void whileExposing(WordCardView wordCardView) {
        super.whileExposing(wordCardView);
        wordCardView.getTextViewContent().setText(wordCardView.getWordDto().getWord());
        getReader().speech(
                wordCardView.getWordDto().getChExplain() + "," + wordCardView.getWordDto().getWord());
    }
}
