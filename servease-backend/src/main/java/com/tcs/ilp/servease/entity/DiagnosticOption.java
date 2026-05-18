package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnostic_options", schema = "dev")
public class DiagnosticOption {

    @Id
    private String optionId;

    private String questionId;
    private String optionText;
    private String nextQuestionId;
    private boolean isPositive;

    //  DEFAULT CONSTRUCTOR (required for JPA)
    public DiagnosticOption() {
    }

    //  PARAMETERIZED CONSTRUCTOR (required for Mockito tests)
    public DiagnosticOption(String optionId,
                            String questionId,
                            String optionText,
                            String nextQuestionId,
                            boolean isPositive) {
        this.optionId = optionId;
        this.questionId = questionId;
        this.optionText = optionText;
        this.nextQuestionId = nextQuestionId;
        this.isPositive = isPositive;
    }

    //  GETTERS AND SETTERS

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(String nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

    //  toString
    @Override
    public String toString() {
        return "DiagnosticOption [optionId=" + optionId
                + ", questionId=" + questionId
                + ", optionText=" + optionText
                + ", nextQuestionId=" + nextQuestionId
                + ", isPositive=" + isPositive + "]";
    }
}