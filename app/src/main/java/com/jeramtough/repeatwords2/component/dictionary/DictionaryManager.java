package com.jeramtough.repeatwords2.component.dictionary;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.bean.word.Dictionary;
import com.jeramtough.repeatwords2.component.app.AppConstants;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@JtComponent
public class DictionaryManager {

    private String recordFileName = "dictionary.json";

    private Context context;
    private Directory backupDirectory;
    private File recordFile;

    @IocAutowire
    public DictionaryManager(Context context) {
        this.context = context;

        backupDirectory = new Directory(
                AppConstants.APP_DIRECTORY_PATH + File.separator + "RepeatWords");
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }
        recordFile =
                new File(backupDirectory.getAbsoluteFile() + File.separator + recordFileName);
    }

    public Dictionary getDictionaryFromAssets() {
        try {
            InputStream inputStream = context.getAssets().open("dictionary.json");
            String jsonStr = IOUtils.toString(inputStream, "UTF-8");
            Dictionary dictionary = JSON.parseObject(jsonStr, Dictionary.class);
            return dictionary;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean backup(Dictionary dictionary) {
        if (backupDirectory.exists()) {
            try {
                byte[] bytes = JSON.toJSONBytes(dictionary);
                FileOutputStream fileOutputStream = new FileOutputStream(recordFile);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Dictionary recover() {
        if (recordFile.exists()) {
            try {
                String jsonStr = IOUtils.toString(new FileInputStream(recordFile), "UTF-8");
                Dictionary dictionary =
                        JSON.parseObject(jsonStr, Dictionary.class);
                return dictionary;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
