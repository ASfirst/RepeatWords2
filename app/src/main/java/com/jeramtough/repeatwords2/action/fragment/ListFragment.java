package com.jeramtough.repeatwords2.action.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.WordWithRecordTime;
import com.jeramtough.repeatwords2.service.ListService;
import com.jeramtough.repeatwords2.component.adapter.ListWordsAdapter;

public class ListFragment extends BaseFragment
        implements RadioGroup.OnCheckedChangeListener, View.OnLongClickListener,
        AdapterView.OnItemLongClickListener {

    private static final int BUSINESS_CODE_GET_HAVE_GRASPED_WORDS = 1;
    private static final int BUSINESS_CODE_GET_SHALL_LEARNING_WORDS = 2;
    private static final int BUSINESS_CODE_GET_MARKED_WORDS = 3;
    private static final int BUSINESS_CODE_GET_DESERTED_WORDS = 4;
    private static final int BUSINESS_CODE_GET_TODAY_WORDS = 5;

    private static final int BUSINESS_CODE_REMOVE_WORD_FROM_HAVE_GRASPED_LIST = 11;
    private static final int BUSINESS_CODE_REMOVE_WORD_FROM_SHALL_LEARNING_LIST = 12;
    private static final int BUSINESS_CODE_REMOVE_WORD_FROM_MARKED_LIST = 13;
    private static final int BUSINESS_CODE_REMOVE_WORD_FROM_DESERTED_LIST = 14;
    private static final int BUSINESS_CODE_REMOVE_WORD_FROM_TODAY_LIST = 15;

    private RadioGroup radioGroup;
    private RadioButton radioButtonTodayWords;
    private RadioButton radioButtonHaveGraspedWords;
    private RadioButton radioButtonShallLearningWords;
    private RadioButton radioButtonHaveMarkedWords;
    private RadioButton radioButtonHaveDesertedWords;
    private TextView textViewWordsTotality;
    private ListView listViewWords;
    private TimedCloseTextView timedCloseTextView;


    @InjectService
    private ListService listService;

    @Override
    public int loadFragmentLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonTodayWords = findViewById(R.id.radioButton_today_words);
        radioButtonHaveGraspedWords = findViewById(R.id.radioButton_have_grasped_words);
        radioButtonShallLearningWords = findViewById(R.id.radioButton_shall_learning_words);
        radioButtonHaveMarkedWords = findViewById(R.id.radioButton_have_marked_words);
        radioButtonHaveDesertedWords = findViewById(R.id.radioButton_have_deserted_words);
        textViewWordsTotality = findViewById(R.id.textView_words_totality);
        listViewWords = findViewById(R.id.listView_words);
        timedCloseTextView = findViewById(R.id.timedCloseTextView);

        radioGroup.setOnCheckedChangeListener(this);
        listViewWords.setOnItemLongClickListener(this);
       // initResources();
    }

    @Override
    protected void initResources() {
        radioButtonTodayWords.setChecked(true);
    }

    @Override
    public void onSelected() {
        //重新选中时，默认选中today
        if (!radioButtonTodayWords.isChecked()) {
            radioButtonTodayWords.setChecked(true);
        }
        else{
            //如果已经选中today,刷新一下
            onCheckedChanged(null,radioButtonTodayWords.getId());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton_today_words:
                timedCloseTextView.setPrimaryMessage("init today words");
                timedCloseTextView.visible();
                listService.getTodaysHaveLearnedWords(new BusinessCaller(getFragmentHandler(),
                        BUSINESS_CODE_GET_TODAY_WORDS));
                break;

            case R.id.radioButton_have_grasped_words:
                timedCloseTextView.setPrimaryMessage("init have grasped words");
                timedCloseTextView.visible();
                listService.getHaveGraspedWords(new BusinessCaller(getFragmentHandler(),
                        BUSINESS_CODE_GET_HAVE_GRASPED_WORDS));

                break;
            case R.id.radioButton_shall_learning_words:
                timedCloseTextView.setPrimaryMessage("init shall learning words");
                timedCloseTextView.visible();
                listService.getShallLearningWords(new BusinessCaller(getFragmentHandler(),
                        BUSINESS_CODE_GET_SHALL_LEARNING_WORDS));

                break;
            case R.id.radioButton_have_marked_words:
                timedCloseTextView.setPrimaryMessage("init marked words");
                timedCloseTextView.visible();
                listService.getMarkedWords(new BusinessCaller(getFragmentHandler(),
                        BUSINESS_CODE_GET_MARKED_WORDS));

                break;
            case R.id.radioButton_have_deserted_words:
                timedCloseTextView.setPrimaryMessage("init deserted words");
                timedCloseTextView.visible();
                listService.getDesertedWords(new BusinessCaller(getFragmentHandler(),
                        BUSINESS_CODE_GET_DESERTED_WORDS));

                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final WordWithRecordTime wordWithRecordTime =
                (WordWithRecordTime) listViewWords.getAdapter().getItem(position);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setMessage(
                "Are you sure to remove this word for \"" + wordWithRecordTime.getEn() + "\"?")
                                                                  .setNegativeButton("Cancel",
                                                                          null)
                                                                  .setPositiveButton("Yes",
                                                                          (DialogInterface dialog1, int which) -> {
                                                                              int wordId = wordWithRecordTime.getId();
                                                                              if (radioButtonTodayWords.isChecked()) {
                                                                                  listService.removeWordFromTodaysHaveLearnedList(
                                                                                          wordId,
                                                                                          new BusinessCaller(
                                                                                                  getFragmentHandler(),
                                                                                                  BUSINESS_CODE_REMOVE_WORD_FROM_TODAY_LIST));
                                                                              }
                                                                              else if (radioButtonHaveGraspedWords.isChecked()) {
                                                                                  listService.removeWordFromHaveGraspedList(
                                                                                          wordId,
                                                                                          new BusinessCaller(
                                                                                                  getFragmentHandler(),
                                                                                                  BUSINESS_CODE_REMOVE_WORD_FROM_HAVE_GRASPED_LIST));
                                                                              }
                                                                              else if (radioButtonShallLearningWords.isChecked()) {
                                                                                  listService.removeWordFromShallLearningList(
                                                                                          wordId,
                                                                                          new BusinessCaller(
                                                                                                  getFragmentHandler(),
                                                                                                  BUSINESS_CODE_REMOVE_WORD_FROM_SHALL_LEARNING_LIST));
                                                                              }
                                                                              else if (radioButtonHaveMarkedWords.isChecked()) {
                                                                                  listService.removeWordFromMarkedList(
                                                                                          wordId,
                                                                                          new BusinessCaller(
                                                                                                  getFragmentHandler(),
                                                                                                  BUSINESS_CODE_REMOVE_WORD_FROM_MARKED_LIST));
                                                                              }
                                                                              else if (radioButtonHaveDesertedWords.isChecked()) {
                                                                                  listService.removeWordFromDesertedList(
                                                                                          wordId,
                                                                                          new BusinessCaller(
                                                                                                  getFragmentHandler(),
                                                                                                  BUSINESS_CODE_REMOVE_WORD_FROM_DESERTED_LIST));
                                                                              }
                                                                          }).show();
        return true;
    }


    @Override
    public void handleFragmentMessage(Message message) {

        WordWithRecordTime[] wordWithRecordTimes = (WordWithRecordTime[]) message.getData()
                                                                                 .getSerializable(
                                                                                         "wordWithRecordTimes");
        ListWordsAdapter wordsAdapter = (ListWordsAdapter) listViewWords.getAdapter();
        if (wordsAdapter == null) {
            wordsAdapter = new ListWordsAdapter(getContext(), wordWithRecordTimes);
        }
        else {
            wordsAdapter.setWordWithRecordTimes(wordWithRecordTimes);
            wordsAdapter.notifyDataSetChanged();
        }
        timedCloseTextView.invisible();

        textViewWordsTotality.setText(wordWithRecordTimes.length + "");

        switch (message.what) {
            case BUSINESS_CODE_GET_TODAY_WORDS:
            case BUSINESS_CODE_REMOVE_WORD_FROM_TODAY_LIST:

            case BUSINESS_CODE_REMOVE_WORD_FROM_HAVE_GRASPED_LIST:
            case BUSINESS_CODE_GET_HAVE_GRASPED_WORDS:

            case BUSINESS_CODE_REMOVE_WORD_FROM_SHALL_LEARNING_LIST:
            case BUSINESS_CODE_GET_SHALL_LEARNING_WORDS:

            case BUSINESS_CODE_REMOVE_WORD_FROM_MARKED_LIST:
            case BUSINESS_CODE_GET_MARKED_WORDS:

            case BUSINESS_CODE_REMOVE_WORD_FROM_DESERTED_LIST:
            case BUSINESS_CODE_GET_DESERTED_WORDS:

                listViewWords.setAdapter(wordsAdapter);
                break;
        }
    }

}
