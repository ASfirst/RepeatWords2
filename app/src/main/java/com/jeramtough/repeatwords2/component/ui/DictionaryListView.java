package com.jeramtough.repeatwords2.component.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jeramtough.jtlog3.P;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.adapter.DictionaryWordsAdapter;

import java.util.List;

public class DictionaryListView extends ListView implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    private OnItemOptionsWordListening onItemOptionsWordListening;
    private int position;
    private LinearLayout lastOptionsWordLayout;

    public DictionaryListView(Context context) {
        super(context);
        this.initResources();
    }

    public DictionaryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    protected void initResources() {
        this.setOnItemLongClickListener(this);
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        hideLastOptionsWordLayout();
                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        DictionaryListView.this.position = position;

        //hide last the layout if it not is null.
        hideLastOptionsWordLayout();

        LinearLayout layout = view.findViewById(R.id.layout_word_options);
        layout.setVisibility(View.VISIBLE);
        view.setBackgroundColor(Color.GRAY);
        AppCompatImageView imageViewWordDelete;
        AppCompatImageView imageViewWordModify;
        AppCompatImageView imageViewWordSpeak;
        AppCompatImageView imageViewWordDetail;
        imageViewWordDelete = view.findViewById(R.id.imageView_word_delete);
        imageViewWordModify = view.findViewById(R.id.imageView_word_modify);
        imageViewWordSpeak = view.findViewById(R.id.imageView_word_speak);
        imageViewWordDetail = view.findViewById(R.id.imageView_word_detail);

        imageViewWordDelete.setOnClickListener(DictionaryListView.this);
        imageViewWordModify.setOnClickListener(DictionaryListView.this);
        imageViewWordSpeak.setOnClickListener(DictionaryListView.this);
        imageViewWordDetail.setOnClickListener(DictionaryListView.this);

        lastOptionsWordLayout = layout;

        return false;
    }

    @Override
    public void onClick(View v) {
        if (onItemOptionsWordListening != null) {
            switch (v.getId()) {
                case R.id.imageView_word_delete:
                    onItemOptionsWordListening.onItemDeleteWord(getWordByPosition(position));
                    break;
                case R.id.imageView_word_detail:
                    onItemOptionsWordListening.onItemDetailWord(getWordByPosition(position));
                    break;
                case R.id.imageView_word_modify:
                    onItemOptionsWordListening.onItemModifyWord(getWordByPosition(position));
                    break;
                case R.id.imageView_word_speak:
                    onItemOptionsWordListening.onItemSpeakWord(getWordByPosition(position));
                    break;
            }
        }
    }

    public void setOnItemOptionsWordListening(OnItemOptionsWordListening onItemOptionsWordListening) {
        this.onItemOptionsWordListening = onItemOptionsWordListening;
    }

    public boolean moveToPositionOfWordByEn(String en) {
        for (int i = 0; i < getWords().length; i++) {
            Word word = getWords()[i];
            if (en.equals(word.getEn())) {
                int finalI = i;
                this.post(() -> {
                    setSelection(finalI);
                });
                return true;
            }
        }
        return false;
    }

    public boolean moveToPositionOfWordByCh(String ch) {
        for (int i = 0; i < getWords().length; i++) {
            Word word = getWords()[i];
            if (ch.equals(word.getCh())) {
                int finalI = i;
                this.post(() -> {
                    setSelection(finalI);
                });
                return true;
            }
        }
        return false;
    }

    public boolean moveToPositionOfWordByWordId(int wordId) {
        for (int i = 0; i < getWords().length; i++) {
            Word word = getWords()[i];
            if (wordId == word.getId()) {
                int finalI = i;
                this.post(() -> {
                    setSelection(finalI);
                });
                return true;
            }
        }
        return false;
    }

    //************
    private Word getWordByPosition(int position) {
        return (Word) getAdapter().getItem(position);
    }

    private Word[] getWords() {
        DictionaryWordsAdapter adapter = (DictionaryWordsAdapter) this.getAdapter();
        return adapter.getWords();
    }


    private void hideLastOptionsWordLayout() {
        if (lastOptionsWordLayout != null) {
            lastOptionsWordLayout.setVisibility(View.GONE);
            ViewGroup viewGroup = (ViewGroup) lastOptionsWordLayout.getParent();
            viewGroup.setBackgroundColor(Color.WHITE);
        }
    }

    //{{{{{{}}}}}}}}}
    public interface OnItemOptionsWordListening {

        void onItemDeleteWord(Word word);

        void onItemDetailWord(Word word);

        void onItemSpeakWord(Word word);

        void onItemModifyWord(Word word);

    }
}
