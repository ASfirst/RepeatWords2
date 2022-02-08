package com.jeramtough.repeatwords2.bean.word;

import java.io.Serializable;

/**
 * @author 11718
 * on 2018  May 03 Thursday 19:01.
 */
public class Word implements Serializable
{
	private int id;
	private String ch;
	private String en;
	private String phonetic;

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getCh()
	{
		return ch;
	}
	
	public void setCh(String ch)
	{
		this.ch = ch;
	}
	
	public String getEn()
	{
		return en;
	}
	
	public void setEn(String en)
	{
		this.en = en;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	@Override
	public String toString() {
		return "Word{" +
				"id=" + id +
				", ch='" + ch + '\'' +
				", en='" + en + '\'' +
				", phonetic='" + phonetic + '\'' +
				'}';
	}
}
