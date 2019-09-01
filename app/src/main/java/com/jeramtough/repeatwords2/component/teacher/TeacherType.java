package com.jeramtough.repeatwords2.component.teacher;

/**
 * @author 11718
 * on 2018  May 07 Monday 14:57.
 */
public enum TeacherType {
    /**
     *
     */
    LISTENING_TEACHER(0, "listen"),

    /**
     *
     */
    SPEAKING_TEACHER(1, "speak"),

    /**
     *
     */
    WRITING_TEACHER(2, "write"),

    /**
     *
     */
    READING_TEACHER(3, "read");

    private int teacherTypeId;
    private String tag;

    TeacherType(int teacherTypeId, String tag) {
        this.teacherTypeId = teacherTypeId;
        this.tag = tag;
    }

    public int getTeacherTypeId() {
        return teacherTypeId;
    }

    public String getTag() {
        return tag;
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
