package com.example.milionare;

public class ScoreEntity {
    long id;
    String money;
    int time;
    int score = 0;

    public ScoreEntity(long id, String money, int time, int score) {
        this.id = id;
        this.money = money;
        this.time = time;
        this.score = score;
    }

    public ScoreEntity(String money, int time) {
        this.money = money;
        this.time = time;
        calculateScore(money, time);
    }

    void calculateScore(String money, int time){
        int scoreBefore = 0;
        switch (money) {
            case "0":
                scoreBefore=0;
                break;
            case "500":
                scoreBefore=10000;
                break;
            case "1.000":
                scoreBefore=20000;
                break;
            case "2.000":
                scoreBefore=30000;
                break;
            case "3.000":
                scoreBefore=40000;
                break;
            case "5.000":
                scoreBefore=50000;
                break;
            case "7.500":
                scoreBefore=60000;
                break;
            case "15.000":
                scoreBefore=70000;
                break;
            case "30.000":
                scoreBefore=80000;
                break;
            case "60.000":
                scoreBefore=90000;
                break;
            case "125.000":
                scoreBefore=100000;
                break;
            case "250.000":
                scoreBefore=110000;
                break;
            case "1.000.000":
                scoreBefore=120000;
                break;
        }

        int score = scoreBefore/time;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
