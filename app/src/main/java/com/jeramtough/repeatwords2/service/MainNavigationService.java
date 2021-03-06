package com.jeramtough.repeatwords2.service;

import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;

/**
 * @author 11718
 */
public interface MainNavigationService {

    TeacherType getTeacherType();

    LearningMode getLearningMode();
}
