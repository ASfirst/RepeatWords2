package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * Created on 2019-09-04 01:55
 * by @author JeramTough
 */
@JtComponent
public class WordCardsPagerAdapterProvider {

    private MyAppSetting myAppSetting;
    private Context context;

    @IocAutowire
    public WordCardsPagerAdapterProvider(
            MyAppSetting myAppSetting, Context context) {
        this.myAppSetting = myAppSetting;
        this.context = context;
    }

    public WordCardsPagerAdapter get(WordDto[] wordDtos,
                                     WordCardView.WordActionsListener wordActionsListener) {
        WordCardsPagerAdapter wordCardsPagerAdapter = null;
        LearningMode learningMode =
                LearningMode.getLearningMode(myAppSetting.getLearningMode());
        switch (learningMode) {
            case NEW:
                wordCardsPagerAdapter =
                        new NewWordCardsPagerAdapter(context, wordDtos, wordActionsListener);
                break;
            case MARKED:
                wordCardsPagerAdapter =
                        new MarkedWordCardsPagerAdapter(context, wordDtos,
                                wordActionsListener);
                break;
            case REVIME:
                wordCardsPagerAdapter =
                        new ReviewWordCardsPagerAdapter(context, wordDtos,
                                wordActionsListener);
                break;
        }
        return wordCardsPagerAdapter;
    }
}
