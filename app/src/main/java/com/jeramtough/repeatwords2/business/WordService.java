package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.repeatwords2.bean.word.Word;

public interface WordService {

    void queryWordDetail(Word word, BusinessCaller businessCaller);
}
