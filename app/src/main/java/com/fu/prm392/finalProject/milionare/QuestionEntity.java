package com.fu.prm392.finalProject.milionare;

import java.util.Map;

public class QuestionEntity {
    Map<String,String> cevaplar;
    String dogrucevap;
    String sorulansoru;

    public QuestionEntity() {
    }

    public Map<String, String> getCevaplar() {

        return cevaplar;
    }

    public void setCevaplar(Map<String, String> cevaplar) {
        this.cevaplar = cevaplar;
    }

    public String getDogrucevap() {
        return dogrucevap;
    }

    public void setDogrucevap(String dogrucevap) {
        this.dogrucevap = dogrucevap;
    }

    public String getSorulansoru() {
        return sorulansoru;
    }

    public void setSorulansoru(String sorulansoru) {
        this.sorulansoru = sorulansoru;
    }

    public QuestionEntity(Map<String, String> cevaplar, String dogrucevap, String sorulansoru) {

        this.cevaplar = cevaplar;
        this.dogrucevap = dogrucevap;
        this.sorulansoru = sorulansoru;
    }
}
