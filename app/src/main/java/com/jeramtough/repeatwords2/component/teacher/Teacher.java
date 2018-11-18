package com.jeramtough.repeatwords2.component.teacher;

import com.jeramtough.repeatwords2.bean.word.Word;

interface Teacher {

    Word getNextNeedLearningWord();

    Word[] getAllRandomNeedLearningWords();

    Word[] getRandomNeedLearningWords(int size);

}
