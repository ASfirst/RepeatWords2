package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.utils.DateTimeUtil;
import com.jeramtough.oedslib.comparator.TagPositionComparator;
import com.jeramtough.oedslib.entity.LargeWord;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.oedslib.tag.WordTag;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordRecordMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordRecordMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordRecordMapperProvider;

import java.util.Arrays;

/**
 * Created on 2019-08-31 22:20
 * by @author JeramTough
 */
@JtComponent(pattern = JtBeanPattern.Prototype)
public class ListeningLearningScheme extends BaseLearningScheme implements LearningScheme {

    @IocAutowire
    public ListeningLearningScheme(
            DictionaryMapper dictionaryMapper,
            @InjectComponent(impl = DefaultOperateWordRecordMapperProvider.class)
                    OperateWordRecordMapperProvider operateWordRecordMapperProvider) {

        super(dictionaryMapper, operateWordRecordMapperProvider);

    }

    @Override
    TeacherType loadTeacherType() {
        return TeacherType.LISTENING_TEACHER;
    }

    @Override
    public void initScheme(CommonCallback<LargeWord> callback) {
        LargeWord[] largeWords;
        largeWords = super.dictionaryMapper.selectListByTag(WordTag.NCE);
        Arrays.sort(largeWords, new TagPositionComparator(WordTag.NCE));
        OperateWordRecordMapper operateWordRecordMapper =
                super.operateWordRecordMapperProvider.getOperateWordsMapper(
                        super.teacherType,
                        WordCondition.SHALL_LEARNING);
        for (LargeWord largeWord : largeWords) {
            WordRecord wordRecord = new WordRecord();
            wordRecord.setTime(DateTimeUtil.getCurrentDateTime());
            wordRecord.setLevel(null);
            wordRecord.setWordId(largeWord.getFdId());
            callback.callback(largeWord);
            operateWordRecordMapper.addWordRecord(wordRecord);
        }
    }


}
