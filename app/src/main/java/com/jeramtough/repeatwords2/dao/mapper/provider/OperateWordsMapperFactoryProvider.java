package com.jeramtough.repeatwords2.dao.mapper.provider;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.dao.mapper.factory.ListeningTeacherOperateWordsMapperFactory;
import com.jeramtough.repeatwords2.dao.mapper.factory.OperateWordsMapperFactory;
import com.jeramtough.repeatwords2.dao.mapper.factory.SpeakingTeacherOperateWordsMapperFactory;
import com.jeramtough.repeatwords2.dao.mapper.factory.WritingTeacherOperateWordsMapperFactory;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 01:03.
 */
@JtComponent
public class OperateWordsMapperFactoryProvider {
    private MyAppSetting myAppSetting;
    private ListeningTeacherOperateWordsMapperFactory
            listeningTeacherOperateWordsMapperFactory;
    private SpeakingTeacherOperateWordsMapperFactory speakingTeacherOperateWordsMapperFactory;
    private WritingTeacherOperateWordsMapperFactory writingTeacherOperateWordsMapperFactory;

    @IocAutowire
    public OperateWordsMapperFactoryProvider(MyAppSetting myAppSetting,
                                             ListeningTeacherOperateWordsMapperFactory listeningTeacherOperateWordsMapperFactory,
                                             SpeakingTeacherOperateWordsMapperFactory speakingTeacherOperateWordsMapperFactory, WritingTeacherOperateWordsMapperFactory writingTeacherOperateWordsMapperFactory) {
        this.myAppSetting = myAppSetting;
        this.listeningTeacherOperateWordsMapperFactory =
                listeningTeacherOperateWordsMapperFactory;
        this.speakingTeacherOperateWordsMapperFactory =
                speakingTeacherOperateWordsMapperFactory;
        this.writingTeacherOperateWordsMapperFactory = writingTeacherOperateWordsMapperFactory;
    }

    public OperateWordsMapperFactory getOperateWordsMapperFactory() {
        switch (myAppSetting.getTeacherType()) {
            case LISTENING_TEACHER:
                return listeningTeacherOperateWordsMapperFactory;
            case SPEAKING_TEACHER:
                return speakingTeacherOperateWordsMapperFactory;
            case WRITING_TEACHER:
                return writingTeacherOperateWordsMapperFactory;
        }
        return null;
    }

    public ListeningTeacherOperateWordsMapperFactory getListeningTeacherOperateWordsMapperFactory() {
        return listeningTeacherOperateWordsMapperFactory;
    }

    public SpeakingTeacherOperateWordsMapperFactory getSpeakingTeacherOperateWordsMapperFactory() {
        return speakingTeacherOperateWordsMapperFactory;
    }

    public WritingTeacherOperateWordsMapperFactory getWritingTeacherOperateWordsMapperFactory() {
        return writingTeacherOperateWordsMapperFactory;
    }
}
