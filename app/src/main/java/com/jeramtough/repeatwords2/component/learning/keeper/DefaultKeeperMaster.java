package com.jeramtough.repeatwords2.component.learning.keeper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;

/**
 *
 * <p>
 * Created on 2019-09-03 18:34
 * by @author JeramTough
 */
@JtComponent
public class DefaultKeeperMaster implements KeeperMaster {

    private MyAppSetting myAppSetting;
    private NewWordRecordKeeper newWordTeacher;
    private ReviewWordRecordKeeper reviewWordTeacher;
    private MarkWordRecordKeeper markWordTeacher;

    @IocAutowire
    public DefaultKeeperMaster(MyAppSetting myAppSetting,
                               NewWordRecordKeeper newWordTeacher,
                               ReviewWordRecordKeeper reviewWordTeacher,
                               MarkWordRecordKeeper markWordTeacher) {
        this.myAppSetting = myAppSetting;
        this.newWordTeacher = newWordTeacher;
        this.reviewWordTeacher = reviewWordTeacher;
        this.markWordTeacher = markWordTeacher;
    }

    @Override
    public RecordKeeper getCurrentRecordKeeper() {
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
    public RecordKeeper getNewWordRecordKeeper() {
        return newWordTeacher;
    }

    @Override
    public RecordKeeper getMarkWordRecordKeeper() {
        return markWordTeacher;
    }

    @Override
    public RecordKeeper getReviewWordRecordKeeper() {
        return reviewWordTeacher;
    }
}
