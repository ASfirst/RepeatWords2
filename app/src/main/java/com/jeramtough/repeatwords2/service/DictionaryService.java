package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.repeatwords2.bean.word.Word;

/**
 * @author 11718
 * on 2018  May 05 Saturday 14:05.
 */
public interface DictionaryService {
    void getDictionaryWords(BusinessCaller businessCaller);

    @Deprecated
    void addNewWordIntoDictionary(String ch, String en,
                                  BusinessCaller businessCaller);

    void addNewWordIntoDictionary(Word word,
                                  BusinessCaller businessCaller);

    void deleteWordFromDictionary(int wordId, BusinessCaller businessCaller);

    void modifyWordOfDictionary(Word word, BusinessCaller businessCaller);
}
