package com.jeramtough.repeatwords2.business;

import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;

/**
 * @author 11718
 */
public interface MainNavigationService {

    TeacherType getTeacherType();

    LearningMode getLearningMode();
}
