package com.jeramtough.repeatwords2.dao.mapper.provider;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.condition.WordCondition;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordsMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 01:03.
 */
@JtComponent
public class DefaultOperateWordsMapperProvider implements OperateWordsMapperProvider {
    private MyAppSetting myAppSetting;

    private Map<TeacherType, Map<WordCondition, OperateWordsMapper>> operateWordsMapperDoubleMap;
    private MyDatabaseHelper myDatabaseHelper;

    @IocAutowire
    public DefaultOperateWordsMapperProvider(
            MyAppSetting myAppSetting,
            MyDatabaseHelper myDatabaseHelper) {
        this.myAppSetting = myAppSetting;
        this.myDatabaseHelper = myDatabaseHelper;
        operateWordsMapperDoubleMap = new HashMap<>();

        for (TeacherType teacherType : TeacherType.values()) {
            for (WordCondition wordCondition : WordCondition.values()) {
                String tableName =
                        teacherType.name().toLowerCase() + "_" + wordCondition.name().toLowerCase() + "_tb";
                OperateWordsMapper operateWordsMapper = new OperateWordsMapper(
                        myDatabaseHelper,
                        tableName);
                Map<WordCondition, OperateWordsMapper> mapperMap = new HashMap<>();
                mapperMap.put(wordCondition, operateWordsMapper);
                operateWordsMapperDoubleMap.put(teacherType, mapperMap);
            }
        }
    }


    @Override
    public OperateWordsMapper getCurrentOperateWordsMapper() {
        int code = 0;
        //TODO:code?
        WordCondition wordCondition = WordCondition.getWordConditionByCode(code);
        return getOperateWordsMapper(myAppSetting.getTeacherType(), wordCondition);
    }

    @Override
    public OperateWordsMapper getCurrentOperateWordsMapper(WordCondition wordCondition) {
        return getOperateWordsMapper(myAppSetting.getTeacherType(), wordCondition);
    }


    @Override
    public OperateWordsMapper getOperateWordsMapper(TeacherType teacherType,
                                                    WordCondition wordCondition) {

        return operateWordsMapperDoubleMap.get(teacherType).get(wordCondition);
    }
}
