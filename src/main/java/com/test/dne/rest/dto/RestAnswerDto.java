package com.test.dne.rest.dto;

public class RestAnswerDto {

    public RestAnswerDto(int answer) {
        this.answer = answer;
    }

    private int answer;

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
