package com.jeramtough.repeatwords2.controller.service;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import com.jeramtough.repeatwords2.component.listener.AppControllerListener;

/**
 * @author 11718
 */
public class ControllerService extends AccessibilityService
{
	private static AppControllerListener appControllerListener;
	private final long beShortTime = 250;
	private final long beLongTime = 650;
	private final long beTooLongTime = 4000;
	private CountTimer countTimer;
	
	public ControllerService()
	{
		countTimer = new CountTimer();
	}
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent)
	{
		
	}
	
	@Override
	public void onInterrupt()
	{
		
	}
	
	public static void setAppControllerListener(AppControllerListener appControllerListener)
	{
		ControllerService.appControllerListener = appControllerListener;
	}
	
	@Override
	protected boolean onKeyEvent(KeyEvent event)
	{
		boolean isFiltrate = filtrateTheEvenKey(event);
		
		if (event.getAction() == KeyEvent.ACTION_DOWN)
		{
			countTimer.start();
		}
		else
		{
			long timeOfInterval = countTimer.stop();
			
			if (appControllerListener != null)
			{
				switch (event.getKeyCode())
				{
					case KeyEvent.KEYCODE_VOLUME_UP:
						if (beTooLongTime>timeOfInterval&&timeOfInterval > beLongTime)
						{
							appControllerListener.theUpOnLongClick();
						}
						else if (timeOfInterval>beTooLongTime)
						{
							appControllerListener.theUpOnTooLongClick();
						}
						else if (timeOfInterval<=beShortTime)
						{
							appControllerListener.theUpOnClick();
						}
						break;
					case KeyEvent.KEYCODE_VOLUME_DOWN:
						if (beTooLongTime>timeOfInterval&&timeOfInterval > beLongTime)
						{
							
							appControllerListener.theDownOnLongClick();
						}
						else if (timeOfInterval>beTooLongTime)
						{
							appControllerListener.theDownOnTooLongClick();
						}
						else if (timeOfInterval<=beShortTime)
						{
							appControllerListener.theDownOnClick();
						}
						break;
				}
			}
		}
		return isFiltrate;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	
	//****************************
	private boolean filtrateTheEvenKey(KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP ||
				event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
		{
			return true;
		}
		return false;
	}
	
	//{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}
	public class CountTimer
	{
		private long startedTime;
		
		public void start()
		{
			startedTime = System.currentTimeMillis();
		}
		
		public long stop()
		{
			return System.currentTimeMillis() - startedTime;
			//
		}
	}
	
}
