package com.jeramtough.repeatwords2.action.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.action.activity.MainActivity;
import com.jeramtough.repeatwords2.action.dialog.WriteFromMemoryDialog;
import com.jeramtough.repeatwords2.component.adapter.WordCardsPagerAdapter;
import com.jeramtough.repeatwords2.component.adapter.WordCardsPagerAdapterProvider;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.component.ui.blackboard.BlackBoardProvider;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.service.LearningService;
import com.jeramtough.repeatwords2.service.impl.LearningServiceImpl;
import com.jeramtough.repeatwords2.util.WordUtil;

import java.util.Objects;

/**
 * @author 11718
 */
public class LearningFragment extends BaseFragment
        implements WordCardView.WordActionsListener {
    private static final int BUSINESS_CODE_INIT_TEACHER = 0;
    private static final int BUSINESS_CODE_GRASP_WORD = 2;
    private static final int BUSINESS_CODE_DESERT_WORD = 3;
    private static final int BUSINESS_CODE_MARK_WORD = 4;
    private static final int BUSINESS_CODE_REMOVE_WORD = 5;

    private Button buttonAgainLearn;
    private TextView textViewPreviousContent;
    private TimedCloseTextView timedCloseTextView;
    private TextView textViewCondition;
    private AppCompatImageButton buttonRefresh;
    private AppCompatImageButton buttonReset;
    private JtViewPager jtViewPager;
    private LinearLayout layoutGate;
    private ProgressBar progressBar;
    private Button buttonStartLearning;

    @InjectService(impl = LearningServiceImpl.class)
    private LearningService learningService;

    @InjectComponent
    private BaiduVoiceReader reader;

    @InjectComponent
    private BlackBoardProvider blackBoardProvider;

    @InjectComponent
    private WordCardsPagerAdapterProvider wordCardsPagerAdapterProvider;

    private int shallLearningCount;
    private int surplusLearningCount;
    private int previousPosition = 0;

    @Override
    public int loadFragmentLayoutId() {
        return R.layout.fragment_learning;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAgainLearn = findViewById(R.id.button_again_learn);
        textViewPreviousContent = findViewById(R.id.textView_previous_content);
        timedCloseTextView = findViewById(R.id.timedCloseTextView);
        textViewCondition = findViewById(R.id.textView_condition);
        buttonReset = findViewById(R.id.button_reset);
        buttonRefresh = findViewById(R.id.button_refresh);
        jtViewPager = findViewById(R.id.jtViewPager);
        layoutGate = findViewById(R.id.layout_gate);
        progressBar = findViewById(R.id.progressBar);
        buttonStartLearning = findViewById(R.id.button_start_learning);

        progressBar.setVisibility(View.INVISIBLE);

        buttonStartLearning.setOnClickListener(this);
        buttonRefresh.setOnClickListener(this);
        buttonAgainLearn.setOnClickListener(this);
        buttonReset.setOnClickListener(this);

        jtViewPager.addOnPageChangeListener(new MySimpleOnPageChangeListener());

        initResources();
    }

    @Override
    protected void initResources() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSelected() {
        super.onSelected();

        reader.getBaiduVoiceSetting().setRepeated(true);
    }

    @Override
    public void onUnselected() {
        if (reader.isReading()) {
            WordCardView wordCardView =
                    jtViewPager.findViewWithTag(jtViewPager.getCurrentItem());
            if (wordCardView != null) {
                blackBoardProvider.get().whileDismiss(wordCardView.getTextViewContent());
            }
        }
    }

    @Override
    public void onClick(View v, int viewId) {
        switch (viewId) {
            case R.id.button_start_learning:
            case R.id.button_again_learn:
                startToLearning();
                break;

            case R.id.button_refresh:
                WordCardView wordCardView =
                        jtViewPager.findViewWithTag(jtViewPager.getCurrentItem());
                if (wordCardView != null) {
                    updatePreviousWordContent(wordCardView.getWordDto());
                }

                WordCardsPagerAdapter adapter =
                        (WordCardsPagerAdapter) jtViewPager.getAdapter();
                Objects.requireNonNull(adapter).resortWords();
                adapter.notifyDataSetChanged();

                if (adapter.getCount() > 0) {
                    jtViewPager.setCurrentItem(0, true);
                }

                learnCurrentWord();
                break;
            case R.id.button_reset:
                reader.stop();
                MainActivity mainActivity = (MainActivity) getActivity();
                Objects.requireNonNull(
                        mainActivity).getMainNavigationHandler().updateStudyFragment();
                break;
        }
    }


    public class MySimpleOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            learnCurrentWord();
            WordCardView wordCardView = jtViewPager.findViewWithTag(previousPosition);
            if (wordCardView != null) {
                updatePreviousWordContent(wordCardView.getWordDto());
                updateWordsCondition();
            }
            previousPosition = position;
        }
    }

    @Override
    public void inExposingArea(WordDto wordDto, TextView textView,
                               WordCardView wordCardView) {
        blackBoardProvider.get().whileExposing(wordCardView);
    }

    @Override
    public void outExposingArea(WordDto wordDto, TextView textView,
                                WordCardView wordCardView) {
        blackBoardProvider.get().whileLearning(wordCardView);
    }

    @Override
    public void atGraspingArea(WordDto wordDto, TextView textView) {
        textView.setBackgroundColor(Color.BLUE);
        learningService.graspOrRemoveWord(wordDto, new TaskCallbackInMain() {
            @Override
            protected void onTaskCompleted(TaskResult taskResult) {
                timedCloseTextView.setNiceMessage("OK");
                timedCloseTextView.visible();
                timedCloseTextView.closeDelayed(2000);
                removePager(wordDto);
            }
        });
    }

    @Override
    public void atLearningArea(WordDto wordDto, TextView textView) {
        learningService.learnedWordInToday(wordDto);
        removePager(wordDto);

    }

    @Override
    public void onSingleClickWord(WordDto wordDto, TextView textView,
                                  WordCardView wordCardView) {
        if (!reader.isReading()) {
            blackBoardProvider.get().whileLearning(wordCardView);
        }
        else {
            blackBoardProvider.get().whileDismiss(textView);
        }
    }

    @Override
    public void onLongClickWord(WordDto wordDto, TextView textView,
                                WordCardView wordCardView) {
        if (blackBoardProvider.getTeacherType() == TeacherType.WRITING_TEACHER) {
            new WriteFromMemoryDialog(Objects.requireNonNull(getContext()), wordDto).show();
        }
    }

    @Override
    public void onClickMarkButton(WordDto wordDto, TextView textView) {
        learningService.markWord(wordDto, new TaskCallbackInMain() {
            @Override
            protected void onTaskCompleted(TaskResult taskResult) {
                if (taskResult.isSuccessful()) {
                    timedCloseTextView.setPrimaryMessage("OK");
                }
                else {
                    timedCloseTextView.setErrorMessage("have marked");
                }

                timedCloseTextView.visible();
                timedCloseTextView.closeDelayed(3000);
            }
        });
    }

    @Override
    public void onClickDesertButton(WordDto wordDto, TextView textView) {

        learningService.desertWord(wordDto, new TaskCallbackInMain() {
            @Override
            protected void onTaskCompleted(TaskResult taskResult) {

                if (taskResult.isSuccessful()) {
                    timedCloseTextView.setPrimaryMessage("OK");
                }
                else {
                    timedCloseTextView.setErrorMessage("have deserted");
                }

                timedCloseTextView.visible();
                timedCloseTextView.closeDelayed(3000);

                removePager(wordDto);
            }
        });
    }

    @Override
    public void onClickPronounceButton(WordDto wordDto) {
        reader.speechOnce(wordDto.getWord());
    }


    //*****************************

    private void startToLearning() {
        buttonStartLearning.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
        learningService.initTeacher(new TaskCallbackInMain() {
            @Override
            protected void onTaskCompleted(TaskResult taskResult) {
                WordDto[] wordDtos = (WordDto[]) taskResult.getSerializablePayload(
                        "wordDtos");
                shallLearningCount = wordDtos.length;
                surplusLearningCount=wordDtos.length;

                if (shallLearningCount > 0) {

                    WordCardsPagerAdapter wordCardsPagerAdapter =
                            wordCardsPagerAdapterProvider
                                    .get(wordDtos, LearningFragment.this);

                    jtViewPager.setAdapter(wordCardsPagerAdapter);
                    jtViewPager.setInitFinishedCaller(() -> {
                        layoutGate.setVisibility(View.GONE);
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonStartLearning.setClickable(true);
                        learnCurrentWord();
                        updateWordsCondition();
                    });
                }
                else {
                    Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    buttonStartLearning.setClickable(true);
                }
            }
        });
    }


    private void updatePreviousWordContent(WordDto wordDto) {
        if (wordDto != null) {
            textViewPreviousContent
                    .setText(
                            wordDto.getFdId() + ":" + wordDto.getWord() + "-" + WordUtil.abbreviateChinese(
                                    wordDto.getChExplain()));
        }
    }

    private void updateWordsCondition() {

        String currentPosition;
        if (jtViewPager.getChildCount() > 0) {
            currentPosition = jtViewPager.getCurrentItem() + 1 + "";
        }
        else {
            currentPosition = "*";
        }
        textViewCondition.setText(
                currentPosition + "-" + surplusLearningCount + "-" + shallLearningCount);
    }

    private void removePager(WordDto wordDto) {
        int lastPosition = jtViewPager.getCurrentItem();
        WordCardsPagerAdapter adapter = (WordCardsPagerAdapter) jtViewPager.getAdapter();
        if (adapter != null) {
            adapter.removeWord(wordDto);
        }

        learningService.learnedWordInToday(wordDto);

        if (lastPosition < jtViewPager.getAdapter().getCount()) {
            jtViewPager.setCurrentItem(lastPosition, true);
        }

        surplusLearningCount--;
        updateWordsCondition();
        updatePreviousWordContent(wordDto);

        learnCurrentWord();
    }

    private void learnCurrentWord() {
        WordCardView wordCardView = jtViewPager.findViewWithTag(jtViewPager.getCurrentItem());
        if (wordCardView != null) {
            blackBoardProvider.get()
                              .whileLearning(
                                      wordCardView);
        }
        else {
            reader.stop();
        }
    }
}
