package com.jeramtough.repeatwords2.action.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
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

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordWithIsLearnedAtLeastTwiceToday;
import com.jeramtough.repeatwords2.service.LearningService;
import com.jeramtough.repeatwords2.component.adapter.MarkedWordCardsPagerAdapter;
import com.jeramtough.repeatwords2.component.adapter.NewWordCardsPagerAdapter;
import com.jeramtough.repeatwords2.component.adapter.ReviewWordCardsPagerAdapter;
import com.jeramtough.repeatwords2.component.adapter.WordCardsPagerAdapter;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.component.ui.blackboard.BaseBlackboardOfTeacher;
import com.jeramtough.repeatwords2.component.ui.blackboard.BlackboardOfLearningTeacher;
import com.jeramtough.repeatwords2.component.ui.blackboard.BlackboardOfSpeakingTeacher;
import com.jeramtough.repeatwords2.component.ui.blackboard.BlackboardOfWritingTeacher;
import com.jeramtough.repeatwords2.component.ui.wordcard.WordCardView;
import com.jeramtough.repeatwords2.action.activity.MainActivity;
import com.jeramtough.repeatwords2.action.dialog.WriteFromMemoryDialog;

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

    @InjectService
    private LearningService learningService;

    @InjectComponent
    private BaiduVoiceReader reader;

    private BaseBlackboardOfTeacher baseBlackboardOfTeacher;

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
        initBlackboardOfTeacher();
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
                baseBlackboardOfTeacher.whileDismiss(wordCardView.getTextViewContent());
            }
        }
    }

    @Override
    public void onClick(View v, int viewId) {
        switch (viewId) {
            case R.id.button_start_learning:
            case R.id.button_again_learn:
                buttonStartLearning.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
                learningService.initTeacher(
                        new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_INIT_TEACHER));
                break;
            case R.id.button_refresh:
                WordCardView wordCardView =
                        jtViewPager.findViewWithTag(jtViewPager.getCurrentItem());
                if (wordCardView != null) {
                    updatePreviousWordContent(wordCardView.getWord());
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
                updatePreviousWordContent(wordCardView.getWord());
                updateWordsCondition();
            }
            previousPosition = position;
        }
    }

    @Override
    public void inExposingArea(Word word, TextView textView) {
        baseBlackboardOfTeacher.whileExposing(word, textView);
    }

    @Override
    public void outExposingArea(Word word, TextView textView) {
        baseBlackboardOfTeacher.whileLearning(word, textView);
    }

    @Override
    public void atGraspingArea(Word word, TextView textView) {
        LearningMode learningMode = learningService.getLearningMode();
        textView.setBackgroundColor(Color.BLUE);
        switch (learningMode) {
            case NEW:
                learningService.graspWord(word,
                        new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_GRASP_WORD));
                break;
            case MARKED:
            case REVIME:
                learningService.removeWord(word,
                        new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_REMOVE_WORD));
                break;
        }

    }

    @Override
    public void atLearningArea(Word word, TextView textView) {
        learningService.learnedWordInToday(word);
        removePager(word);
    }

    @Override
    public void onSingleClickWord(Word word, TextView textView) {
        if (!reader.isReading()) {
            baseBlackboardOfTeacher.whileLearning(word, textView);
        }
        else {
            baseBlackboardOfTeacher.whileDismiss(textView);
        }
    }

    @Override
    public void onLongClickWord(Word word, TextView textView) {
        if (learningService.getTeacherType() == TeacherType.WRITING_TEACHER) {
            new WriteFromMemoryDialog(Objects.requireNonNull(getContext()), word).show();
        }
    }

    @Override
    public void onClickMarkButton(Word word, TextView textView) {
        learningService.markWord(word,
                new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_MARK_WORD));
    }

    @Override
    public void onClickDesertButton(Word word, TextView textView) {
        learningService.desertWord(word,
                new BusinessCaller(getFragmentHandler(), BUSINESS_CODE_DESERT_WORD));
        removePager(word);
    }

    @Override
    public void handleFragmentMessage(Message message) {
        switch (message.what) {
            case BUSINESS_CODE_INIT_TEACHER:
                shallLearningCount = message.getData().getInt("shallLearningSize");

                if (shallLearningCount > 0) {
                    /*Word[] words =
                            (Word[]) message.getData().getSerializable("shallLearningWords");*/
                    WordWithIsLearnedAtLeastTwiceToday[]
                            wordWithIsLearnedAtLeastTwiceTodays =
                            (WordWithIsLearnedAtLeastTwiceToday[])
                                    message.getData()
                                           .getSerializable(
                                                   "wordWithIsLearnedAtLeastTwiceTodays");


                    surplusLearningCount = Objects.requireNonNull(wordWithIsLearnedAtLeastTwiceTodays).length;

                    WordCardsPagerAdapter wordCardsPagerAdapter =
                            this.processingWordCardsPagerAdapter(wordWithIsLearnedAtLeastTwiceTodays);

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
                break;
            case BUSINESS_CODE_GRASP_WORD:
            case BUSINESS_CODE_REMOVE_WORD:
                Word word = (Word) message.getData().getSerializable("word");
                removePager(word);
                timedCloseTextView.setNiceMessage("OK");
                timedCloseTextView.visible();
                timedCloseTextView.closeDelayed(3000);
                break;
            case BUSINESS_CODE_MARK_WORD:
                if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL)) {
                    timedCloseTextView.setPrimaryMessage("OK");
                }
                else {
                    timedCloseTextView.setErrorMessage("have marked");
                }

                timedCloseTextView.visible();
                timedCloseTextView.closeDelayed(3000);
                break;
            case BUSINESS_CODE_DESERT_WORD:
                timedCloseTextView.setErrorMessage("OK");
                timedCloseTextView.visible();
                timedCloseTextView.closeDelayed(3000);
                break;
            default:
        }
    }

    //*****************************
    private void updatePreviousWordContent(Word word) {
        if (word != null) {
            textViewPreviousContent
                    .setText(word.getId() + ":" + word.getEn() + "-" + word.getCh());
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

    private void initBlackboardOfTeacher() {
        TeacherType currentTeacherType = learningService.getTeacherType();

        switch (currentTeacherType) {
            case LISTENING_TEACHER:
                baseBlackboardOfTeacher = new BlackboardOfLearningTeacher(reader);
                break;
            case SPEAKING_TEACHER:
                baseBlackboardOfTeacher = new BlackboardOfSpeakingTeacher(reader);
                break;
            case WRITING_TEACHER:
                baseBlackboardOfTeacher = new BlackboardOfWritingTeacher(reader);
                break;
        }
    }

    private void removePager(Word word) {
        int lastPosition = jtViewPager.getCurrentItem();
        WordCardsPagerAdapter adapter = (WordCardsPagerAdapter) jtViewPager.getAdapter();
        if (adapter != null) {
            adapter.removeWord(word);
        }

        //learningService.learnedWordInToday(word);

        if (lastPosition < jtViewPager.getAdapter().getCount()) {
            jtViewPager.setCurrentItem(lastPosition, true);
        }

        surplusLearningCount--;
        updateWordsCondition();
        updatePreviousWordContent(word);
        learnCurrentWord();
    }

    private void learnCurrentWord() {
        WordCardView wordCardView = jtViewPager.findViewWithTag(jtViewPager.getCurrentItem());
        if (wordCardView != null) {
            baseBlackboardOfTeacher
                    .whileLearning(wordCardView.getWord(), wordCardView.getTextViewContent());
        }
        else {
            reader.stop();
        }
    }

    private WordCardsPagerAdapter processingWordCardsPagerAdapter(Word[] words) {
        WordCardsPagerAdapter wordCardsPagerAdapter = null;
        LearningMode learningMode = learningService.getLearningMode();
        switch (learningMode) {
            case NEW:
                wordCardsPagerAdapter =
                        new NewWordCardsPagerAdapter(getContext(), words,
                                LearningFragment
                                        .this);
                break;
            case MARKED:
                wordCardsPagerAdapter =
                        new MarkedWordCardsPagerAdapter(getContext(), words,
                                LearningFragment
                                        .this);
                break;
            case REVIME:
                wordCardsPagerAdapter =
                        new ReviewWordCardsPagerAdapter(getContext(), words,
                                LearningFragment
                                        .this);
                break;
        }
        return wordCardsPagerAdapter;
    }
}
