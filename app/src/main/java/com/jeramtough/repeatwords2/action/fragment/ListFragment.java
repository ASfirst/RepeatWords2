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

import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtandroid.util.ViewUtil;
import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.component.adapter.ListWordsAdapter;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.dao.dto.record.WordRecordDto;
import com.jeramtough.repeatwords2.service.ListService;
import com.jeramtough.repeatwords2.service.impl.ListServiceImpl;

public class ListFragment extends BaseFragment
        implements RadioGroup.OnCheckedChangeListener, View.OnLongClickListener,
        AdapterView.OnItemLongClickListener {

    private RadioGroup radioGroup;
    private RadioButton radioButtonTodayWords;
    private RadioButton radioButtonHaveGraspedWords;
    private RadioButton radioButtonShallLearningWords;
    private RadioButton radioButtonHaveMarkedWords;
    private RadioButton radioButtonHaveDesertedWords;
    private TextView textViewWordsTotality;
    private ListView listViewWords;
    private TimedCloseTextView timedCloseTextView;


    @InjectService(impl = ListServiceImpl.class)
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
        initResources();
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
        else {
            //如果已经选中today,刷新一下
            onCheckedChanged(null, radioButtonTodayWords.getId());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton_today_words:
                timedCloseTextView.setPrimaryMessage("init today words");
                timedCloseTextView.visible();
                ViewUtil.disableRadioGroup(radioGroup);
                listService.getHaveLearnedWordRecordsInToday(new TaskCallbackInMain() {
                    @Override
                    protected void onTaskCompleted(TaskResult taskResult) {
                        WordRecordDto[] wordRecordDtos =
                                (WordRecordDto[]) taskResult.getSerializablePayload(
                                        "wordRecordDtos");
                        showList(wordRecordDtos);
                    }
                });
                break;

            case R.id.radioButton_have_grasped_words:
                timedCloseTextView.setPrimaryMessage("init have grasped words");
                timedCloseTextView.visible();
                ViewUtil.disableRadioGroup(radioGroup);
                listService.getHaveGraspedWords(new TaskCallbackInMain() {
                    @Override
                    protected void onTaskCompleted(TaskResult taskResult) {
                        WordRecordDto[] wordRecordDtos =
                                (WordRecordDto[]) taskResult.getSerializablePayload(
                                        "wordRecordDtos");
                        showList(wordRecordDtos);
                    }
                });


                break;
            case R.id.radioButton_shall_learning_words:
                timedCloseTextView.setPrimaryMessage("init shall learning words");
                timedCloseTextView.visible();
                ViewUtil.disableRadioGroup(radioGroup);
                listService.getShallLearningWords(new TaskCallbackInMain() {
                    @Override
                    protected void onTaskCompleted(TaskResult taskResult) {
                        WordRecordDto[] wordRecordDtos =
                                (WordRecordDto[]) taskResult.getSerializablePayload(
                                        "wordRecordDtos");
                        showList(wordRecordDtos);
                    }
                });


                break;
            case R.id.radioButton_have_marked_words:
                timedCloseTextView.setPrimaryMessage("init marked words");
                timedCloseTextView.visible();
                ViewUtil.disableRadioGroup(radioGroup);
                listService.getMarkedWords(new TaskCallbackInMain() {
                    @Override
                    protected void onTaskCompleted(TaskResult taskResult) {
                        WordRecordDto[] wordRecordDtos =
                                (WordRecordDto[]) taskResult.getSerializablePayload(
                                        "wordRecordDtos");
                        showList(wordRecordDtos);
                    }
                });

                break;
            case R.id.radioButton_have_deserted_words:
                timedCloseTextView.setPrimaryMessage("init deserted words");
                timedCloseTextView.visible();
                ViewUtil.disableRadioGroup(radioGroup);
                listService.getDesertedWords(new TaskCallbackInMain() {
                    @Override
                    protected void onTaskCompleted(TaskResult taskResult) {
                        WordRecordDto[] wordRecordDtos =
                                (WordRecordDto[]) taskResult.getSerializablePayload(
                                        "wordRecordDtos");
                        showList(wordRecordDtos);
                    }
                });

                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final WordRecordDto wordRecordDto =
                (WordRecordDto) listViewWords.getAdapter().getItem(position);
        new AlertDialog.Builder(getContext()).setMessage(
                "Are you sure to remove this word for \""
                        + wordRecordDto.getWord()
                        + "\"?").setNegativeButton("Cancel",
                null).setPositiveButton("Yes",
                (DialogInterface dialog1, int which) -> {
                    if (radioButtonTodayWords.isChecked()) {
                        listService.removeWordFromHaveLearnedTodayList(wordRecordDto,
                                new TaskCallbackInMain() {
                                    @Override
                                    protected void onTaskCompleted(TaskResult taskResult) {
                                        onCheckedChanged(radioGroup,
                                                R.id.radioButton_today_words);
                                    }
                                });
                    }
                    else if (radioButtonHaveGraspedWords.isChecked()) {
                        listService.removeWordFromHaveGraspedList(wordRecordDto,
                                new TaskCallbackInMain() {
                                    @Override
                                    protected void onTaskCompleted(TaskResult taskResult) {
                                        onCheckedChanged(radioGroup,
                                                R.id.radioButton_have_grasped_words);

                                    }
                                });
                    }
                    else if (radioButtonShallLearningWords.isChecked()) {
                        listService.removeWordFromShallLearningList(wordRecordDto,
                                new TaskCallbackInMain() {
                                    @Override
                                    protected void onTaskCompleted(TaskResult taskResult) {
                                        onCheckedChanged(radioGroup,
                                                R.id.radioButton_shall_learning_words);
                                    }
                                });
                    }
                    else if (radioButtonHaveMarkedWords.isChecked()) {
                        listService.removeWordFromMarkedList(wordRecordDto,
                                new TaskCallbackInMain() {
                                    @Override
                                    protected void onTaskCompleted(TaskResult taskResult) {
                                        onCheckedChanged(radioGroup,
                                                R.id.radioButton_have_marked_words);
                                    }
                                });
                    }
                    else if (radioButtonHaveDesertedWords.isChecked()) {
                        listService.removeWordFromDesertedList(wordRecordDto,
                                new TaskCallbackInMain() {
                                    @Override
                                    protected void onTaskCompleted(TaskResult taskResult) {
                                        onCheckedChanged(radioGroup,
                                                R.id.radioButton_have_deserted_words);

                                    }
                                });
                    }
                }).show();
        return true;
    }


    @Override
    public void handleFragmentMessage(Message message) {

        /*WordWithRecordTime[] wordWithRecordTimes = (WordWithRecordTime[]) message.getData()
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
        }*/
    }

    //***********************

    private void showList(WordRecordDto[] wordRecordDtos) {
        ListWordsAdapter wordsAdapter = (ListWordsAdapter) listViewWords.getAdapter();
        if (wordsAdapter == null) {
            wordsAdapter = new ListWordsAdapter(getContext(), wordRecordDtos);
        }
        else {
            wordsAdapter.setWordWithRecordTimes(wordRecordDtos);
            wordsAdapter.notifyDataSetChanged();
        }

        timedCloseTextView.invisible();
        textViewWordsTotality.setText(wordRecordDtos.length + "");
        listViewWords.setAdapter(wordsAdapter);
        ViewUtil.enableRadioGroup(radioGroup);
    }
}
