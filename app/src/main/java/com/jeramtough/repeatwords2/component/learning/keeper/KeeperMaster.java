package com.jeramtough.repeatwords2.component.learning.keeper;

/**
 * Created on 2019-09-03 18:38
 * by @author JeramTough
 */
public interface KeeperMaster {

    RecordKeeper getCurrentRecordKeeper();

    RecordKeeper getNewWordRecordKeeper();

    RecordKeeper getMarkWordRecordKeeper();

    RecordKeeper getReviewWordRecordKeeper();
}
