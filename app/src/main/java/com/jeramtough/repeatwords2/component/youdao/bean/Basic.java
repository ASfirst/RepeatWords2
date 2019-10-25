package com.jeramtough.repeatwords2.component.youdao.bean;

import java.util.List;

/**
 * Auto-generated: 2018-05-12 14:50:18
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Basic {

    private String usPhonetic;
    private String phonetic;
    private String ukPhonetic;
    private String ukSpeech;
    private List<String> explains;
    private String usSpeech;

    public String getUsPhonetic() {
        return usPhonetic;
    }

    public void setUsPhonetic(String usPhonetic) {
        this.usPhonetic = usPhonetic;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getUkPhonetic() {
        return ukPhonetic;
    }

    public void setUkPhonetic(String ukPhonetic) {
        this.ukPhonetic = ukPhonetic;
    }

    public String getUkSpeech() {
        return ukSpeech;
    }

    public void setUkSpeech(String ukSpeech) {
        this.ukSpeech = ukSpeech;
    }

    public List<String> getExplains() {
        return explains;
    }

    public void setExplains(List<String> explains) {
        this.explains = explains;
    }

    public String getUsSpeech() {
        return usSpeech;
    }

    public void setUsSpeech(String usSpeech) {
        this.usSpeech = usSpeech;
    }

    @Override
    public String toString() {
        return "Basic{" +
                "usPhonetic='" + usPhonetic + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", ukPhonetic='" + ukPhonetic + '\'' +
                ", ukSpeech='" + ukSpeech + '\'' +
                ", explains=" + explains +
                ", usSpeech='" + usSpeech + '\'' +
                '}';
    }
}