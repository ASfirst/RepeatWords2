package com.jeramtough.repeatwords2.component.record;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.java.Directory;
import com.jeramtough.jtcomponent.utils.CompressorUtil;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.repeatwords2.bean.record.LearningRecord;
import com.jeramtough.repeatwords2.component.app.AppConstants;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;

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
public class LearningRecordManager implements WithLogger {
    private Directory backupDirectory;
    private String learningRecordFileName = "leaning_record.json";
    private String speakingRecordFileName = "speaking_record.json";
    private String writingRecordFileName = "writing_record.json";
    private String readingRecordFileName = "reading_record.json";
    private String recordJsonBackupFileName = "RecordJsonBackupFiles.zip";
    private File recordFile1;
    private File recordFile2;
    private File recordFile3;
    private File recordFile4;
    private File recordBackupZipFile;

    @IocAutowire
    public LearningRecordManager() {
        backupDirectory = new Directory(
                AppConstants.APP_DIRECTORY_PATH + File.separator + "RepeatWords" + File.separator + "backup");
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
        recordFile4 =
                new File(
                        backupDirectory.getAbsoluteFile() + File.separator + readingRecordFileName);

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
            if (recordFile4.exists()) {
                recodeFiles.add(recordFile4);
            }
            CompressorUtil.compress(recodeFiles.toArray(new File[0]),
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
                else if (recordKey.equals(readingRecordFileName)) {
                    recordFile = recordFile4;
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
        else {
            getLogger().error("The backup directory didn't exist");
            return false;
        }
    }


    public Map<TeacherType, LearningRecord> recover() {
        HashMap<TeacherType, LearningRecord> learningRecords = new LinkedHashMap<>();
        if (recordFile1.exists()) {
            try {
                String jsonStr1 = IOUtils.toString(new FileInputStream(recordFile1), "UTF-8");
                String jsonStr2 = IOUtils.toString(new FileInputStream(recordFile2), "UTF-8");
                String jsonStr3 = IOUtils.toString(new FileInputStream(recordFile3), "UTF-8");
                String jsonStr4 = IOUtils.toString(new FileInputStream(recordFile4), "UTF-8");
                learningRecords.put(TeacherType.LISTENING_TEACHER,
                        JSON.parseObject(jsonStr1, LearningRecord.class));
                learningRecords.put(TeacherType.SPEAKING_TEACHER,
                        JSON.parseObject(jsonStr2, LearningRecord.class));
                learningRecords.put(TeacherType.WRITING_TEACHER,
                        JSON.parseObject(jsonStr3, LearningRecord.class));
                learningRecords.put(TeacherType.READING_TEACHER,
                        JSON.parseObject(jsonStr4, LearningRecord.class));
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

    public String getSpeakingRecordFileName() {
        return speakingRecordFileName;
    }

    public String getWritingRecordFileName() {
        return writingRecordFileName;
    }

    public String getReadingRecordFileName() {
        return readingRecordFileName;
    }

    //**********************


}
