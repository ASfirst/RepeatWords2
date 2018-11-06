package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.repeatwords2.dao.mapper.DesertedLearningMapper;
import com.jeramtough.repeatwords2.dao.mapper.HaveGraspedMapper;
import com.jeramtough.repeatwords2.dao.mapper.HaveLearnedTodayMapper;
import com.jeramtough.repeatwords2.dao.mapper.MarkedMapper;
import com.jeramtough.repeatwords2.dao.mapper.ShallLearningMapper;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:52.
 */
public interface OperateWordsMapperFactory {
    HaveGraspedMapper getHaveGraspedMapper();

    ShallLearningMapper getShallLearningMapper();

    MarkedMapper getMarkedMapper();

    DesertedLearningMapper getDesertedLearningMapper();

    HaveLearnedTodayMapper getHaveLearnedTodayMapper();
}
