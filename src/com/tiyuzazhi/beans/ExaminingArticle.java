package com.tiyuzazhi.beans;

import java.util.Date;

/**
 * @author chris.xue
 */
public class ExaminingArticle extends ArticleMenu {
    private int id;
    private int state;
    private String summary;
    private String draftNo;
    private Date examineStart;
    private int opId;
    private String opName;
    private String comment;
    private int conclusion;
    private int score;


    public ExaminingArticle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(String draftNo) {
        this.draftNo = draftNo;
    }

    public Date getExamineStart() {
        return examineStart;
    }

    public void setExamineStart(Date examineStart) {
        this.examineStart = examineStart;
    }

    public int getOpId() {
        return opId;
    }

    public void setOpId(int opId) {
        this.opId = opId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getConclusion() {
        return conclusion;
    }

    public void setConclusion(int conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        return "ExaminingArticle{" +
                "id=" + id +
                ", state=" + state +
                ", summary='" + summary + '\'' +
                ", draftNo='" + draftNo + '\'' +
                ", examineStart=" + examineStart +
                ", opId=" + opId +
                ", opName='" + opName + '\'' +
                ", comment='" + comment + '\'' +
                ", conclusion=" + conclusion +
                ", score=" + score +
                "} " + super.toString();
    }
}
