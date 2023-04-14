package com.flk.quiz;

public class Question {
    public String title;
    public String content;

    public String[] answers;
    public int rightPos;

    public Question(String title, String content, int rightPos, String[] answers) {
        this.title = title;
        this.content = content;
        this.answers = answers;
        this.rightPos = rightPos;
    }
}
