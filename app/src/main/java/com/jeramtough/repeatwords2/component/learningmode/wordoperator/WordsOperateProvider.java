package com.jeramtough.repeatwords2.component.learningmode.wordoperator;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:07.
 */
@JtComponent
public class WordsOperateProvider {
    private MyAppSetting myAppSetting;
    private NewModeWordsOperator newModeWordsOperator;
    private ReviewModeWordsOperator reviewModeWordsOperator;
    private MarkModeWordsOperator markModeWordsOperator;

    @IocAutowire
    public WordsOperateProvider(MyAppSetting myAppSetting,
                                NewModeWordsOperator newModeWordsOperator,
                                ReviewModeWordsOperator reviewModeWordsOperator,
                                MarkModeWordsOperator markModeWordsOperator) {
        this.myAppSetting = myAppSetting;
        this.newModeWordsOperator = newModeWordsOperator;
        this.reviewModeWordsOperator = reviewModeWordsOperator;
        this.markModeWordsOperator = markModeWordsOperator;
    }


    public WordsOperator getWordsOperator() {
        switch (getLearningMode()) {
            case NEW:
                return newModeWordsOperator;
            case REVIME:
                return reviewModeWordsOperator;
            case MARKED:
                return markModeWordsOperator;
        }
        return null;
    }

    //*********************************************
    private TeacherType getTeacherType() {
        return myAppSetting.getTeacherType();
    }

    private LearningMode getLearningMode() {
        return LearningMode.getLearningMode(myAppSetting.getLearningMode());
    }
}
