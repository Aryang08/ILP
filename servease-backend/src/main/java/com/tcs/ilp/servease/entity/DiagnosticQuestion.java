package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnostic_question", schema = "dev")
public class DiagnosticQuestion {

    @Id
    private String questionId;

    private String applianceType;
    private String questionText;
    private String issueTag;
    private boolean isTerminal;
    private boolean isRoot;

    // DEFAULT CONSTRUCTOR (required for JPA)
    public DiagnosticQuestion() {
    }

    //  PARAMETERIZED CONSTRUCTOR (required for your tests)
    public DiagnosticQuestion(String questionId,
                              String applianceType,
                              String questionText,
                              String issueTag,
                              boolean isTerminal,
                              boolean isRoot) {
        this.questionId = questionId;
        this.applianceType = applianceType;
        this.questionText = questionText;
        this.issueTag = issueTag;
        this.isTerminal = isTerminal;
        this.isRoot = isRoot;
    }

    //  GETTERS AND SETTERS

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getApplianceType() {
        return applianceType;
    }

    public void setApplianceType(String applianceType) {
        this.applianceType = applianceType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getIssueTag() {
        return issueTag;
    }

    public void setIssueTag(String issueTag) {
        this.issueTag = issueTag;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    //  toString
    @Override
    public String toString() {
        return "DiagnosticQuestion [questionId=" + questionId
                + ", applianceType=" + applianceType
                + ", questionText=" + questionText
                + ", issueTag=" + issueTag
                + ", isTerminal=" + isTerminal
                + ", isRoot=" + isRoot + "]";
    }
}