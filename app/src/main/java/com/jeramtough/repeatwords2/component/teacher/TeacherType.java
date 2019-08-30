package com.jeramtough.repeatwords2.component.teacher;

/**
 * @author 11718
 * on 2018  May 07 Monday 14:57.
 */
public enum TeacherType {
    /**
     *
     */
    LISTENING_TEACHER(0),

    /**
     *
     */
    SPEAKING_TEACHER(1),

    /**
     *
     */
    WRITING_TEACHER(2),

    /**
     *
     */
    READ_TEACHER(3);

    private int teacherTypeId;

    TeacherType(int teacherTypeId) {
        this.teacherTypeId = teacherTypeId;
    }

    public int getTeacherTypeId() {
        return teacherTypeId;
    }

    public static TeacherType getLearningMode(int learningModeId) {
        switch (learningModeId) {
            case 0:
                return LISTENING_TEACHER;
            case 1:
                return SPEAKING_TEACHER;
            case 2:
                return WRITING_TEACHER;
        }
        return LISTENING_TEACHER;
    }
}
