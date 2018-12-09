package com.jeramtough.repeatwords2.component.learningmode;

import com.jeramtough.repeatwords2.bean.word.WordRecord;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 08 Tuesday 00:06.
 */
public interface WordsOperator
{
	void removeWordFromList(int wordId);
	
	/**
	 * get ids of word of needing to learn
	 * @return ids
	 */
	List<Integer> getWordIdsOfNeeding(int size);

	void learnWordToday(WordRecord wordRecord);
	
}
