package com.jeramtough.repeatwords2.component.dictionary;

import android.util.SparseArray;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 04 Friday 00:18.
 */
@JtComponent
public class WordsPool
{
	private SparseArray<Word> words;
	
	@IocAutowire
	public WordsPool()
	{
		words = new SparseArray<>();
	}
	
	public synchronized void addWord(Word word)
	{
		words.put(word.getId(), word);
	}
	
	public Word getWord(int wordId)
	{
		return words.get(wordId);
	}
	
	public void addWords(List<Word> words)
	{
		for (Word word : words)
		{
			this.words.put(word.getId(), word);
		}
	}
	
	public List<Word> getWordList()
	{
		ArrayList<Word> list = new ArrayList();
		for (int i = 0; i < this.words.size(); i++)
		{
			int key = words.keyAt(i);
			Word word = words.get(key);
			list.add(word);
		}
		return list;
	}
	
	public Word[] getWordArray()
	{
		Word[] array = new Word[words.size()];
		for (int i = 0; i < this.words.size(); i++)
		{
			int key = words.keyAt(i);
			Word word = words.get(key);
			array[i] = word;
		}
		return array;
	}
	
	public void clear()
	{
		words.clear();
	}
}
