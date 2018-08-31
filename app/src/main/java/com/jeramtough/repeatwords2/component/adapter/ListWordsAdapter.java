package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.WordWithRecordTime;

/**
 * @author 11718
 * on 2018  May 05 Saturday 15:22.
 */
public class ListWordsAdapter extends BaseAdapter {
    private Context context;
    private WordWithRecordTime[] wordWithRecordTimes;

    public ListWordsAdapter(Context context, WordWithRecordTime[] wordWithRecordTimes) {
        this.context = context;
        this.wordWithRecordTimes = wordWithRecordTimes;
    }

    @Override
    public int getCount() {
        return wordWithRecordTimes.length;
    }

    @Override
    public Object getItem(int position) {
        return wordWithRecordTimes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items_list_word, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewWordId = (TextView) convertView.findViewById(R.id.textView_word_id);
            viewHolder.textViewWordEn = (TextView) convertView.findViewById(R.id.textView_word_en);
            viewHolder.textViewWordCh = (TextView) convertView.findViewById(R.id.textView_word_ch);
            viewHolder.textViewWordPhonetic = (TextView) convertView.findViewById(R.id.textView_word_phonetic);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.textView_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WordWithRecordTime wordWithRecordTime = wordWithRecordTimes[position];
        if (wordWithRecordTime != null) {
            viewHolder.textViewWordId.setText(wordWithRecordTime.getId() + "");
            viewHolder.textViewWordEn.setText(wordWithRecordTime.getEn());
            viewHolder.textViewWordCh.setText(wordWithRecordTime.getCh());
            viewHolder.textViewWordPhonetic.setText(wordWithRecordTime.getPhonetic());
            viewHolder.textViewTime.setText(wordWithRecordTime.getTime());
        }
        return convertView;
    }
    
    public void setWordWithRecordTimes(WordWithRecordTime[] wordWithRecordTimes)
    {
        this.wordWithRecordTimes = wordWithRecordTimes;
    }
    
    //{{{{{{{}}}}}}}}
    private class ViewHolder {
        TextView textViewWordId;
        TextView textViewWordEn;
        TextView textViewWordCh;
        TextView textViewWordPhonetic;
        TextView textViewTime;
    }
}
