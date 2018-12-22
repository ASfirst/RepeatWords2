package com.jeramtough.repeatwords2.component.record;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtutil.core.CompressorUtil;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.component.app.AppConstants;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    private String recordJsonBackupFileName = "RecordJsonBackupFiles.zip";
    private File recordFile1;
    private File recordFile2;
    private File recordFile3;
    private File recordBackupZipFile;

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

        recordBackupZipFile = new File(
                backupDirectory.getAbsoluteFile() + File.separator +
                        recordJsonBackupFileName);
    }

    public boolean backup(Map<String, LearningRecord> learningRecords) {

        if (backupDirectory.exists()) {

            //backup
            recordBackupZipFile.delete();
            List<File> recodeFiles = new ArrayList<>();
            if (recordFile1.exists()) {
                recodeFiles.add(recordFile1);
            }
            if (recordFile2.exists()) {
                recodeFiles.add(recordFile2);
            }
            if (recordFile3.exists()) {
                recodeFiles.add(recordFile3);
            }
            CompressorUtil.compress(recodeFiles.toArray(new File[recodeFiles.size()]),
                    recordBackupZipFile.getAbsolutePath());

            for (String recordKey : learningRecords.keySet()) {
                LearningRecord learningRecord = learningRecords.get(recordKey);
                File recordFile = null;
                if (recordKey.equals(learningRecordFileName)) {
                    recordFile = recordFile1;
                }
                else if (recordKey.equals(speakingRecordFileName)) {
                    recordFile = recordFile2;
                }
                else if (recordKey.equals(writingRecordFileName)) {
                    recordFile = recordFile3;
                }

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
