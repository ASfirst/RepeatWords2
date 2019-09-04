package com.jeramtough.repeatwords2.dao.mapper.factory;

import com.jeramtough.repeatwords2.dao.mapper.DesertedLearningMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.HaveGraspedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.HaveLearnedTodayMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.MarkedMapperWord;
import com.jeramtough.repeatwords2.dao.mapper.ShallLearningMapperWord;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:52.
 */
public interface OperateWordsMapperFactory {
    HaveGraspedMapperWord getHaveGraspedMapper();

    ShallLearningMapperWord getShallLearningMapper();

    MarkedMapperWord getMarkedMapper();

    DesertedLearningMapperWord getDesertedLearningMapper();

    HaveLearnedTodayMapperWord getHaveLearnedTodayMapper();
}
