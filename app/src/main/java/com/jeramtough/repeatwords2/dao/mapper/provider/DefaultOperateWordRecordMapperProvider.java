package com.jeramtough.repeatwords2.dao.mapper.provider;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.school.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 01:03.
 */
@JtComponent
public class DefaultOperateWordRecordMapperProvider
        implements OperateWordRecordMapperProvider {
    private MyAppSetting myAppSetting;

    private Map<TeacherType, Map<WordCondition, OperateWordRecordMapper>> operateWordRecordMapperDoubleMap;
    private MyDatabaseHelper myDatabaseHelper;


    @IocAutowire
    public DefaultOperateWordRecordMapperProvider(
            MyAppSetting myAppSetting,
            MyDatabaseHelper myDatabaseHelper) {
        this.myAppSetting = myAppSetting;
        this.myDatabaseHelper = myDatabaseHelper;
        operateWordRecordMapperDoubleMap = new HashMap<>();

        for (TeacherType teacherType : TeacherType.values()) {
            Map<WordCondition, OperateWordRecordMapper> mapperMap = new HashMap<>();
            for (WordCondition wordCondition : WordCondition.values()) {
                String tableName =
                        teacherType.getTag().toLowerCase() + "_" + wordCondition.name().toLowerCase() + "_tb";
                OperateWordRecordMapper operateWordRecordMapper = new OperateWordRecordMapper(
                        myDatabaseHelper,
                        tableName);
                mapperMap.put(wordCondition, operateWordRecordMapper);
                operateWordRecordMapperDoubleMap.put(teacherType, mapperMap);
            }
        }
    }


   /* @Override
    public OperateWordRecordMapper getCurrentOperateWordsMapper() {
        int code = 0;
        //TODO:code?
        WordCondition wordCondition = WordCondition.getWordConditionByCode(code);
        return getOperateWordsMapper(myAppSetting.getTeacherType(), wordCondition);
    }*/

    @Override
    public OperateWordRecordMapper getCurrentOperateWordsMapper(WordCondition wordCondition) {
        return getOperateWordsMapper(myAppSetting.getTeacherType(), wordCondition);
    }


    @Override
    public OperateWordRecordMapper getOperateWordsMapper(TeacherType teacherType,
                                                         WordCondition wordCondition) {

        return operateWordRecordMapperDoubleMap.get(teacherType).get(wordCondition);
    }
}
