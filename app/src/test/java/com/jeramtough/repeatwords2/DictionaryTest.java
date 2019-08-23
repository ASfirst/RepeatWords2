package com.jeramtough.repeatwords2;

import com.jeramtough.jtcomponent.utils.FileUtil;
import com.jeramtough.jtcomponent.utils.WordUtil;
import com.jeramtough.jtlog.annotation.LogConfiguration;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.repeatwords2.bean.word.Word;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2019-08-23 15:26
 * by @author JeramTough
 */
@LogConfiguration(wrapCount = 0)
public class DictionaryTest {


    @Before
    public void before() {
        L.getLogContext().getLogConfig().setWrapCount(0);
    }

    public static void main(String[] args) {

        L.arrive();
    }


    @Test
    public void test1() {


        try {
            List<String> lines = IOUtils.readLines(
                    new FileInputStream(new File("C:\\Users\\11718" +
                            "\\Desktop\\words.txt")), "UTF-8");
            String text = "";
            Pattern pattern = Pattern.compile("[a-z]\\[(.*?)*]");
            int size = 0;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);

                Matcher matcher = pattern.matcher(line);
                String phonetic = "";

                String[] temp = line.split(" ");


                if (WordUtil.isContainsChinese(temp[0])) {
                }
                else {
                    text = text + line + "\n";
                }

               /* while (matcher.find()) {
                    phonetic = matcher.group();
                    String a = phonetic.substring(1);
                    char b = phonetic.toCharArray()[0];
                    phonetic = b + " " + a;
                    L.debug(phonetic);
                }

                //替换所有匹配的字符串
                line = matcher.replaceAll(phonetic);
                text = text + line + "\n";*/



             /*  if (line.contains("计算机 ")){
                   L.p(line);
                   text = text + line.replace("计算机 ","计算机:") + "\n";
               }
               else{
                   text = text + line + "\n";
               }*/


            }
            File file2 = new File("C:\\Users\\11718\\Desktop\\words2.txt");
            FileUtil.createFile(file2);
            IOUtils.write(text.getBytes(),
                    new FileWriter(file2), "UTF" +
                            "-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        File file2 = new File("C:\\Users\\11718\\Desktop\\words2.txt");
        List<String> lines = null;
        Pattern pattern = Pattern.compile("\\s\\[(.*?)*]");
        try {
            lines = IOUtils.readLines(
                    new FileInputStream(file2), "UTF-8");
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);


                String[] temp = line.split(" ");
                String ch = "";
                for (int i1 = 0; i1 < temp.length; i1++) {
                    if (i1 > 0) {
                        ch = ch + temp[i1] + "，";
                    }
                }
                Word word = new Word();
                word.setEn(temp[0]);
                L.p(word.toString());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
