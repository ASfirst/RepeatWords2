package com.jeramtough.repeatwords2.util;

import com.jeramtough.jtcomponent.utils.DateTimeUtil;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;

/**
 * Created on 2019-09-04 11:45
 * by @author JeramTough
 */
public class WordUtil {

    public static WordRecord wordDtoToWordRecord(WordDto wordDto) {
        WordRecord wordRecord = new WordRecord(null, wordDto.getFdId(),
                DateTimeUtil.getCurrentDateTime(), null);
        return wordRecord;
    }
}
