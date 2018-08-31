package com.jeramtough.repeatwords2.bean.youdao;

import java.util.List;

/**
 * Auto-generated: 2018-05-12 14:50:18
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Web {

    private List<String> value;
    private String key;

    public void setValue(List<String> value) {
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Web{" +
                "value=" + value +
                ", key='" + key + '\'' +
                '}';
    }
}