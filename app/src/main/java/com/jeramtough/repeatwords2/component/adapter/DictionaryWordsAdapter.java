package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;

/**
 * @author 11718
 * on 2018  May 05 Saturday 15:22.
 */
public class DictionaryWordsAdapter extends BaseAdapter {

    private Context context;
    private Word[] words;

    public DictionaryWordsAdapter(Context context, Word[] words) {
        this.context = context;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.length + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position != words.length) {
            return words[position];
        }
        else {
            return words[position - 1];
        }
    }

    @Override
    public long getItemId(int position) {
        if (position != words.length ) {
            return position;
        }
        else {
            return position - 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items_dictionary_word,
                    null);

            viewHolder = new ViewHolder();
            viewHolder.textViewWordId = convertView.findViewById(R.id.textView_word_id);
            viewHolder.textViewWordEn = convertView.findViewById(R.id.textView_word_en);
            viewHolder.textViewWordCh = convertView.findViewById(R.id.textView_word_ch);
            viewHolder.textViewWordPhonetic = convertView.findViewById(
                    R.id.textView_word_phonetic);
            viewHolder.imageViewWordDelete = convertView.findViewById(
                    R.id.imageView_word_delete);
            viewHolder.imageViewWordModify = convertView.findViewById(
                    R.id.imageView_word_modify);
            viewHolder.imageViewWordSpeak = convertView.findViewById(
                    R.id.imageView_word_speak);
            viewHolder.imageViewWordDetail = convertView.findViewById(
                    R.id.imageView_word_detail);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position != words.length) {
            Word word = words[position];
            if (word != null) {
                viewHolder.textViewWordId.setText(word.getId() + "");
                viewHolder.textViewWordEn.setText(word.getEn());
                viewHolder.textViewWordCh.setText(word.getCh());
                viewHolder.textViewWordPhonetic.setText(word.getPhonetic());
            }
        }
        else {
            viewHolder.textViewWordId.setText("/\n/");
            viewHolder.textViewWordEn.setText("/\n/");
            viewHolder.textViewWordCh.setText("/\n/");
            viewHolder.textViewWordPhonetic.setText("/\n/");
        }
        return convertView;
    }

    public Word[] getWords() {
        return this.words;
    }

    public void setWords(Word[] words) {
        this.words = words;
    }

    //{{{{{{{}}}}}}}}
    private class ViewHolder {
        TextView textViewWordId;
        TextView textViewWordEn;
        TextView textViewWordCh;
        TextView textViewWordPhonetic;
        AppCompatImageView imageViewWordDelete;
        AppCompatImageView imageViewWordModify;
        AppCompatImageView imageViewWordSpeak;
        AppCompatImageView imageViewWordDetail;
    }
}
