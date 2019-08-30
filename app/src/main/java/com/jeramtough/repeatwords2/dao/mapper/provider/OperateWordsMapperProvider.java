package com.jeramtough.repeatwords2.dao.mapper.provider;

import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.condition.WordCondition;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordsMapper;

/**
 * Created on 2019-08-30 02:43
 * by @author JeramTough
 */
@JtComponent
public interface OperateWordsMapperProvider {

    OperateWordsMapper getCurrentOperateWordsMapper();

    OperateWordsMapper getCurrentOperateWordsMapper(WordCondition wordCondition);

    OperateWordsMapper getOperateWordsMapper(TeacherType teacherType,
                                             WordCondition wordCondition);

}