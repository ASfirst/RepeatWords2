package com.jeramtough.repeatwords2.dao.mapper.provider;

import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;

/**
 * Created on 2019-08-30 02:43
 * by @author JeramTough
 */
public interface OperateWordRecordMapperProvider {

    OperateWordRecordMapper getCurrentOperateWordsMapper(WordCondition wordCondition);

    OperateWordRecordMapper getOperateWordsMapper(TeacherType teacherType,
                                                  WordCondition wordCondition);

}
