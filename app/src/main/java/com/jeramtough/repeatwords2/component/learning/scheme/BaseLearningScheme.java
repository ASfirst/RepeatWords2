package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;

/**
 * Created on 2019-09-01 14:17
 * by @author JeramTough
 */
abstract class BaseLearningScheme implements LearningScheme {

    DictionaryMapper dictionaryMapper;
    OperateWordRecordMapperProvider operateWordRecordMapperProvider;

    public BaseLearningScheme(DictionaryMapper dictionaryMapper,
                              OperateWordRecordMapperProvider operateWordRecordMapperProvider) {
        this.dictionaryMapper = dictionaryMapper;
        this.operateWordRecordMapperProvider = operateWordRecordMapperProvider;
    }


}
