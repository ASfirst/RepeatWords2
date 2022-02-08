package com.jeramtough.repeatwords2.component.dictionary;

import android.content.Context;

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

    private Context context;
    private Directory backupDirectory;
    private Directory dbDirectory;

    MySqlLiteHelper mySqlLiteHelper;

    private File dictionaryDbFile;

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
                return false;
            }
            finally {
                if (tempDictionaryDbFile != null) {
                    tempDictionaryDbFile.delete();
                }
            }
        }

        //connect dicionary db
        try {
            mySqlLiteHelper.connectDatabase();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
