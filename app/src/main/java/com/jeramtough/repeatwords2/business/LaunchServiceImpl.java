package com.jeramtough.repeatwords2.business;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.bean.word.Dictionary;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.dictionary.DictionaryManager;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;
import com.jeramtough.repeatwords2.dao.mapper.DictionaryMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:08.
 */
@JtServiceImpl(pattern = JtBeanPattern.Prototype)
class LaunchServiceImpl implements LaunchService
{
    private Context context;
    private final PermissionManager permissionManager;
    private DictionaryMapper dictionaryMapper;
    private OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider;
    private DictionaryManager dictionaryManager;
    private BaiduVoiceReader baiduVoiceReader;
    
    @IocAutowire
    private LaunchServiceImpl(Context context, PermissionManager permissionManager,
            DictionaryMapper dictionaryMapper,
            OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider,
            DictionaryManager dictionaryManager, BaiduVoiceReader baiduVoiceReader)
    {
        this.context = context;
        this.permissionManager = permissionManager;
        this.dictionaryMapper = dictionaryMapper;
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
        this.dictionaryManager = dictionaryManager;
        this.baiduVoiceReader = baiduVoiceReader;
    }
    
    
    @Override
    public boolean requestNeededPermission(Activity activity, int requestPermissionsCode)
    {
        permissionManager.addNeededPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionManager.addNeededPermission(Manifest.permission.RECORD_AUDIO);
        
        boolean isHaveAllNeededPermissions =
                permissionManager.checkIsHaveAllPermission(activity);
        if (isHaveAllNeededPermissions)
        {
            return true;
        }
        else
        {
            permissionManager.requestNeededPermissions(activity, 666);
            return false;
        }
    }
    
    @Override
    public void initApp(BusinessCaller businessCaller)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                //init dictionary
                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                int wordsCount = myDatabaseHelper.getDictionaryWordsCount();
                if (wordsCount == 0)
                {
                    Dictionary dictionary = dictionaryManager.getDictionaryFromAssets();
                    for (Word word : dictionary.getWords())
                    {
                        dictionaryMapper.addWord(word.getId(), word.getEn(), word.getCh(),
                                word.getPhonetic());
                    }
                    
                    myDatabaseHelper.initTables();
                }
                
                //init the BaiduVoiceReader
                baiduVoiceReader.initSpeechSynthesizer();
                
                businessCaller.callBusiness();
            }
        }.start();
    }
}
