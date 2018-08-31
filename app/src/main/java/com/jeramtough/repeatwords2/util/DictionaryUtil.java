package com.jeramtough.repeatwords2.util;

import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.dictionary.WordsPool;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 05 Saturday 14:55.
 */
public class DictionaryUtil
{
	public static Word[] getWordsByIds(List<Integer> ids, WordsPool wordsPool)
	{
		Word[] words = new Word[ids.size()];
		for (int i = 0; i < ids.size(); i++)
		{
			int id = ids.get(i);
			words[i] = wordsPool.getWord(id);
		}
		return words;
	}
}
