package com.jeramtough.repeatwords2.component.baidu;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog.with.WithLogger;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author 11718
 */
@JtComponent
public class VoiceResources implements WithLogger {
    private Context context;
    private String textModelFilePath;
    private String speechMaleModelFilePath;
    private String speechFemaleModelFilePath;
    private String appVoiceDataPath;


    @IocAutowire
    public VoiceResources(Context context) {
        this.context = context;
        appVoiceDataPath = context.getFilesDir().getPath() + "/RepeatWords/voiceData";
    }

    public void initResources() throws IOException {
        File appDirFile = new File(appVoiceDataPath);
        if (!new File(getTextModelFilePath()).exists()) {
            boolean isCreatedSuccessful = appDirFile.mkdirs();
            getLogger().info("create the RepeatWords foldr is " + isCreatedSuccessful);

            copyVoiceResourceFromTheAssetsToAppDir(context.getAssets()
                            .open(BaiduVoiceConstants.TEXT_MODEL_FILE_FULL_PATH_NAME),
                    getTextModelFilePath());

            copyVoiceResourceFromTheAssetsToAppDir(context.getAssets()
                            .open(BaiduVoiceConstants.SPEECH_MALE_MODEL_FILE_FULL_PATH_NAME),
                    getSpeechMaleModelFilePath());

            copyVoiceResourceFromTheAssetsToAppDir(context.getAssets()
                            .open(BaiduVoiceConstants.SPEECH_FEMALE_MODEL_FILE_FULL_PATH_NAME1),
                    getSpeechFemaleModelFilePath());


        }
    }

    public String getTextModelFilePath() {
        textModelFilePath =
                appVoiceDataPath + "/" + BaiduVoiceConstants.TEXT_MODEL_FILE_FULL_PATH_NAME;
        return textModelFilePath;
    }

    public String getSpeechMaleModelFilePath() {
        speechMaleModelFilePath = appVoiceDataPath + "/" +
                BaiduVoiceConstants.SPEECH_MALE_MODEL_FILE_FULL_PATH_NAME;
        return speechMaleModelFilePath;
    }

    public String getSpeechFemaleModelFilePath() {
        speechFemaleModelFilePath = appVoiceDataPath + "/" +
                BaiduVoiceConstants.SPEECH_FEMALE_MODEL_FILE_FULL_PATH_NAME1;
        return speechFemaleModelFilePath;
    }


    //*********************************************
    private void copyVoiceResourceFromTheAssetsToAppDir(InputStream inputStream,
                                                        String filePath) {
        try {
            File file = new File(filePath);
            getLogger().info("create new voice data is " + file.createNewFile());

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, fileOutputStream);

            fileOutputStream.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
