package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;
import android.graphics.Color;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

/**
 * Created on 2018-11-27 22:21
 * by @author JeramTough
 */
public class MarkedWordCardsPagerAdapter extends WordCardsPagerAdapter {
    public MarkedWordCardsPagerAdapter(Context context,
                                       Word[] words,
                                       WordCardView.WordActionsListener wordActionsListener) {
        super(context, words, wordActionsListener);
    }

    @Override
    public void setWordCardView(int position, WordCardView wordCardView) {
        super.setWordCardView(position, wordCardView);
        wordCardView.getTextViewGrasped().setText("m-r");
        wordCardView.getTextViewLearning().setText("m-l");
        wordCardView.getTextViewExposing().setText("m-e");
        wordCardView.getButtonMark().setClickable(false);
        wordCardView.getButtonMark().setTextColor(Color.GRAY);
    }
}
