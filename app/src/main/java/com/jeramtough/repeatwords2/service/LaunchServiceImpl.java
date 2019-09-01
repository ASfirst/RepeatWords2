package com.jeramtough.repeatwords2.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.jeramtough.jtandroid.function.FirstBoot;
import com.jeramtough.jtandroid.function.PermissionManager;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.oedslib.comparator.TagPositionComparator;
import com.jeramtough.oedslib.entity.LargeWord;
import com.jeramtough.oedslib.mapper.DictionaryMapper;
import com.jeramtough.oedslib.tag.WordTag;
import com.jeramtough.repeatwords2.bean.word.WordCondition;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.baidu.BaiduVoiceReader;
import com.jeramtough.repeatwords2.component.dictionary.DictionaryManager;
import com.jeramtough.repeatwords2.component.learning.scheme.LearningScheme;
import com.jeramtough.repeatwords2.component.learning.scheme.ListeningLearningScheme;
import com.jeramtough.repeatwords2.component.learning.scheme.ReadingLearningScheme;
import com.jeramtough.repeatwords2.component.learning.scheme.SpeakingLearningScheme;
import com.jeramtough.repeatwords2.component.learning.scheme.WritingLearningScheme;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.dao.MyDatabaseHelper;
import com.jeramtough.repeatwords2.dao.entity.WordRecord;
import com.jeramtough.repeatwords2.dao.mapper.DictionaryMapper1;
import com.jeramtough.repeatwords2.dao.mapper.OperateWordsMapper;
import com.jeramtough.repeatwords2.dao.mapper.provider.DefaultOperateWordsMapperProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperFactoryProvider;
import com.jeramtough.repeatwords2.dao.mapper.provider.OperateWordsMapperProvider;
import com.jeramtough.repeatwords2.util.DateTimeUtil;

import java.util.Arrays;

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
    private FirstBoot firstBoot;
    private OperateWordsMapperProvider operateWordsMapperProvider;
    private MyDatabaseHelper myDatabaseHelper;

    @IocAutowire
    private LaunchServiceImpl(Context context,
                              PermissionManager permissionManager,
                              DictionaryMapper1 dictionaryMapper1,
                              OperateWordsMapperFactoryProvider operateWordsMapperFactoryProvider,
                              DictionaryManager dictionaryManager,
                              BaiduVoiceReader baiduVoiceReader, MyAppSetting myAppSetting,
                              FirstBoot firstBoot,
                              @InjectComponent(impl = DefaultOperateWordsMapperProvider.class)
                                      OperateWordsMapperProvider operateWordsMapperProvider,
                              MyDatabaseHelper myDatabaseHelper) {
        this.context = context;
        this.permissionManager = permissionManager;
        this.dictionaryMapper1 = dictionaryMapper1;
        this.operateWordsMapperFactoryProvider = operateWordsMapperFactoryProvider;
        this.dictionaryManager = dictionaryManager;
        this.baiduVoiceReader = baiduVoiceReader;
        this.myAppSetting = myAppSetting;
        this.firstBoot = firstBoot;
        this.operateWordsMapperProvider = operateWordsMapperProvider;
        this.myDatabaseHelper = myDatabaseHelper;
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
                    iocContext.getBeanContainer().registerBean(dictionaryMapper,
                            JtBeanPattern.Singleton);


                    preTaskResult.setMessage("Preparing the learn scheme");
                    runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);

                    if (firstBoot.isFirstBoot()) {
                        myDatabaseHelper.createTables();

                        //preparing listening learning scheme.
                        iocContext.getBeanContainer().registerBean(
                                ListeningLearningScheme.class);
                        ListeningLearningScheme listeningLearningScheme =
                                iocContext.getBeanContainer().getBean(
                                        ListeningLearningScheme.class);
                        preparingLearningScheme(preTaskResult, runningTaskCallback,
                                listeningLearningScheme, denominator);


                        //preparing speaking learning scheme.
                        iocContext.getBeanContainer().registerBean(
                                SpeakingLearningScheme.class);
                        SpeakingLearningScheme speakingLearningScheme =
                                iocContext.getBeanContainer().getBean(
                                        SpeakingLearningScheme.class);
                        preparingLearningScheme(preTaskResult, runningTaskCallback,
                                speakingLearningScheme, denominator);


                        //preparing writing learning scheme.
                        iocContext.getBeanContainer().registerBean(
                                WritingLearningScheme.class);
                        WritingLearningScheme writingLearningScheme =
                                iocContext.getBeanContainer().getBean(
                                        WritingLearningScheme.class);
                        preparingLearningScheme(preTaskResult, runningTaskCallback,
                                writingLearningScheme, denominator);

                        //preparing reading learning scheme.
                        iocContext.getBeanContainer().registerBean(
                                ReadingLearningScheme.class);
                        ReadingLearningScheme readingLearningScheme =
                                iocContext.getBeanContainer().getBean(
                                        ReadingLearningScheme.class);
                        preparingLearningScheme(preTaskResult, runningTaskCallback,
                                readingLearningScheme, denominator);


                    }

                    //clear learning recodes if today is new day
                    preTaskResult.setMessage(
                            "Clearing the learning recodes if today is new day");
                    runningTaskCallback.onTaskRunning(preTaskResult, 3, 4);
                    if (!DateTimeUtil.getDate().equals(
                            myAppSetting.getDateForLastOpenedApp())) {
                        operateWordsMapperProvider.getOperateWordsMapper(
                                TeacherType.LISTENING_TEACHER,
                                WordCondition.LEARNED_TODAY).clearAll();
                        operateWordsMapperProvider.getOperateWordsMapper(
                                TeacherType.SPEAKING_TEACHER,
                                WordCondition.LEARNED_TODAY).clearAll();
                        operateWordsMapperProvider.getOperateWordsMapper(
                                TeacherType.WRITING_TEACHER,
                                WordCondition.LEARNED_TODAY).clearAll();
                        operateWordsMapperProvider.getOperateWordsMapper(
                                TeacherType.READING_TEACHER,
                                WordCondition.LEARNED_TODAY).clearAll();

                        myAppSetting.setDateForLastOpenedApp(DateTimeUtil.getDate());
                    }

                    preTaskResult.setMessage("Init the BaiduVoiceReader");
                    runningTaskCallback.onTaskRunning(preTaskResult, 4, denominator);
                    //init the BaiduVoiceReader
                    baiduVoiceReader.initSpeechSynthesizer();
                    return true;
                });
    }


    //****************************

    private void preparingLearningScheme(PreTaskResult preTaskResult,
                                         RunningTaskCallback runningTaskCallback,
                                         LearningScheme learningScheme, int denominator) {
        final int[] i = {0};
        preTaskResult.setMessage("Waiting for " + learningScheme.getClass().getSimpleName());
        runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
        learningScheme.initScheme(largeWord -> {
            preTaskResult.setMessage(String.format("\n[%d] \n%s\nword[%s]", i[0],
                    learningScheme.getClass().getSimpleName(), largeWord.getWord()));
            runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
            i[0]++;
        });
    }


    private void initListeningAndSpeakingLearningScheme(PreTaskResult preTaskResult,
                                                        RunningTaskCallback runningTaskCallback,
                                                        int denominator,
                                                        DictionaryMapper dictionaryMapper) {
        LargeWord[] largeWords;
        //Listening mode and speaking mode use the NCE dictionary
        preTaskResult.setMessage("Selecting " + WordTag.NCE.name() + " " +
                "dictionary...");
        runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
        largeWords = dictionaryMapper.selectListByTag(WordTag.NCE);
        Arrays.sort(largeWords, new TagPositionComparator(WordTag.NCE));
        OperateWordsMapper listenOperateWordsMapper =
                operateWordsMapperProvider.getOperateWordsMapper(
                        TeacherType.LISTENING_TEACHER,
                        WordCondition.SHALL_LEARNING);
        OperateWordsMapper speakOperateWordsMapper =
                operateWordsMapperProvider.getOperateWordsMapper(
                        TeacherType.SPEAKING_TEACHER,
                        WordCondition.SHALL_LEARNING);
        for (int i = 0; i < largeWords.length; i++) {
            LargeWord largeWord = largeWords[i];
            WordRecord wordRecord = new WordRecord();
            wordRecord.setTime(DateTimeUtil.getDateTime());
            wordRecord.setLevel(0);
            wordRecord.setWordId(largeWord.getFdId());
            preTaskResult.setMessage(String.format("\n[%d]Listening and " +
                    "Speaking " +
                    "\n word[%s]", i, largeWord.getWord()));
            runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
            listenOperateWordsMapper.addWordRecord(wordRecord);
            speakOperateWordsMapper.addWordRecord(wordRecord);
        }
    }

    private void initWirtingLearningScheme(PreTaskResult preTaskResult,
                                           RunningTaskCallback runningTaskCallback,
                                           int denominator,
                                           DictionaryMapper dictionaryMapper) {
        LargeWord[] largeWords;
        preTaskResult.setMessage("Selecting " + WordTag.ZK.name() + " " +
                "dictionary...");
        runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
        largeWords = dictionaryMapper.selectListByWordTagOrderByFrq(WordTag.ZK);
        OperateWordsMapper writeOperateWordsMapper =
                operateWordsMapperProvider.getOperateWordsMapper(
                        TeacherType.WRITING_TEACHER,
                        WordCondition.SHALL_LEARNING);
        for (int i = 0; i < largeWords.length; i++) {
            LargeWord largeWord = largeWords[i];
            WordRecord wordRecord = new WordRecord();
            wordRecord.setTime(DateTimeUtil.getDateTime());
            wordRecord.setLevel(0);
            wordRecord.setWordId(largeWord.getFdId());
            preTaskResult.setMessage(
                    String.format("\n[%d]Writing word[%s]", i, largeWord.getWord()));
            runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
            writeOperateWordsMapper.addWordRecord(wordRecord);
        }
    }

    private void initReadingLearningScheme(PreTaskResult preTaskResult,
                                           RunningTaskCallback runningTaskCallback,
                                           int denominator,
                                           DictionaryMapper dictionaryMapper) {
        LargeWord[] largeWords;
        preTaskResult.setMessage("Selecting " + WordTag.CET4.name() + " " +
                "dictionary...");
        runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
        largeWords = dictionaryMapper.selectListByWordTagOrderByFrq(WordTag.CET4);
        OperateWordsMapper readOperateWordsMapper =
                operateWordsMapperProvider.getOperateWordsMapper(
                        TeacherType.READING_TEACHER,
                        WordCondition.SHALL_LEARNING);
        for (int i = 0; i < largeWords.length; i++) {
            LargeWord largeWord = largeWords[i];
            WordRecord wordRecord = new WordRecord();
            wordRecord.setTime(DateTimeUtil.getDateTime());
            wordRecord.setLevel(0);
            wordRecord.setWordId(largeWord.getFdId());
            preTaskResult.setMessage(
                    String.format("\n[%d]Reading word[%s]", i, largeWord.getWord()));
            runningTaskCallback.onTaskRunning(preTaskResult, 2, denominator);
            readOperateWordsMapper.addWordRecord(wordRecord);
        }
    }
}
