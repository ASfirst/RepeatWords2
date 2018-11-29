package com.jeramtough.repeatwords2.component.baidu;

import android.content.Context;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog.with.WithJtLogger;
import com.jeramtough.jtutil.WordUtil;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 11718
 * on 2017  九月 17 星期日 20:46.
 *
 * @author 11718
 */
@JtComponent
public class BaiduVoiceReader implements Reader, WithJtLogger {
    private SpeechSynthesizer speechSynthesizer;
    private VoiceResources voiceResources;
    private Context context;

    private BaiduVoiceSetting baiduVoiceSetting;

    private ScheduledExecutorService executorService;

    private String currentReadText;

    private ReadThread currentReadThread;

    private boolean isReading = false;

    private int readerGenderIndex = 1;

    @IocAutowire
    public BaiduVoiceReader(Context context, VoiceResources voiceResources,
                            BaiduVoiceSetting baiduVoiceSetting) {
        this.context = context;
        this.voiceResources = voiceResources;
        this.baiduVoiceSetting = baiduVoiceSetting;

        executorService = JtExecutors.newScheduledThreadPool(1);
    }

    public void initSpeechSynthesizer() {
        //复制离线发音包到指定目录下
        try {
            voiceResources.initResources();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // 获取语音合成对象实例
        speechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        speechSynthesizer.setContext(context);
        // 设置语音合成状态监听器
        speechSynthesizer.setSpeechSynthesizerListener(new MySpeechSynthesizerListener());
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        speechSynthesizer
                .setApiKey(BaiduVoiceConstants.API_KEY, BaiduVoiceConstants.SECRE_KEY);
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        speechSynthesizer.setAppId(BaiduVoiceConstants.APP_ID);
        // 设置语音合成文本模型文件
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE,
                voiceResources.getTextModelFilePath());
        // 设置语音合成声音模型文件
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                voiceResources.getSpeechFemaleModelFilePath());
        // 获取语音合成授权信息
        AuthInfo authInfo = speechSynthesizer.auth(TtsMode.MIX);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            getJtLogger().error("鉴权失败 =" + errorMsg);
        }
        else {
            getJtLogger().info("验证通过，离线正式授权文件存在。");
            int result = speechSynthesizer.initTts(TtsMode.MIX);
            if (result != 0) {
                getJtLogger().error("初始化失败 + errorCode：" + result);
            }
            else {
                // 此时可以调用 speak和synthesize方法
                getJtLogger().info("合成引擎初始化成功");
            }
        }

    }

    protected class MySpeechSynthesizerListener implements SpeechSynthesizerListener {

        @Override
        public void onSynthesizeStart(String s) {
        }

        @Override
        public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        }

        @Override
        public void onSynthesizeFinish(String s) {
        }

        @Override
        public void onSpeechStart(String s) {
        }

        @Override
        public void onSpeechProgressChanged(String s, int i) {
        }

        @Override
        public void onSpeechFinish(String s) {
            if (baiduVoiceSetting.isRepeated() && isReading) {
                if (baiduVoiceSetting.getRepeatIntervalTime() > 0) {
                    executorService.schedule(currentReadThread,
                            baiduVoiceSetting.getRepeatIntervalTime(), TimeUnit.MILLISECONDS);
                }
                else {
                    currentReadThread = new ReadThread(currentReadText);
                    executorService.schedule(currentReadThread, 10, TimeUnit.MILLISECONDS);
                }
            }
        }

        @Override
        public void onError(String s, SpeechError speechError) {
//            getP().error(s + speechError.toString());
        }
    }

    @Override
    public void speech(String text) {
        this.currentReadText = text;

        //stop the last state
        stop();

        currentReadThread = new ReadThread(text);
        executorService.execute(currentReadThread);
    }

    @Override
    public void pause() {
        isReading = false;
        speechSynthesizer.pause();
    }

    @Override
    public void resume() {
        isReading = true;
        speechSynthesizer.resume();
    }

    @Override
    public void stop() {
        isReading = false;
        speechSynthesizer.stop();

        if (currentReadThread != null) {
            currentReadThread.setStopped(true);
        }

    }


    @Override
    public boolean isReading() {
        return isReading;
    }

    @Override
    public VoiceSetting getBaiduVoiceSetting() {
        return baiduVoiceSetting;
    }

    //*******************************
    private void changeToEnglishModel(String gender) {
        //		gender="male";
        changeSpeed(baiduVoiceSetting.getReadEnglishSpeed());

        String englishSpeechModelPath =
                "male".equals(gender) ? voiceResources.getSpeechMaleModelFilePath() :
                        voiceResources.getSpeechFemaleModelFilePath();
        speechSynthesizer.loadModel(voiceResources.getTextModelFilePath(),
                englishSpeechModelPath);
    }

    private void changeToChineseModel(String gender) {
        changeSpeed(baiduVoiceSetting.getReadChineseSpeed());

        String chineseSpeechModelPath =
                "male".equals(gender) ? voiceResources.getSpeechMaleModelFilePath() :
                        voiceResources.getSpeechFemaleModelFilePath();
        speechSynthesizer
                .loadModel(voiceResources.getTextModelFilePath(), chineseSpeechModelPath);
    }

    private void changeVolume(int degree) {
        if (degree >= 0 && degree <= 9) {
            speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, degree + "");
        }
    }

    private void changePitch(int degree) {
        if (degree >= 0 && degree <= 9) {
            speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, degree + "");
        }
    }

    private void changeSpeed(int degree) {
        if (degree >= 0 && degree <= 9) {
            speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, degree + "");
        }
    }

    //{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}
    private class ReadThread extends Thread {
        private String readText;
        private boolean isStopped = false;

        ReadThread(String readText) {
            this.readText = readText;
        }

        @Override
        public void run() {
            if (!isStopped) {
                isReading = true;
                boolean isChinese = WordUtil.isContainsChinese(readText);

                String gender;
                if (readerGenderIndex >= 1) {
                    gender = "female";
                }
                else {
                    gender = "male";
                }
                readerGenderIndex++;
                if (readerGenderIndex == 3) {
                    readerGenderIndex = 0;
                }


                if (isChinese) {
                    changeToChineseModel(gender);
                }
                else {
                    changeToEnglishModel(gender);
                }


                speechSynthesizer.speak(readText);
            }
        }

        public boolean isStopped() {
            return isStopped;
        }

        public void setStopped(boolean stopped) {
            isStopped = stopped;
        }
    }
}
