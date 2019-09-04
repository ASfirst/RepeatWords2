package com.jeramtough.repeatwords2.component.ui.blackboard;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.learning.school.teacher.TeacherType;

/**
 * Created on 2019-09-04 01:14
 * by @author JeramTough
 */
@JtComponent
public class BlackBoardProvider {

    private MyAppSetting myAppSetting;

    private BaiduVoiceReader reader;

    private BlackboardOfLearningTeacher blackboardOfLearningTeacher;
    private BlackboardOfSpeakingTeacher blackboardOfSpeakingTeacher;
    private BlackboardOfWritingTeacher blackboardOfWritingTeacher;


    @IocAutowire
    public BlackBoardProvider(MyAppSetting myAppSetting,
                              BaiduVoiceReader reader) {
        this.myAppSetting = myAppSetting;
        this.reader = reader;
    }

    public Blackboard get() {
        TeacherType currentTeacherType = TeacherType.getLearningMode(
                myAppSetting.getLearningMode());

        switch (currentTeacherType) {
            case LISTENING_TEACHER:
                if (blackboardOfLearningTeacher == null) {
                    blackboardOfLearningTeacher = new BlackboardOfLearningTeacher(reader);
                }
                return blackboardOfLearningTeacher;
            case SPEAKING_TEACHER:
                if (blackboardOfSpeakingTeacher == null) {
                    blackboardOfSpeakingTeacher = new BlackboardOfSpeakingTeacher(reader);
                }
                return blackboardOfSpeakingTeacher;
            case WRITING_TEACHER:
                if (blackboardOfSpeakingTeacher == null) {
                    blackboardOfWritingTeacher = new BlackboardOfWritingTeacher(reader);
                }
                return blackboardOfWritingTeacher;
            case READING_TEACHER:
                break;
            default:
        }
        return null;
    }


    public TeacherType getTeacherType() {
        return myAppSetting.getTeacherType();
    }
}
