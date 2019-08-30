package com.jeramtough.repeatwords2.service;

import com.jeramtough.jtandroid.business.BusinessCaller;

public interface ListService {
    void getHaveGraspedWords(BusinessCaller businessCaller);

    void getShallLearningWords(BusinessCaller businessCaller);

    void getMarkedWords(BusinessCaller businessCaller);

    void getDesertedWords(BusinessCaller businessCaller);

    void getTodaysHaveLearnedWords(BusinessCaller businessCaller);

    void removeWordFromTodaysHaveLearnedList(int wordId, BusinessCaller businessCaller);

    void removeWordFromHaveGraspedList(int wordId, BusinessCaller businessCaller);

    void removeWordFromShallLearningList(int wordId, BusinessCaller businessCaller);

    void removeWordFromMarkedList(int wordId, BusinessCaller businessCaller);

    void removeWordFromDesertedList(int wordId, BusinessCaller businessCaller);
}