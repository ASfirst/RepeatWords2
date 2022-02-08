package com.jeramtough.repeatwords2.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.dao.dto.record.WordRecordDto;

/**
 * @author 11718
 * on 2018  May 05 Saturday 15:22.
 */
public class ListWordsAdapter extends BaseAdapter {
    private Context context;
    private WordRecordDto[] wordRecordDtos;

    public ListWordsAdapter(Context context, WordRecordDto[] wordRecordDtos) {
        this.context = context;
        this.wordRecordDtos = wordRecordDtos;
    }

    @Override
    public int getCount() {
        return wordRecordDtos.length;
    }

    @Override
    public Object getItem(int position) {
        return wordRecordDtos[position];
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
            viewHolder.textViewWordId = (TextView) convertView.findViewById(
                    R.id.textView_word_id);
            viewHolder.textViewWordEn = (TextView) convertView.findViewById(
                    R.id.textView_word_en);
            viewHolder.textViewWordCh = (TextView) convertView.findViewById(
                    R.id.textView_word_ch);
            viewHolder.textViewWordPhonetic = (TextView) convertView.findViewById(
                    R.id.textView_word_phonetic);
            viewHolder.textViewWordLevel =
                    (TextView) convertView.findViewById(R.id.textView_word_level);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.textView_time);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WordRecordDto wordRecordDto = wordRecordDtos[position];
        if (wordRecordDto != null) {
            viewHolder.textViewWordId.setText(wordRecordDto.getFdId() + "");
            viewHolder.textViewWordEn.setText(wordRecordDto.getWord());
            viewHolder.textViewWordCh.setText(wordRecordDto.getMiniChExplain());
            viewHolder.textViewWordPhonetic.setText(wordRecordDto.getPhonetic());
            viewHolder.textViewWordLevel.setText(wordRecordDto.getLevel()+"");
            viewHolder.textViewTime.setText(wordRecordDto.getTime());
        }
        return convertView;
    }

    public void setWordWithRecordTimes(WordRecordDto[] wordRecordDtos) {
        this.wordRecordDtos = wordRecordDtos;
    }

    //{{{{{{{}}}}}}}}

    private class ViewHolder {
        TextView textViewWordId;
        TextView textViewWordEn;
        TextView textViewWordCh;
        TextView textViewWordPhonetic;
        TextView textViewWordLevel;
        TextView textViewTime;
    }
}
