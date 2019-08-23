package com.jeramtough.repeatwords2.controller.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.business.SettingService;
import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 23:51.
 */
public class SettingFragment extends BaseFragment
        implements RadioGroup.OnCheckedChangeListener {
    private static final int BUSINESS_CODE_BACKUP_LEARNING_RECORD = 0;
    private static final int BUSINESS_CODE_RECOVER_LEARNING_RECORD = 1;
    private static final int BUSINESS_CODE_CLEAR_HAVED_LEARNED_WORD_TODAY = 2;

    private EditText editTextPerLearnCount;
    private EditText editTextReadEnglishSpeed;
    private EditText editTextIntervalTime;
    private RadioGroup radioGroupSelectTeacher;
    private RadioButton radioButtonListeningTeacher;
    private RadioButton radioButtonSpeakingTeacher;
    private RadioButton radioButtonWritingTeacher;
    private RadioGroup radioGroupSelectLearningMode;
    private RadioButton radioButtonNewMode;
    private RadioButton radioButtonReviewMode;
    private RadioButton radioButtonMarkedMode;
    private Button buttonBackup;
    private Button buttonRecover;
    private Button buttonClearToday;

    private ProgressDialog progressDialog;

    @InjectService
    private SettingService settingService;

    @Override
    public int loadFragmentLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextPerLearnCount = findViewById(R.id.editText_per_learn_count);
        editTextReadEnglishSpeed = findViewById(R.id.editText_read_english_speed);
        editTextIntervalTime = findViewById(R.id.editText_interval_time);
        radioGroupSelectTeacher = findViewById(R.id.radioGroup_select_teacher);
        radioButtonListeningTeacher = findViewById(R.id.radioButton_listening_teacher);
        radioButtonSpeakingTeacher = findViewById(R.id.radioButton_speaking_teacher);
        radioGroupSelectLearningMode = findViewById(R.id.radioGroup_select_learning_mode);
        radioButtonNewMode = findViewById(R.id.radioButton_new_mode);
        radioButtonReviewMode = findViewById(R.id.radioButton_review_mode);
        radioButtonMarkedMode = findViewById(R.id.radioButton_marked_mode);
        buttonBackup = findViewById(R.id.button_backup);
        buttonRecover = findViewById(R.id.button_recover);
        buttonClearToday = findViewById(R.id.button_clear_today);
        radioButtonWritingTeacher = findViewById(R.id.radioButton_writing_teacher);

        buttonBackup.setOnClickListener(this);
        buttonRecover.setOnClickListener(this);
        buttonClearToday.setOnClickListener(this);

        radioGroupSelectLearningMode.setOnCheckedChangeListener(this);
        radioGroupSelectTeacher.setOnCheckedChangeListener(this);

        this.initResources();
    }

    @Override
    protected void initResources() {
        editTextPerLearnCount.setText(settingService.getPerLearningCount() + "");
        editTextIntervalTime.setText(settingService.getRepeatIntervalTime() + "");
        editTextReadEnglishSpeed.setText(settingService.getReadEnglishSpeed() + "");

        switch (LearningMode.getLearningMode(settingService.getLearningMode())) {
            case NEW:
                radioButtonNewMode.setChecked(true);
                break;
            case MARKED:
                radioButtonMarkedMode.setChecked(true);
                break;
            case REVIME:
                radioButtonReviewMode.setChecked(true);
                break;
        }

        switch (TeacherType.getLearningMode(settingService.getTeacherType())) {
            case LISTENING_TEACHER:
                radioButtonListeningTeacher.setChecked(true);
                break;
            case SPEAKING_TEACHER:
                radioButtonSpeakingTeacher.setChecked(true);
                break;
            case WRITING_TEACHER:
                radioButtonWritingTeacher.setChecked(true);
        }
    }

    @Override
    public void onUnselected() {
        super.onUnselected();
        saveSetting();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == radioGroupSelectLearningMode) {
            switch (checkedId) {
                case R.id.radioButton_new_mode:
                    settingService.setLearningMode(LearningMode.NEW.getLearningModeId());
                    break;
                case R.id.radioButton_review_mode:
                    settingService.setLearningMode(LearningMode.REVIME.getLearningModeId());
                    break;
                case R.id.radioButton_marked_mode:
                    settingService.setLearningMode(LearningMode.MARKED.getLearningModeId());
                    break;
            }
        }
        else if (group == radioGroupSelectTeacher) {
            switch (checkedId) {
                case R.id.radioButton_listening_teacher:
                    settingService
                            .setTeacherType(TeacherType.LISTENING_TEACHER.getTeacherTypeId());
                    break;
                case R.id.radioButton_speaking_teacher:
                    settingService
                            .setTeacherType(TeacherType.SPEAKING_TEACHER.getTeacherTypeId());
                    break;
                case R.id.radioButton_writing_teacher:
                    settingService
                            .setTeacherType(TeacherType.WRITING_TEACHER.getTeacherTypeId());
                    break;
            }
        }
    }

    @Override
    public void onClick(View v, int viewId) {
        super.onClick(v, viewId);
        switch (viewId) {
            case R.id.button_backup:
                showIsSureDialog("Do you want to backup?", () -> {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    settingService.backupTheLearningRecord(
                            new BusinessCaller(getFragmentHandler(),
                                    BUSINESS_CODE_BACKUP_LEARNING_RECORD));
                });
                break;

            case R.id.button_recover:

                showIsSureDialog("Do you want to recover?", () -> {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    settingService.recoverTheLearningRecord(
                            new BusinessCaller(getFragmentHandler(),
                                    BUSINESS_CODE_RECOVER_LEARNING_RECORD));
                });
                break;

            case R.id.button_clear_today:
                showIsSureDialog("Do you want to clear today's learned words?", () -> {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);
                    settingService.clearHavedLearnedWordToday(new BusinessCaller
                            (getFragmentHandler(),
                                    BUSINESS_CODE_CLEAR_HAVED_LEARNED_WORD_TODAY));
                });
                break;
        }
    }

    @Override
    public void handleFragmentMessage(Message message) {
        switch (message.what) {
            case BUSINESS_CODE_BACKUP_LEARNING_RECORD:
                if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL)) {
                    Toast.makeText(getContext(), "backup ok", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "backup fail", Toast.LENGTH_SHORT).show();
                }

                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
                break;
            case BUSINESS_CODE_RECOVER_LEARNING_RECORD:
                if (message.getData().getBoolean(BusinessCaller.IS_SUCCESSFUL)) {
                    Toast.makeText(getContext(), "recover ok", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "recover fail", Toast.LENGTH_SHORT).show();
                }

                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
                break;
            case BUSINESS_CODE_CLEAR_HAVED_LEARNED_WORD_TODAY:
                Toast.makeText(getContext(), "clear finishly", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    //*************
    private void saveSetting() {
        if (editTextPerLearnCount.getText().length() > 0) {
            settingService.setPerLearningCount(
                    Integer.parseInt(editTextPerLearnCount.getText().toString()));
        }

        if (editTextReadEnglishSpeed.getText().length() > 0) {
            settingService.setReadEnglishSpeed(
                    Integer.parseInt(editTextReadEnglishSpeed.getText().toString()));
        }

        if (editTextIntervalTime.getText().length() > 0) {
            settingService.setRepeatIntervalTime(
                    Long.parseLong(editTextIntervalTime.getText().toString()));
        }
    }

    private void showIsSureDialog(String message, Runnable simpleCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message).setNegativeButton("NO", null).setPositiveButton
                ("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleCallback.run();
                    }
                }).create().show();
    }

}
