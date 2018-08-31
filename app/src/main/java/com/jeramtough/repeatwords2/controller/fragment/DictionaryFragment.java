package com.jeramtough.repeatwords2.controller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.business.DictionaryService;
import com.jeramtough.repeatwords2.component.adapter.DictionaryWordsAdapter;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.ui.DictionaryListView;
import com.jeramtough.repeatwords2.controller.activity.WordActivity;
import com.jeramtough.repeatwords2.controller.dialog.EditWordDialog;
import com.jeramtough.repeatwords2.controller.dialog.SearchWordDialog;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 23:51.
 */
public class DictionaryFragment extends BaseFragment implements DictionaryListView.OnItemOptionsWordListening {
    private static final int BUSINESS_CODE_MODIFY_DICTIONARY_WORD = 0;
    private static final int BUSINESS_CODE_GET_DICTIONARY_WORD = 1;


    private TextView textViewWordsTotality;
    private Button buttonAdd;
    private Button buttonSearch;
    private DictionaryListView listViewWords;
    private TimedCloseTextView timedCloseTextView;

    private BaiduVoiceReader baiduVoiceReader;

    @InjectService
    private DictionaryService dictionaryService;

    @InjectComponent
    private BaiduVoiceReader reader;

    @Override
    public int loadFragmentLayoutId() {
        return R.layout.fragment_dictionary;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewWordsTotality = findViewById(R.id.textView_words_totality);
        buttonAdd = findViewById(R.id.button_add);
        buttonSearch = findViewById(R.id.button_search);
        listViewWords = findViewById(R.id.listView_words);
        timedCloseTextView = findViewById(R.id.timedCloseTextView);

        buttonAdd.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        listViewWords.setOnItemOptionsWordListening(this);

        this.initResources();
    }

    @Override
    protected void initResources() {
        timedCloseTextView.setPrimaryMessage("init the dictionary");
        timedCloseTextView.visible();

        dictionaryService.getDictionaryWords(new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_GET_DICTIONARY_WORD));
    }
    
    @Override
    public void onSelected()
    {
        super.onSelected();
        reader.getBaiduVoiceSetting().setRepeated(false);
    }
    
    @Override
    public void onClick(View v, int viewId) {
        switch (viewId) {
            case R.id.button_add:
                EditWordDialog dialog = new EditWordDialog(getContext());
                dialog.setOnEditNewWordListening(new EditWordDialog.OnEditNewWordListening() {
                    @Override
                    public void onSure(String en, String ch) {
                        timedCloseTextView.post(() -> {
                            timedCloseTextView.setPrimaryMessage("adding...");
                        });
                        dictionaryService.addNewWordIntoDictionary(ch, en, new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_MODIFY_DICTIONARY_WORD));
                    }
                });
                dialog.show();
                break;
            case R.id.button_search:
                SearchWordDialog searchWordDialog = new SearchWordDialog(getContext());
                searchWordDialog.setOnEditWordInfoListening(new SearchWordDialog.OnEditWordInfoListening() {
                    @Override
                    public void onEditWordId(int wordId) {
                        boolean isSuccessful = listViewWords.moveToPositionOfWordByWordId(wordId);
                        movingToPositionOfWordIsSuccessful(isSuccessful);
                    }

                    @Override
                    public void onEditWordEn(String en) {
                        boolean isSuccessful = listViewWords.moveToPositionOfWordByEn(en);
                        movingToPositionOfWordIsSuccessful(isSuccessful);
                    }

                    @Override
                    public void onEditWordCh(String ch) {
                        boolean isSuccessful = listViewWords.moveToPositionOfWordByCh(ch);
                        movingToPositionOfWordIsSuccessful(isSuccessful);
                    }
                });
                searchWordDialog.show();
                break;
        }
    }

    @Override
    public void onItemDeleteWord(Word word) {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setMessage("Are you sure to remove this word for \"" + word.getEn() + "\"?").setNegativeButton("Cancel", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int wordId = word.getId();
                dictionaryService.deleteWordFromDictionary(wordId, new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_MODIFY_DICTIONARY_WORD));
            }
        }).show();
    }

    @Override
    public void onItemDetailWord(Word word) {
        Intent intent = new Intent(this.getContext(), WordActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }

    @Override
    public void onItemSpeakWord(Word word) {
        reader.speech(word.getEn());
    }

    @Override
    public void onItemModifyWord(final Word word) {
        EditWordDialog editWordDialog = new EditWordDialog(getContext(), word.getEn(), word.getCh());
        editWordDialog.setOnEditNewWordListening(new EditWordDialog.OnEditNewWordListening() {
            @Override
            public void onSure(String en, String ch) {
                timedCloseTextView.setPrimaryMessage("updating the word...");
                word.setEn(en);
                word.setCh(ch);
                dictionaryService.modifyWordOfDictionary(word, new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_MODIFY_DICTIONARY_WORD));
            }
        });
        editWordDialog.show();
    }

    @Override
    public void handleFragmentMessage(Message message) {
        Word[] words = (Word[]) message.getData().getSerializable("words");
        DictionaryWordsAdapter dictionaryWordsAdapter = (DictionaryWordsAdapter) listViewWords.getAdapter();
        if (words != null) {
            if (dictionaryWordsAdapter == null) {
                dictionaryWordsAdapter = new DictionaryWordsAdapter(getContext(), words);
            } else {
                dictionaryWordsAdapter.setWords(words);
                dictionaryWordsAdapter.notifyDataSetChanged();
            }
            textViewWordsTotality.setText(words.length + "");
        }

        switch (message.what) {
            case BUSINESS_CODE_GET_DICTIONARY_WORD:
            case BUSINESS_CODE_MODIFY_DICTIONARY_WORD:
                listViewWords.setAdapter(dictionaryWordsAdapter);
                timedCloseTextView.invisible();
                break;
        }
    }

    //**********************
    private void movingToPositionOfWordIsSuccessful(boolean isSuccessful) {
        if (!isSuccessful) {
            timedCloseTextView.setErrorMessage("don't find this word");
            timedCloseTextView.visible();
            timedCloseTextView.closeDelayed(2000);
        } else {
            timedCloseTextView.setNiceMessage("find this word");
            timedCloseTextView.visible();
            timedCloseTextView.closeDelayed(2000);
        }
    }

}
