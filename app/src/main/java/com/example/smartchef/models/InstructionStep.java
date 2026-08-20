package com.example.smartchef.models;

import java.io.Serializable;

public class InstructionStep implements Serializable {
    private int stepNumber;
    private String text;
    private int timerSeconds;

    public InstructionStep(int stepNumber, String text, int timerSeconds) {
        this.stepNumber = stepNumber;
        this.text = text;
        this.timerSeconds = timerSeconds;
    }

    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getTimerSeconds() { return timerSeconds; }
    public void setTimerSeconds(int timerSeconds) { this.timerSeconds = timerSeconds; }
}
