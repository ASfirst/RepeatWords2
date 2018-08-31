package com.jeramtough.repeatwords2.controller.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jeramtough.jtandroid.controller.dialog.JtIocDialog;
import com.jeramtough.jtutil.WordUtil;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;

/**
 * @author 11718
 * on 2018  May 05 Saturday 17:40.
 */
public class EditWordDialog extends JtIocDialog implements View.OnClickListener {
    private EditText editTextChinese;
    private EditText editTextEnglish;
    private Button buttonCancel;
    private Button buttonOk;

    private String ch, en;

    private OnEditNewWordListening onEditNewWordListening;

    public EditWordDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.dialog_add_new_word);
        initViews();
    }

    public EditWordDialog(@NonNull Context context, String en, String ch) {
        super(context);
        this.ch = ch;
        this.en = en;
        this.setContentView(R.layout.dialog_add_new_word);
        initViews();
    }

    protected void initViews() {
        editTextChinese = findViewById(R.id.editText_chinese);
        editTextEnglish = findViewById(R.id.editText_english);
        buttonCancel = findViewById(R.id.button_cancel);
        buttonOk = findViewById(R.id.button_ok);

        buttonCancel.setOnClickListener(this);
        buttonOk.setOnClickListener(this);

        initResources();
    }

    protected void initResources() {
        editTextChinese.setText(ch);
        editTextEnglish.setText(en);
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
                if (editTextChinese.getText().length() > 0 &&
                        editTextEnglish.getText().length() > 0 &&
                        onEditNewWordListening != null) {
                    String ch = editTextChinese.getText().toString();
                    String en = editTextEnglish.getText().toString();
                    if (WordUtil.isContainsChinese(ch) && !WordUtil.isContainsChinese(en)) {
                        onEditNewWordListening.onSure(en, ch);
                    }
                    cancel();
                } else {
                    buttonCancel.performClick();
                }
            {
                break;
            }
        }
    }

    public void setOnEditNewWordListening(OnEditNewWordListening onEditNewWordListening) {
        this.onEditNewWordListening = onEditNewWordListening;
    }

    //{{{{{{{{{}}}}}}}
    public interface OnEditNewWordListening {
        void onSure(String en, String ch);
    }
}
