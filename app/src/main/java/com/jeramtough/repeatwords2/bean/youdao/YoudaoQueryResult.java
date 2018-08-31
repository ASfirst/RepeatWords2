package com.jeramtough.repeatwords2.bean.youdao;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-12 14:50:18
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class YoudaoQueryResult implements Serializable {

    private String tspeakurl;
    private List<Web> web;
    private String query;
    private List<String> translation;
    private String errorcode;
    private Dict dict;
    private Webdict webdict;
    private Basic basic;
    private String l;
    private String speakurl;

    public void setTspeakurl(String tspeakurl) {
        this.tspeakurl = tspeakurl;
    }

    public String getTspeakurl() {
        return tspeakurl;
    }

    public void setWeb(List<Web> web) {
        this.web = web;
    }

    public List<Web> getWeb() {
        return web;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public Dict getDict() {
        return dict;
    }

    public void setWebdict(Webdict webdict) {
        this.webdict = webdict;
    }

    public Webdict getWebdict() {
        return webdict;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getL() {
        return l;
    }

    public void setSpeakurl(String speakurl) {
        this.speakurl = speakurl;
    }

    public String getSpeakurl() {
        return speakurl;
    }

    @Override
    public String toString() {
        return "YoudaoQueryResult{" +
                "tspeakurl='" + tspeakurl + '\'' +
                ", web=" + web +
                ", query='" + query + '\'' +
                ", translation=" + translation +
                ", errorcode='" + errorcode + '\'' +
                ", dict=" + dict +
                ", webdict=" + webdict +
                ", basic=" + basic +
                ", l='" + l + '\'' +
                ", speakurl='" + speakurl + '\'' +
                '}';
    }
}