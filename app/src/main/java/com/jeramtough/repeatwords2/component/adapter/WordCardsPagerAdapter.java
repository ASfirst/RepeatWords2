package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 11718
 */
public class WordCardsPagerAdapter extends PagerAdapter {
    private Context context;
    private List<WordDto> wordDtoList;
    private WordCardView.WordActionsListener wordActionsListener;

    public WordCardsPagerAdapter(Context context, List<WordDto> wordDtoList,
                                 WordCardView.WordActionsListener wordActionsListener) {
        this.context = context;
        this.wordDtoList = wordDtoList;
        this.wordActionsListener = wordActionsListener;
    }

    public WordCardsPagerAdapter(Context context, WordDto[] wordDtoList,
                                 WordCardView.WordActionsListener wordActionsListener) {
        this.context = context;
        this.wordDtoList = new ArrayList<>();
        this.wordDtoList.addAll(Arrays.asList(wordDtoList));
        this.wordActionsListener = wordActionsListener;
    }


    @Override
    public int getCount() {
        return wordDtoList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        WordDto wordDto = wordDtoList.get(position);
        WordCardView wordCardView = new WordCardView(context, wordDto);
        setWordCardView(position, wordCardView);
        container.addView(wordCardView);
        return wordCardView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,
                            @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void removeWord(WordDto wordDto) {
        wordDtoList.remove(wordDto);
        notifyDataSetChanged();
    }

    public void resortWords() {
        Collections.shuffle(wordDtoList);
    }

    public void setWordCardView(int position, WordCardView wordCardView) {
        wordCardView.setTag(position);
        wordCardView.setWordActionsListener(wordActionsListener);
    }
}
