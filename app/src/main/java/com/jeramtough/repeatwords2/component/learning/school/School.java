package com.jeramtough.repeatwords2.component.learning.school;

import com.jeramtough.repeatwords2.component.learning.school.teacher.Teacher1;

/**
 * Created on 2019-09-03 18:38
 * by @author JeramTough
 */
public interface School {

    Teacher1 getCurrentTeacher();

    Teacher1 getNewWordTeacher();

    Teacher1 getMarkWordTeacher();

    Teacher1 getReviewWordTeacher();
}
