package com.jeramtough.repeatwords2.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.ioc.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.dictionary.Dictionary;
import com.jeramtough.repeatwords2.component.dictionary.DictionaryManager;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;
import com.jeramtough.repeatwords2.dao.mapper.DictionaryMapper1;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:08.
 */
@JtServiceImpl
class LaunchServiceImpl implements LaunchService {
    private Context context;
    private final PermissionManager permissionManager;
    private DictionaryMapper1 dictionaryMapper1;
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;
    private DictionaryManager dictionaryManager;
    private BaiduVoiceReader baiduVoiceReader;
    private MyAppSetting myAppSetting;

    @IocAutowire
    private LaunchServiceImpl(Context context,
                              PermissionManager permissionManager,
                              DictionaryMapper1 dictionaryMapper1,
                              OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider,
                              DictionaryManager dictionaryManager,
                              BaiduVoiceReader baiduVoiceReader, MyAppSetting myAppSetting
    ) {
        this.context = context;
        this.permissionManager = permissionManager;
        this.dictionaryMapper1 = dictionaryMapper1;
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
        this.dictionaryManager = dictionaryManager;
        this.baiduVoiceReader = baiduVoiceReader;
        this.myAppSetting = myAppSetting;
    }


    @Override
    public boolean requestNeededPermission(Activity activity, int requestPermissionsCode) {
        permissionManager.addNeededPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionManager.addNeededPermission(Manifest.permission.RECORD_AUDIO);

        boolean isHaveAllNeededPermissions =
                permissionManager.checkIsHaveAllPermission(activity);
        if (isHaveAllNeededPermissions) {
            return true;
        }
        else {
            permissionManager.requestNeededPermissions(activity, 666);
            return false;
        }
    }

    @Override
    public FutureTaskResponse initApp(IocContext iocContext, TaskCallbackInMain taskCallback) {
        return ResponseFactory.asyncDoing(taskCallback.get(),
                (preTaskResult, runningTaskCallback) -> {

                    final int denominator = 4;

                    //init dictionary
                    preTaskResult.setMessage("Initializing dictionary");
                    runningTaskCallback.onTaskRunning(preTaskResult, 1, denominator);
                    boolean isSuccessful = dictionaryManager.initDictionary();
                    if (!isSuccessful) {
                        preTaskResult.setMessage("init dictionary db failed");
                        return false;
                    }
                    DictionaryMapper dictionaryMapper = dictionaryManager.getDictionaryMapper();
                    iocContext.getBeansContainer().registerBean(dictionaryMapper);


                    preTaskResult.setMessage("Preparing the learn scheme");
                    runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);

                    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                    int wordsCount = myDatabaseHelper.getDictionaryWordsCount();
                    if (wordsCount == 0) {
                        Dictionary dictionary = dictionaryManager.getDictionaryFromAssets();
                        for (Word word : dictionary.getWords()) {
                            dictionaryMapper1.addWord(word.getId(), word.getEn(), word.getCh(),
                                    word.getPhonetic());
                        }

                        myDatabaseHelper.initTables();
                    }

                    //clear learning recodes if today is new day
                    preTaskResult.setMessage(
                            "Clearing the learning recodes if today is new day");
                    runningTaskCallback.onTaskRunning(preTaskResult, 3, 4);
                    if (!DateTimeUtil.getDate().equals(myAppSetting.getLastDateOpenedApp())) {
                        operateWordsMapperFactoryProvider.getListeningTeacherOperateWordsMapperFactory()
                                                         .getHaveLearnedTodayMapper().clearAll();
                        operateWordsMapperFactoryProvider.getSpeakingTeacherOperateWordsMapperFactory()
                                                         .getHaveLearnedTodayMapper().clearAll();
                        operateWordsMapperFactoryProvider.getWritingTeacherOperateWordsMapperFactory()
                                                         .getHaveLearnedTodayMapper().clearAll();

                        myAppSetting.setLastDateOpenedApp(DateTimeUtil.getDate());
                    }

                    preTaskResult.setMessage("Init the BaiduVoiceReader");
                    runningTaskCallback.onTaskRunning(preTaskResult, 4, denominator);
                    //init the BaiduVoiceReader
                    baiduVoiceReader.initSpeechSynthesizer();
                    return false;
                });
    }
}
