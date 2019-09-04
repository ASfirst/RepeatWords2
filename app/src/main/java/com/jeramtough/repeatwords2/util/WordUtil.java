package com.jeramtough.repeatwords2.util;

import com.alibaba.fastjson.JSON;
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

    public static String formatWordDto(WordDto wordDto) {
        return JSON.toJSONString(wordDto, true);
    }

    public static String processChExplain(String chExplain) {
        chExplain = chExplain.replace("n.", "名词.");
        chExplain = chExplain.replace("\\\\n.", "");
        chExplain = chExplain.replace("adv.", "副词.");
        chExplain = chExplain.replace("adj.", "形容词.");
        chExplain = chExplain.replace("vi.", "不及物动词.");
        chExplain = chExplain.replace("vt.", "及物动词.");
        return chExplain;
    }

    /**
     * 缩写单词
     */
    public static String abbreviateChinese(String chinese, int maxLength) {
        if (chinese.length() > maxLength) {
            return chinese.substring(0, maxLength - 1);
        }
        return chinese;
    }

    public static String abbreviateChinese(String chinese) {
        return abbreviateChinese(chinese, 7);
    }
}
