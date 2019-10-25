package com.jeramtough.repeatwords2.component.dictionary;

import com.jeramtough.oedslib.entity.LargeWord;
import com.jeramtough.oedslib.tag.WordTag;
import com.jeramtough.repeatwords2.bean.word.Word;

import java.util.List;

public class Dictionary
{

	private WordTag wordTag;

	private List<LargeWord>largeWordList;

	private List<Word> words;

	public Dictionary() {
	}

	public Dictionary(WordTag wordTag) {
		this.wordTag = wordTag;
	}

	public List<Word> getWords()
	{
		return words;
	}
	
	public void setWords(List<Word> words)
	{
		this.words = words;
	}

	public WordTag getWordTag() {
		return wordTag;
	}

	public void setWordTag(WordTag wordTag) {
		this.wordTag = wordTag;
	}

	public List<LargeWord> getLargeWordList() {
		return largeWordList;
	}

	public void setLargeWordList(List<LargeWord> largeWordList) {
		this.largeWordList = largeWordList;
	}
}
