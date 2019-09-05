package com.jeramtough.repeatwords2.component.learning.teacher;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordWithIsLearnedAtLeastTwiceToday;

import java.util.List;

/**
 * @author 11718
 */
public interface Teacher {

    Word getNextNeedLearningWord();

    Word[] getAllRandomNeedLearningWords();

    Word[] getRandomNeedLearningWords(int size);

    WordWithIsLearnedAtLeastTwiceToday[]
    getAllRandomNeedLearningWordsWithIsLearedTodayAtLeastTwice(
            List<Integer> todaysHaveLearnedWordsAtLeastTwice);

}
