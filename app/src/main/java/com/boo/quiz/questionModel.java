package com.boo.quiz;

public class questionModel {
    private String id, question, A, B, C, D, answer,Set;

    public questionModel(String id, String question, String a, String b, String c, String d, String answer, String set) {
        this.id = id;
        this.question = question;
        A = a;
        B = b;
        C = c;
        D = d;
        this.answer = answer;
        this.Set = set;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSet() {
        return Set;
    }

    public void setSet(String set) {
        Set = set;
    }
}