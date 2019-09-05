package com.jeramtough.repeatwords2.service.impl;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.callback.RunningTaskCallback;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtcomponent.task.runner.CallbackRunner;
import com.jeramtough.repeatwords2.component.learning.keeper.DefaultKeeperMaster;
import com.jeramtough.repeatwords2.component.learning.keeper.KeeperMaster;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;
import com.jeramtough.repeatwords2.service.LearningService;

/**
 * Created on 2019-09-02 19:20
 * by @author JeramTough
 */
@JtServiceImpl
public class LearningServiceImpl implements LearningService {

    private KeeperMaster keeperMaster;

    @IocAutowire
    public LearningServiceImpl(
            @InjectComponent(impl = DefaultKeeperMaster.class)
                    KeeperMaster keeperMaster) {
        this.keeperMaster = keeperMaster;
    }

    @Override
    public FutureTaskResponse initTeacher(
            TaskCallbackInMain taskCallback) {
        return ResponseFactory.asyncDoing(taskCallback.get(),
                (preTaskResult, runningTaskCallback) -> {
                    WordDto[] wordDtos = keeperMaster.getCurrentRecordKeeper().getRandomNeedLearningWords();
                    preTaskResult.putPayload("wordDtos", wordDtos);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse graspOrRemoveWord(WordDto wordDto,
                                                TaskCallbackInMain taskCallback) {
        return ResponseFactory.asyncDoing(taskCallback.get(),
                (preTaskResult, runningTaskCallback) -> {
                    keeperMaster.getCurrentRecordKeeper().removeWordFromRecordList(wordDto);
                    return true;
                });
    }

    @Override
    public FutureTaskResponse desertWord(WordDto wordDto, TaskCallbackInMain taskCallback) {
        return ResponseFactory.asyncDoing(taskCallback.get(),
                (preTaskResult, runningTaskCallback) -> {
                    return keeperMaster.getCurrentRecordKeeper().addWordToDesertedRecordList(wordDto);
                });
    }

    @Override
    public FutureTaskResponse markWord(WordDto wordDto, TaskCallbackInMain taskCallback) {
        return ResponseFactory.asyncDoing(taskCallback.get(), new CallbackRunner() {
            @Override
            public boolean doTask(PreTaskResult preTaskResult,
                                  RunningTaskCallback runningTaskCallback) {
                return keeperMaster.getCurrentRecordKeeper().addWordToMarkedRecordList(wordDto);
            }
        });
    }


    @Override
    public FutureTaskResponse learnedWordInToday(WordDto wordDto) {
        return ResponseFactory.asyncDoing(preTaskResult -> {
            keeperMaster.getCurrentRecordKeeper().addWordToHaveLearnedTodayRecordList(wordDto);
            return true;
        });
    }


}
