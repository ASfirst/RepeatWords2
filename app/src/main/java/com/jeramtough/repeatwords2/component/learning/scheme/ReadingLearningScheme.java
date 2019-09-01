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
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordsMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordsMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

/**
 * Created on 2019-08-31 22:20
 * by @author JeramTough
 */
@JtComponent(pattern = JtBeanPattern.Prototype)
public class ReadingLearningScheme extends BaseLearningScheme implements LearningScheme {

    @IocAutowire
    public ReadingLearningScheme(
            DictionaryMapper dictionaryMapper,
            @InjectComponent(impl = DefaultOperateWordsMapperProvider.class)
                    OperateWordsMapperProvider operateWordsMapperProvider) {

        super(dictionaryMapper, operateWordsMapperProvider);

    }

    @Override
    public void initScheme(CommonCallback<LargeWord> callback) {
        LargeWord[] largeWords;
        largeWords = super.dictionaryMapper.selectListByWordTagOrderByFrq(WordTag.CET4);
        OperateWordsMapper operateWordsMapper =
                super.operateWordsMapperProvider.getOperateWordsMapper(
                        TeacherType.READING_TEACHER,
                        WordCondition.SHALL_LEARNING);
        for (LargeWord largeWord : largeWords) {
            WordRecord wordRecord = new WordRecord();
            wordRecord.setTime(DateTimeUtil.getDateTime());
            wordRecord.setLevel(null);
            wordRecord.setWordId(largeWord.getFdId());
            callback.callback(largeWord);
            operateWordsMapper.addWordRecord(wordRecord);
        }
    }
}
