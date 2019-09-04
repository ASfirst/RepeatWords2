package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.oedslib.entity.LargeWord;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.oedslib.tag.WordTag;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.school.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

/**
 * Created on 2019-08-31 22:20
 * by @author JeramTough
 */
@JtComponent(pattern = JtBeanPattern.Prototype)
public class WritingLearningScheme extends BaseLearningScheme implements LearningScheme {

    @IocAutowire
    public WritingLearningScheme(
            DictionaryMapper dictionaryMapper,
            @InjectComponent(impl = DefaultOperateWordRecordMapperProvider.class)
                    OperateWordRecordMapperProvider operateWordRecordMapperProvider) {

        super(dictionaryMapper, operateWordRecordMapperProvider);

    }

    @Override
    public void initScheme(CommonCallback<LargeWord> callback) {
        LargeWord[] largeWords;
        largeWords = super.dictionaryMapper.selectListByWordTagOrderByFrq(WordTag.ZK);
        OperateWordRecordMapper operateWordRecordMapper =
                super.operateWordRecordMapperProvider.getOperateWordsMapper(
                        TeacherType.WRITING_TEACHER,
                        WordCondition.SHALL_LEARNING);
        for (int i = 0; i < largeWords.length; i++) {
            LargeWord largeWord = largeWords[i];
            WordRecord wordRecord = new WordRecord();
            wordRecord.setTime(DateTimeUtil.getDateTime());
            wordRecord.setLevel(null);
            wordRecord.setWordId(largeWord.getFdId());
            callback.callback(largeWord);
            operateWordRecordMapper.addWordRecord(wordRecord);
        }
    }
}
