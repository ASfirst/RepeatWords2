package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.app.Activity;
import android.graphics.Color;

import com.jeramtough.repeatwords2.action.dialog.WriteFromMemoryDialog;
import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

/**
 * @author 11718
 */
public class BlackboardOfWritingTeacher extends BaseBlackboardOfTeacher {

    public BlackboardOfWritingTeacher(Reader reader) {
        super(reader);
    }


    @Override
    public void whileLearning(WordCardView wordCardView) {
        super.whileLearning(wordCardView);
        if (wordCardView.getWordDto().getChExplain().length() > 6) {
            String ch1 = wordCardView.getWordDto().getChExplain().substring(0, 5);
            String ch2 = wordCardView.getWordDto().getChExplain().substring(5,
                    wordCardView.getWordDto().getChExplain().length());
            wordCardView.getTextViewContent().setText(ch1 + "\n" + ch2);
        }
        else {
            wordCardView.getTextViewContent().setText(
                    wordCardView.getWordDto().getChExplain());
        }
    }

    @Override
    public void whileExposing(WordCardView wordCardView) {
        super.whileExposing(wordCardView);

        wordCardView.getTextViewContent().setText(wordCardView.getWordDto().getWord());
    }

    @Override
    public void onLongClickWord(WordCardView wordCardView, Activity activity) {
        new WriteFromMemoryDialog(activity,
                wordCardView.getWordDto()).show();
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
