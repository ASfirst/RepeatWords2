package com.jeramtough.repeatwords2.component.app;

import android.os.Environment;

import java.io.File;

/**
 * @author 11718
 * on 2018  May 06 Sunday 18:07.
 */
public class AppConstants
{
	public static final String APP_DIRECTORY_PATH =
			Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
					"@MyFolder";
}
