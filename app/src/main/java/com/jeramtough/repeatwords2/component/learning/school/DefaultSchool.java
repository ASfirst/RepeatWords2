package com.jeramtough.repeatwords2.component.learning.school;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;
import com.jeramtough.repeatwords2.component.learning.school.teacher.MarkWordTeacher;
import com.jeramtough.repeatwords2.component.learning.school.teacher.NewWordTeacher;
import com.jeramtough.repeatwords2.component.learning.school.teacher.ReviewWordTeacher;
import com.jeramtough.repeatwords2.component.learning.school.teacher.Teacher1;

/**
 * 负责提供老师对象
 * <p>
 * Created on 2019-09-03 18:34
 * by @author JeramTough
 */
@JtComponent
public class DefaultSchool implements School {

    private MyAppSetting myAppSetting;
    private NewWordTeacher newWordTeacher;
    private ReviewWordTeacher reviewWordTeacher;
    private MarkWordTeacher markWordTeacher;

    @IocAutowire
    public DefaultSchool(MyAppSetting myAppSetting,
                         NewWordTeacher newWordTeacher,
                         ReviewWordTeacher reviewWordTeacher,
                         MarkWordTeacher markWordTeacher) {
        this.myAppSetting = myAppSetting;
        this.newWordTeacher = newWordTeacher;
        this.reviewWordTeacher = reviewWordTeacher;
        this.markWordTeacher = markWordTeacher;
    }

    @Override
    public Teacher1 getCurrentTeacher() {
        switch (LearningMode.getLearningMode(myAppSetting.getLearningMode())) {
            case NEW:
                return newWordTeacher;
            case REVIME:
                return reviewWordTeacher;
            case MARKED:
                return markWordTeacher;
            default:
        }
        return null;
    }

    @Override
    public Teacher1 getNewWordTeacher() {
        return newWordTeacher;
    }

    @Override
    public Teacher1 getMarkWordTeacher() {
        return markWordTeacher;
    }

    @Override
    public Teacher1 getReviewWordTeacher() {
        return reviewWordTeacher;
    }
}
