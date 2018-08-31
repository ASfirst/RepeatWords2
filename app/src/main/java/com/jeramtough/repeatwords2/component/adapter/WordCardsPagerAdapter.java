package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 11718
 */
public class WordCardsPagerAdapter extends PagerAdapter
{
    private Context context;
    private List<Word> words;
    private WordCardView.WordActionsListener wordActionsListener;
    
    public WordCardsPagerAdapter(Context context, List<Word> words,
            WordCardView.WordActionsListener wordActionsListener)
    {
        this.context = context;
        this.words = words;
        this.wordActionsListener = wordActionsListener;
    }
    
    public WordCardsPagerAdapter(Context context,Word[] words,
            WordCardView.WordActionsListener wordActionsListener)
    {
        this.context = context;
        this.words=new ArrayList<>();
        this.words.addAll(Arrays.asList(words));
        this.wordActionsListener = wordActionsListener;
    }
    
    
    @Override
    public int getCount()
    {
        return words.size();
    }
    
    
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }
    
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        Word word = words.get(position);
        WordCardView wordCardView = new WordCardView(context, word);
        wordCardView.setTag(position);
        wordCardView.setWordActionsListener(wordActionsListener);
        container.addView(wordCardView);
        return wordCardView;
    }
    
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        View view = (View) object;
        container.removeView(view);
    }
    
    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }
    
    public void removeWord(Word word)
    {
        words.remove(word);
        notifyDataSetChanged();
    }
    
    public void resortWords()
    {
        Collections.shuffle(words);
    }
}
