package com.jeramtough.repeatwords2.action.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jeramtough.jtandroid.controller.dialog.JtIocDialog;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

/**
 * Created on 2018-06-13 18:46
 * by @author JeramTough
 */
public class WriteFromMemoryDialog extends JtIocDialog
        implements TextWatcher, View.OnClickListener {

    private TextView textViewWordInfo;
    private EditText editTextWord;
    private Button buttonForgot;
    private Button buttonEn;

    private String iWrited;
    private boolean isForgot = false;

    private WordDto wordDto;

    public WriteFromMemoryDialog(@NonNull Context context, WordDto wordDto) {
        super(context);
        this.wordDto = wordDto;
        this.setContentView(R.layout.dialog_write_from_memory);

        textViewWordInfo = findViewById(R.id.textView_word_info);
        editTextWord = findViewById(R.id.editText_word);
        buttonForgot = findViewById(R.id.button_forgot);
        buttonEn = findViewById(R.id.button_en);

        textViewWordInfo.setText(wordDto.getChExplain());

        editTextWord.addTextChangedListener(this);
        buttonEn.setOnClickListener(this);
        buttonForgot.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initResources();
    }

    protected void initResources() {
        showSoftInputFromWindow();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!isForgot) {
            if (s.toString().equals(wordDto.getWord())) {
                editTextWord.setTextColor(0xff06ba3a);
            }
            else {
                editTextWord.setTextColor(Color.BLACK);
            }
        }
        else {
            isForgot = false;
            editTextWord.setText(s.toString().charAt(0) + "");
            editTextWord.setSelection(editTextWord.getText().length());
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonEn.getId()) {
            cancel();
        }
        else if (v.getId() == buttonForgot.getId()) {
            iWrited = editTextWord.getText().toString();
            char[] iWriteds = iWrited.toCharArray();
            char[] words = wordDto.getWord().toCharArray();
            String htmlCode = "";
            for (int i = 0; i < words.length; i++) {
                char a = words[i];
                char b = iWriteds.length >= i + 1 ? iWriteds[i] : '`';
                if (a == b) {
                    htmlCode = htmlCode + getColorSpanHtml("#06ba3a", a + "");
                }
                else {
                    htmlCode = htmlCode + getColorSpanHtml("#FF0000", a + "");
                }
            }

            if (Build.VERSION.SDK_INT >= 24) {
                editTextWord.setText(Html.fromHtml(htmlCode, Html.FROM_HTML_MODE_COMPACT));
            }
            else {
                editTextWord.setText(Html.fromHtml(htmlCode));
            }

            isForgot = true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    //****************************
    private void showSoftInputFromWindow() {
        editTextWord.setFocusable(true);
        editTextWord.setFocusableInTouchMode(true);
        editTextWord.requestFocus();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private String getColorSpanHtml(String color, String text) {
        String htmlCode = "<font color=\"" + color + "\">" + text + "</font>";
        return htmlCode;
    }
}
