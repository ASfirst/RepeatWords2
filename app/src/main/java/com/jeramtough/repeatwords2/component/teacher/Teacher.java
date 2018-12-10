package com.jeramtough.repeatwords2.component.teacher;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordWithIsLearnedAtLeastTwiceToday;

import java.util.List;

interface Teacher {

    Word getNextNeedLearningWord();

    Word[] getAllRandomNeedLearningWords();

    Word[] getRandomNeedLearningWords(int size);

    WordWithIsLearnedAtLeastTwiceToday[]
    getAllRandomNeedLearningWordsWithIsLearedTodayAtLeastTwice(
            List<Integer> todaysHaveLearnedWordsAtLeastTwice);

}
