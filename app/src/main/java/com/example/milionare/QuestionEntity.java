package com.example.milionare;

import java.util.Map;

public class QuestionEntity {
    Map<String,String> answer;
    String correctAnswer;
    String question;

    public QuestionEntity() {
    }

    public QuestionEntity(Map<String, String> answer, String correctAnswer, String question) {
        this.answer = answer;
        this.correctAnswer = correctAnswer;
        this.question = question;
    }

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
