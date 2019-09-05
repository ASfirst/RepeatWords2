package com.jeramtough.repeatwords2.component.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeramtough.jtandroid.function.AppSetting;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;

/**
 * @author 11718
 * on 2018  May 03 Thursday 14:50.
 */
@JtComponent
public class MyAppSetting extends AppSetting {
    private int learningMode = 0;

    @IocAutowire
    public MyAppSetting(Context context) {
        super(context);
    }

    public int getPerLearningCount() {
        return getSharedPreferences().getInt("perLearningCount", 10);
    }

    public void setPerLearningCount(int perLearningCount) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt("perLearningCount", perLearningCount);
        editor.apply();
    }

    public void setLearningMode(int learningMode) {
        this.learningMode = learningMode;
    }

    public int getLearningMode() {
        return learningMode;
    }

    public TeacherType getTeacherType() {
        int teacherTypeId = getSharedPreferences()
                .getInt("teacherType", TeacherType.LISTENING_TEACHER.getTeacherTypeId());
        return TeacherType.getTeacherType(teacherTypeId);
    }

    public void setTeacherType(TeacherType teacherType) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt("teacherType", teacherType.getTeacherTypeId());
        editor.commit();
    }

    public String getDateForLastOpenedApp() {
        return getSharedPreferences().getString("lastOpenedAppDate", null);
    }

    public void setDateForLastOpenedApp(String date) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString("lastOpenedAppDate", date);
        editor.apply();
    }

}
