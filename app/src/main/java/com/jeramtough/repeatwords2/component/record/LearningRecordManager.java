package com.jeramtough.repeatwords2.component.record;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.component.app.AppConstants;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 11718
 * on 2018  May 06 Sunday 18:05.
 */
@JtComponent
public class LearningRecordManager {
    private Directory backupDirectory;
    private String learningRecordFileName = "leaning_record.json";
    private String speakingRecordFileName = "speaking_record.json";
    private String writingRecordFileName = "writing_record.json";
    private File recordFile1;
    private File recordFile2;
    private File recordFile3;
    private File recordBackupFile1;
    private File recordBackupFile2;
    private File recordBackupFile3;

    @IocAutowire
    public LearningRecordManager() {
        backupDirectory = new Directory(
                AppConstants.APP_DIRECTORY_PATH + File.separator + "RepeatWords");
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }

        recordFile1 =
                new File(
                        backupDirectory.getAbsoluteFile() + File.separator + learningRecordFileName);
        recordFile2 =
                new File(
                        backupDirectory.getAbsoluteFile() + File.separator + speakingRecordFileName);
        recordFile3 =
                new File(
                        backupDirectory.getAbsoluteFile() + File.separator + writingRecordFileName);

        recordBackupFile1 = new File(
                backupDirectory.getAbsoluteFile() + File.separator +
                        learningRecordFileName + ".backup");
        recordBackupFile2 = new File(
                backupDirectory.getAbsoluteFile() + File.separator +
                        speakingRecordFileName + ".backup");
        recordBackupFile3 = new File(
                backupDirectory.getAbsoluteFile() + File.separator +
                        writingRecordFileName + ".backup");
    }

    public boolean backup(Map<String, LearningRecord> learningRecords) {
        if (backupDirectory.exists()) {
            for (String recordKey : learningRecords.keySet()) {
                LearningRecord learningRecord = learningRecords.get(recordKey);
                File recordFile = null;
                File recordBackupFile = null;
                if (recordKey.equals(learningRecordFileName)) {
                    recordFile = recordFile1;
                    recordBackupFile = recordBackupFile1;
                }
                else if (recordKey.equals(speakingRecordFileName)) {
                    recordFile = recordFile2;
                    recordBackupFile = recordBackupFile2;
                }
                else if (recordKey.equals(writingRecordFileName)) {
                    recordFile = recordFile3;
                    recordBackupFile = recordBackupFile3;
                }

                //backup
                recordBackupFile1.delete();
                recordBackupFile2.delete();
                recordBackupFile3.delete();
                recordFile.renameTo(recordBackupFile);

                try {
                    byte[] bytes = JSON.toJSONBytes(learningRecord);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            Objects.requireNonNull(recordFile));
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    public Map<String, LearningRecord> recover() {
        HashMap<String, LearningRecord> learningRecords = new LinkedHashMap<>();
        if (recordFile1.exists()) {
            try {
                String jsonStr1 = IOUtils.toString(new FileInputStream(recordFile1), "UTF-8");
                String jsonStr2 = IOUtils.toString(new FileInputStream(recordFile2), "UTF-8");
                String jsonStr3 = IOUtils.toString(new FileInputStream(recordFile3), "UTF-8");
                learningRecords.put(learningRecordFileName,
                        JSON.parseObject(jsonStr1, LearningRecord.class));
                learningRecords.put(speakingRecordFileName,
                        JSON.parseObject(jsonStr2, LearningRecord.class));
                learningRecords.put(writingRecordFileName,
                        JSON.parseObject(jsonStr3, LearningRecord.class));
                return learningRecords;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String getLearningRecordFileName() {
        return learningRecordFileName;
    }

    public void setLearningRecordFileName(String learningRecordFileName) {
        this.learningRecordFileName = learningRecordFileName;
    }

    public String getSpeakingRecordFileName() {
        return speakingRecordFileName;
    }

    public void setSpeakingRecordFileName(String speakingRecordFileName) {
        this.speakingRecordFileName = speakingRecordFileName;
    }

    public String getWritingRecordFileName() {
        return writingRecordFileName;
    }

    public void setWritingRecordFileName(String writingRecordFileName) {
        this.writingRecordFileName = writingRecordFileName;
    }
}
