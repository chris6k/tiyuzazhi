package com.tiyuzazhi.beans;

import java.util.Date;

/**
 * @author chris.xue
 */
public class ExaminingArticle extends Article {
    private int id;
    private int state;
    private String summary;
    private String draftNo;
    private Date examineStart;
    private int editorId;
    private Date editorExamDate;
    private Date masterExamDate;
    private Date chiefExamDate;
    private int masterId;
    private int chiefId;
    private String editorComment;
    private String masterComment;
    private String chiefComment;

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

    public int getEditorId() {
        return editorId;
    }

    public void setEditorId(int editorId) {
        this.editorId = editorId;
    }

    public Date getEditorExamDate() {
        return editorExamDate;
    }

    public void setEditorExamDate(Date editorExamDate) {
        this.editorExamDate = editorExamDate;
    }

    public Date getMasterExamDate() {
        return masterExamDate;
    }

    public void setMasterExamDate(Date masterExamDate) {
        this.masterExamDate = masterExamDate;
    }

    public Date getChiefExamDate() {
        return chiefExamDate;
    }

    public void setChiefExamDate(Date chiefExamDate) {
        this.chiefExamDate = chiefExamDate;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getChiefId() {
        return chiefId;
    }

    public void setChiefId(int chiefId) {
        this.chiefId = chiefId;
    }

    public String getEditorComment() {
        return editorComment;
    }

    public void setEditorComment(String editorComment) {
        this.editorComment = editorComment;
    }

    public String getMasterComment() {
        return masterComment;
    }

    public void setMasterComment(String masterComment) {
        this.masterComment = masterComment;
    }

    public String getChiefComment() {
        return chiefComment;
    }

    public void setChiefComment(String chiefComment) {
        this.chiefComment = chiefComment;
    }

    @Override
    public String toString() {
        return "ExaminingArticle{" +
                "id=" + id +
                ", state=" + state +
                ", summary='" + summary + '\'' +
                ", draftNo='" + draftNo + '\'' +
                ", examineStart=" + examineStart +
                ", editorId=" + editorId +
                ", editorExamDate=" + editorExamDate +
                ", masterExamDate=" + masterExamDate +
                ", chiefExamDate=" + chiefExamDate +
                ", masterId=" + masterId +
                ", chiefId=" + chiefId +
                ", editorComment='" + editorComment + '\'' +
                ", masterComment='" + masterComment + '\'' +
                ", chiefComment='" + chiefComment + '\'' +
                '}';
    }
}
