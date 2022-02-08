package com.jeramtough.repeatwords2.component.ui.blackboard;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.component.baidu.Reader;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;
import com.jeramtough.repeatwords2.util.WordUtil;

/**
 * @author 11718
 */
public abstract class BaseBlackboardOfTeacher implements Blackboard {
    private Reader reader;

    public BaseBlackboardOfTeacher(Reader reader) {
        this.reader = reader;
    }


    protected Reader getReader() {
        return reader;
    }

    @Override
    public void whileLearning(WordCardView wordCardView) {
        wordCardView.getLayoutBlackboard().setVisibility(View.INVISIBLE);
        wordCardView.getTextViewContent().setBackgroundResource(
                R.drawable.blackboard_background);
        if (wordCardView.getWordDto().isLearnedAtLeastTwiceToday()) {
            wordCardView.getTextViewContent().setTextColor(Color.BLUE);
        }
        else {
            wordCardView.getTextViewContent().setTextColor(Color.WHITE);
        }
        reader.stop();
    }

    @Override
    public void whileExposing(WordCardView wordCardView) {
        wordCardView.getTextViewBigBlackboard().setText(
                WordUtil.formatWordDto(wordCardView.getWordDto()));
        wordCardView.getLayoutBlackboard().setVisibility(View.VISIBLE);
        wordCardView.getTextViewContent().setBackgroundResource(R.color.transparent);
        wordCardView.getTextViewContent().setTextColor(Color.BLACK);
        reader.stop();
    }

    @Override
    public void whileDismiss(WordCardView wordCardView) {
        reader.stop();
        wordCardView.getTextViewContent().setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onSingleClickWord(WordCardView wordCardView) {
    }

    @Override
    public void onLongClickWord(WordCardView wordCardView, Activity activity) {
    }
}
