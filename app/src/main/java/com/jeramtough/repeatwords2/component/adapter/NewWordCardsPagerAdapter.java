package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

/**
 * Created on 2018-11-27 22:19
 * by @author JeramTough
 */
public class NewWordCardsPagerAdapter extends WordCardsPagerAdapter {
    public NewWordCardsPagerAdapter(Context context,
                                    Word[] words,
                                    WordCardView.WordActionsListener wordActionsListener) {
        super(context, words, wordActionsListener);
    }

    @Override
    public void setWordCardView(int position, WordCardView wordCardView) {
        super.setWordCardView(position, wordCardView);
        wordCardView.getTextViewGrasped().setText("n-g");
        wordCardView.getTextViewLearning().setText("n-l");
        wordCardView.getTextViewExposing().setText("n-e");
    }
}
