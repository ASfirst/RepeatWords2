package com.jeramtough.repeatwords2.action.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.service.SettingService;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 23:51.
 */
public class SettingFragment extends BaseFragment
        implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private static final int BUSINESS_CODE_BACKUP_LEARNING_RECORD = 0;
    private static final int BUSINESS_CODE_RECOVER_LEARNING_RECORD = 1;
    private static final int BUSINESS_CODE_CLEAR_HAVED_LEARNED_WORD_TODAY = 2;

    private EditText editTextPerLearnCount;
    private EditText editTextReadEnglishSpeed;
    private EditText editTextIntervalTime;
    private Spinner spinnerSelectTeacher;
    private RadioGroup radioGroupSelectLearningMode;
    private RadioButton radioButtonNewMode;
    private RadioButton radioButtonReviewMode;
    private RadioButton radioButtonMarkedMode;
    private Button buttonBackup;
    private Button buttonRecover;
    private Button buttonClearToday;
    private TextView textViewMessage;


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
        spinnerSelectTeacher = findViewById(R.id.spinner_select_teacher);
        radioGroupSelectLearningMode = findViewById(R.id.radioGroup_select_learning_mode);
        radioButtonNewMode = findViewById(R.id.radioButton_new_mode);
        radioButtonReviewMode = findViewById(R.id.radioButton_review_mode);
        radioButtonMarkedMode = findViewById(R.id.radioButton_marked_mode);
        buttonBackup = findViewById(R.id.button_backup);
        buttonRecover = findViewById(R.id.button_recover);
        buttonClearToday = findViewById(R.id.button_clear_today);
        textViewMessage = findViewById(R.id.textView_message);

        buttonBackup.setOnClickListener(this);
        buttonRecover.setOnClickListener(this);
        buttonClearToday.setOnClickListener(this);
        spinnerSelectTeacher.setOnItemSelectedListener(this);

        radioGroupSelectLearningMode.setOnCheckedChangeListener(this);

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
            default:

        }

        switch (TeacherType.getTeacherType(settingService.getTeacherType())) {
            case LISTENING_TEACHER:
                spinnerSelectTeacher.setSelection(0, false);
                break;
            case SPEAKING_TEACHER:
                spinnerSelectTeacher.setSelection(1, false);
                break;
            case WRITING_TEACHER:
                spinnerSelectTeacher.setSelection(2, false);
                break;
            case READING_TEACHER:
                spinnerSelectTeacher.setSelection(3, false);
                break;
            default:
                break;

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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                settingService.setTeacherType(
                        TeacherType.LISTENING_TEACHER.getTeacherTypeId());
                break;
            case 1:
                settingService.setTeacherType(TeacherType.SPEAKING_TEACHER.getTeacherTypeId());
                break;
            case 2:
                settingService.setTeacherType(TeacherType.WRITING_TEACHER.getTeacherTypeId());
                break;
            case 3:
                settingService.setTeacherType(TeacherType.READING_TEACHER.getTeacherTypeId());
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    settingService.backupTheLearningRecord(new TaskCallbackInMain() {
                        @Override
                        protected void onTaskCompleted(TaskResult taskResult) {
                            if (taskResult.isSuccessful()) {
                                Toast.makeText(getContext(), "backup ok",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "backup fail",
                                        Toast.LENGTH_SHORT).show();
                            }

                            if (progressDialog.isShowing()) {
                                progressDialog.cancel();
                            }
                        }
                    });
                });
                break;

            case R.id.button_recover:

                showIsSureDialog("Do you want to recover?", () -> {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    settingService.recoverTheLearningRecord(new TaskCallbackInMain() {
                        @Override
                        protected void onTaskRunning(PreTaskResult preTaskResult,
                                                     int numerator, int denominator) {
                            progressDialog.setMessage(String.format("[%d/%d]\n", numerator,
                                    denominator) + preTaskResult.getMessage());
                        }

                        @Override
                        protected void onTaskCompleted(TaskResult taskResult) {
                            if (taskResult.isSuccessful()) {
                                Toast.makeText(getContext(), "recover ok",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "recover fail",
                                        Toast.LENGTH_SHORT).show();
                            }

                            if (progressDialog.isShowing()) {
                                progressDialog.cancel();
                            }
                        }
                    });
                });
                break;

            case R.id.button_clear_today:
                showIsSureDialog("Do you want to clear today's learned words?", () -> {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);
                    settingService.clearHaveLearnedWordToday(new TaskCallbackInMain() {
                        @Override
                        protected void onTaskCompleted(TaskResult taskResult) {
                            Toast.makeText(getContext(), "clear completed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                break;
        }
    }

    @Override
    public void handleFragmentMessage(Message message) {
        switch (message.what) {
            case BUSINESS_CODE_BACKUP_LEARNING_RECORD:

                break;
            case BUSINESS_CODE_RECOVER_LEARNING_RECORD:

                break;
            case BUSINESS_CODE_CLEAR_HAVED_LEARNED_WORD_TODAY:
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
