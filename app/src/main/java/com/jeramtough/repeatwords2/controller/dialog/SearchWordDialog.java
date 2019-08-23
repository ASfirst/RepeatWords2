package com.jeramtough.repeatwords2.controller.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jeramtough.jtandroid.controller.dialog.JtIocDialog;
import com.jeramtough.jtcomponent.utils.WordUtil;
import com.jeramtough.repeatwords2.R;

/**
 * @author 11718
 * on 2018  May 05 Saturday 17:40.
 */
public class SearchWordDialog extends JtIocDialog implements View.OnClickListener {
    private EditText editTextWordInfo;
    private Button buttonCancel;
    private Button buttonOk;

    private String ch, en;

    private OnEditWordInfoListening onEditWordInfoListening;

    public SearchWordDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.dialog_search_word);
        initViews();
    }

    protected void initViews() {
        editTextWordInfo = findViewById(R.id.editText_word_info);
        buttonCancel = findViewById(R.id.button_cancel);
        buttonOk = findViewById(R.id.button_ok);

        buttonCancel.setOnClickListener(this);
        buttonOk.setOnClickListener(this);

        initResources();
    }

    protected void initResources() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                if (this.isShowing()) {
                    cancel();
                }
                break;
            case R.id.button_ok:
                if (editTextWordInfo.getText().length() > 0 &&
                        onEditWordInfoListening != null) {
                    String wordInfo = editTextWordInfo.getText().toString();
                    try {
                        int wordId = Integer.parseInt(wordInfo);
                        onEditWordInfoListening.onEditWordId(wordId);
                        cancel();
                        break;
                    } catch (Exception ignore) {
                    }
                    if (WordUtil.isContainsChinese(wordInfo)) {
                        onEditWordInfoListening.onEditWordCh(wordInfo);
                    } else {
                        onEditWordInfoListening.onEditWordEn(wordInfo);
                    }
                    cancel();
                } else {
                    buttonCancel.performClick();
                }
                break;
        }
    }

    public void setOnEditWordInfoListening(OnEditWordInfoListening onEditWordInfoListening) {
        this.onEditWordInfoListening = onEditWordInfoListening;
    }

    //{{{{{{{{{}}}}}}}
    public interface OnEditWordInfoListening {
        void onEditWordId(int wordId);

        void onEditWordEn(String en);

        void onEditWordCh(String ch);
    }
}
