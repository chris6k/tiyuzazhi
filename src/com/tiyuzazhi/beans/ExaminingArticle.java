package com.tiyuzazhi.beans;

import java.util.Date;

/**
 * @author chris.xue
 */
public class ExaminingArticle extends ArticleMenu {
    private static final long serialVersionUID = 5717063565774216907L;
    private int id;
    private int state;
    private String summary;
    private String draftNo;
    private Date examineStart;
    private Date examineFinish;
    private int opId;
    private String opName;
    private String comment;
    private int conclusion;
    private int score;
    private int category;
    private String orgName;
    private int step;
    private String attachment;
    private String attachmentText;


    public ExaminingArticle() {
    }

    public String getAttachmentText() {
        return attachmentText;
    }

    public void setAttachmentText(String attachmentText) {
        this.attachmentText = attachmentText;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Date getExamineFinish() {
        return examineFinish;
    }

    public void setExamineFinish(Date examineFinish) {
        this.examineFinish = examineFinish;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "ExaminingArticle{" +
                "id=" + id +
                ", state=" + state +
                ", summary='" + summary + '\'' +
                ", draftNo='" + draftNo + '\'' +
                ", examineStart=" + examineStart +
                ", examineFinish=" + examineFinish +
                ", opId=" + opId +
                ", opName='" + opName + '\'' +
                ", comment='" + comment + '\'' +
                ", conclusion=" + conclusion +
                ", score=" + score +
                ", category=" + category +
                ", orgName='" + orgName + '\'' +
                ", step=" + step +
                ", attachment='" + attachment + '\'' +
                ", attachmentText='" + attachmentText + '\'' +
                "} " + super.toString();
    }
}
