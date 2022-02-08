package com.jeramtough.repeatwords2.component.task;

import android.os.Handler;
import android.os.Message;

import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtcomponent.task.callback.TaskCallback;


/**
 * Created on 2019-08-29 00:18
 * by @author JeramTough
 */
public abstract class TaskCallbackInMain {

    private MyTaskCallbask taskCallbask;
    private MyHandler myHandler;

    public TaskCallbackInMain() {
        taskCallbask = new MyTaskCallbask();
        myHandler = new MyHandler();
    }

    public MyTaskCallbask get() {
        return taskCallbask;
    }

    private class MyTaskCallbask implements TaskCallback {

        @Override
        public void onTaskStart() {
            myHandler.sendEmptyMessage(0);
        }

        @Override
        public void onTaskRunning(PreTaskResult preTaskResult, int numerator,
                                  int denominator) {
            Message message = new Message();
            message.what = 1;
            message.getData().putSerializable("preTaskResult", preTaskResult);
            message.getData().putInt("numerator", numerator);
            message.getData().putInt("denominator", denominator);

            myHandler.sendMessage(message);
        }

        @Override
        public void onTaskCompleted(TaskResult taskResult) {

            Message message = new Message();
            if (taskResult.isSuccessful()){
                message.what = 2;
            }
            else{
                message.what=3;
            }
            message.getData().putSerializable("taskResult", taskResult);
            myHandler.sendMessage(message);

        }
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    onTaskStart();
                    break;
                case 1:
                    PreTaskResult preTaskResult = (PreTaskResult) message.getData().getSerializable(
                            "preTaskResult");
                    int numerator = message.getData().getInt("numerator");
                    int denominator = message.getData().getInt("denominator");
                    onTaskRunning(preTaskResult, numerator, denominator);
                    break;
                case 2:
                    TaskResult taskResult = (TaskResult) message.getData().getSerializable(
                            "taskResult");
                    onTaskCompleted(taskResult);
                    break;
                case 3:
                    TaskResult taskResult2 = (TaskResult) message.getData().getSerializable(
                            "taskResult");
                    onTaskFailed(taskResult2);
                    break;
                default:
            }
        }
    }

    protected void onTaskStart() {
    }

    protected void onTaskRunning(PreTaskResult preTaskResult, int numerator,
                                 int denominator) {

    }

    protected void onTaskCompleted(TaskResult taskResult) {

    }

    protected void onTaskFailed(TaskResult taskResult){

    }

}
