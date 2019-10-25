package com.jeramtough.repeatwords2.component.listener;

import java.io.Serializable;

/**
 * Created by 11718
 * on 2017  September 22 Friday 14:58.
 */

public interface AppControllerListener extends Serializable
{
	void theUpOnClick();
	
	void theUpOnLongClick();
	
	void theDownOnClick();
	
	void theDownOnLongClick();
	
	void theUpOnTooLongClick();
	
	void theDownOnTooLongClick();
}
