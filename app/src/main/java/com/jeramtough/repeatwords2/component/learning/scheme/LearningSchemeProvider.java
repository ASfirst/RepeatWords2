package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;

/**
 * Created on 2019-09-05 23:49
 * by @author JeramTough
 */
@JtComponent
public class LearningSchemeProvider {

    private ListeningLearningScheme listeningLearningScheme;
    private SpeakingLearningScheme speakingLearningScheme;
    private WritingLearningScheme writingLearningScheme;
    private ReadingLearningScheme readingLearningScheme;
    private MyAppSetting myAppSetting;

    @IocAutowire
    public LearningSchemeProvider(
            ListeningLearningScheme listeningLearningScheme,
            SpeakingLearningScheme speakingLearningScheme,
            WritingLearningScheme writingLearningScheme,
            ReadingLearningScheme readingLearningScheme,
            MyAppSetting myAppSetting) {
        this.listeningLearningScheme = listeningLearningScheme;
        this.speakingLearningScheme = speakingLearningScheme;
        this.writingLearningScheme = writingLearningScheme;
        this.readingLearningScheme = readingLearningScheme;
        this.myAppSetting = myAppSetting;
    }

    public LearningScheme getLearningScheme(TeacherType teacherType) {
        switch (teacherType) {
            case LISTENING_TEACHER:
                return listeningLearningScheme;
            case SPEAKING_TEACHER:
                return speakingLearningScheme;
            case WRITING_TEACHER:
                return writingLearningScheme;
            case READING_TEACHER:
                return readingLearningScheme;
            default:
        }
        return listeningLearningScheme;
    }

    public LearningScheme getCurrentLearningScheme() {
        return getLearningScheme(myAppSetting.getTeacherType());
    }

}
