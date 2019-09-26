package com.jeramtough.repeatwords2.component.ui.blackboard;

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
}
