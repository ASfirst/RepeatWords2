package com.jeramtough.repeatwords2.component.learning.keeper;

/**
 *
 * 负责提供学习记录信息保持对象
 *
 * Created on 2019-09-03 18:38
 * by @author JeramTough
 */
public interface KeeperMaster {

    RecordKeeper getCurrentRecordKeeper();

    RecordKeeper getNewWordRecordKeeper();

    RecordKeeper getMarkWordRecordKeeper();

    RecordKeeper getReviewWordRecordKeeper();
}
