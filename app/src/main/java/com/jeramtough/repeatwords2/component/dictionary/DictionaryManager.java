package com.jeramtough.repeatwords2.component.dictionary;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtandroid.java.ExtractedFile;
import com.jeramtough.jtandroid.java.ExtractedZip;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.oedslib.sqlite.AbstractSqlLiteHelper;
import com.jeramtough.repeatwords2.component.app.AppConstants;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class
 *
 * @author 11718
 */
@JtComponent
public class DictionaryManager {

    private static final String DICTIONARY_ZIP_FILE_NAME = "en-ch-dictionary.zip";
    private static final Pattern DICTIONARY_DB_FILE_NAME_PATTERN =
            Pattern.compile(
                    "en-ch-dictionary-v(.*?)+.db");

    private String recordFileName = "dictionary.json";
    private String recordBackupZipFileName = "dictionary.zip";

    private Context context;
    private Directory backupDirectory;
    private Directory dbDirectory;

    MySqlLiteHelper mySqlLiteHelper;

    private File dictionaryDbFile;
    private File recordFile;
    private File recordBackupZipFile;

    @IocAutowire
    public DictionaryManager(Context context) {
        this.context = context;
        initResources();
    }

    void initResources() {

        mySqlLiteHelper = new MySqlLiteHelper();

        backupDirectory = new Directory(
                AppConstants.APP_DIRECTORY_PATH + File.separator + "RepeatWords" + File.separator + "backup");
        dbDirectory = new Directory(
                AppConstants.APP_DIRECTORY_PATH + File.separator + "RepeatWords" + File.separator + "database");

        recordFile =
                new File(backupDirectory.getAbsoluteFile() + File.separator + recordFileName);
        recordBackupZipFile = new File(
                backupDirectory.getAbsoluteFile() + File.separator + recordBackupZipFileName);
    }

    public boolean initDictionary() {
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }

        if (!dbDirectory.exists()) {
            dbDirectory.mkdirs();
        }

        findDictionaryDbFile();

        //copy dictionary db file to sd card
        if (dictionaryDbFile == null) {
            File tempDictionaryDbFile = null;
            try {
                InputStream inputStream = context.getAssets().open(DICTIONARY_ZIP_FILE_NAME);
                tempDictionaryDbFile =
                        new File(
                                dbDirectory.getAbsolutePath() + File.separator + DICTIONARY_ZIP_FILE_NAME);
                FileOutputStream fileOutputStream = new FileOutputStream(tempDictionaryDbFile);
                IOUtils.copy(inputStream, fileOutputStream);
                fileOutputStream.close();

                ExtractedFile extractedFile = new ExtractedZip(tempDictionaryDbFile);
                extractedFile.extract(dbDirectory.getAbsolutePath());

                findDictionaryDbFile();
                if (dictionaryDbFile == null) {
                    return false;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (tempDictionaryDbFile != null) {
                    tempDictionaryDbFile.delete();
                }
            }
        }

        //connect dicionary db
        mySqlLiteHelper.connectDatabase();
        return true;
    }

    private void findDictionaryDbFile() {
        for (File file : dbDirectory.listFiles()) {
            String fileName = file.getName();
            Matcher matcher = DICTIONARY_DB_FILE_NAME_PATTERN.matcher(fileName);
            if (matcher.matches()) {
                dictionaryDbFile = file;
                break;
            }
        }
    }

    public Dictionary getDictionaryFromAssets() {
        try {
            InputStream inputStream = context.getAssets().open("dictionary.json");
            String jsonStr = IOUtils.toString(inputStream, "UTF-8");
            Dictionary dictionary = JSON.parseObject(jsonStr, Dictionary.class);
            return dictionary;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean backup(Dictionary dictionary) {
        if (backupDirectory.exists()) {

            if (recordFile.exists()) {
                recordBackupZipFile.delete();
                recordBackupZipFile.getAbsolutePath();
            }

            try {
                byte[] bytes = JSON.toJSONBytes(dictionary);
                FileOutputStream fileOutputStream = new FileOutputStream(recordFile);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                return true;
            }
            catch (IOException e) {
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
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public MySqlLiteHelper getMySqlLiteHelper() {
        return mySqlLiteHelper;
    }

    public File getDictionaryDbFile() {
        return dictionaryDbFile;
    }

    public DictionaryMapper getDictionaryMapper() {
        return mySqlLiteHelper.getDictionaryMapper();
    }
//{{{{{{{{{{{{{{}}}}}}}}}}}}}}}

    class MySqlLiteHelper extends AbstractSqlLiteHelper {
        @Override
        public String loadUrl() {
            return dictionaryDbFile.getAbsolutePath();
        }

        @Override
        public void connectDatabase() {
            super.connectDatabase();
        }

        @Override
        public void closeConnection() {
            super.closeConnection();
        }
    }

}
