package com.tiyuzazhi.beans;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Date examineEnd;
    private Date submitDate;
    private int opId;
    private String opName;
    private String comment;
    private int conclusion;
    private String score;
    private int category;
    private String orgName;
    private int step;
    private String attachment;
    private String attachmentText;


    public ExaminingArticle() {
    }

    public ExaminingArticle(JSONObject jsonObject) throws JSONException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        id = jsonObject.getInt("id");
        if (jsonObject.has("summary"))
        summary = jsonObject.getString("summary");
        if (jsonObject.has("draftNo"))
        draftNo = jsonObject.getString("draftNo");
        String tempDate = null;
        if (jsonObject.has("examineStart"))
        tempDate = jsonObject.getString("examineStart");
        examineStart = (TextUtils.isEmpty(tempDate) || TextUtils.equals("null", tempDate)) ? null : format.parse(tempDate);

        if (jsonObject.has("examineEnd"))
        tempDate = jsonObject.getString("examineEnd");
        examineEnd = (TextUtils.isEmpty(tempDate) || TextUtils.equals("null", tempDate)) ? null : format.parse(tempDate);

        if (jsonObject.has("examineFinish"))
        tempDate = jsonObject.getString("examineFinish");
        examineFinish = (TextUtils.isEmpty(tempDate) || TextUtils.equals("null", tempDate)) ? null : format.parse(tempDate);

        if (jsonObject.has("submitDate"))
        tempDate = jsonObject.getString("submitDate");
        submitDate = (TextUtils.isEmpty(tempDate) || TextUtils.equals("null", tempDate)) ? null : format.parse(tempDate);
        state = examineFinish == null ? 0 : 1;
        conclusion = state;
        if (jsonObject.has("opId"))
        opId = jsonObject.getInt("opId");
        if (jsonObject.has("opName"))
        opName = jsonObject.getString("opName");
        if (jsonObject.has("comment"))
        comment = jsonObject.getString("comment");
        if (TextUtils.isEmpty(comment) || TextUtils.equals("null", comment.toLowerCase())) {
            comment = "";
        }
        if (jsonObject.has("score"))
        score = jsonObject.getString("score");
        if (jsonObject.has("comment"))
        if (jsonObject.has("category") && !TextUtils.equals("null", jsonObject.getString("category").toLowerCase())) {
            category = jsonObject.getInt("category");
        }
        step = jsonObject.getInt("step");
        if (jsonObject.has("orgName")) {
            orgName = jsonObject.getString("orgName");
        }
        if (jsonObject.has("title")) {
            setTitle(jsonObject.getString("title"));
        }
        if (jsonObject.has("author")) {
            setAuthor(jsonObject.getString("author"));
        }

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
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

    public Date getExamineEnd() {
        return examineEnd;
    }

    public void setExamineEnd(Date examineEnd) {
        this.examineEnd = examineEnd;
    }


    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
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
                ", examineEnd=" + examineEnd +
                ", submitDate=" + submitDate +
                ", opId=" + opId +
                ", opName='" + opName + '\'' +
                ", comment='" + comment + '\'' +
                ", conclusion=" + conclusion +
                ", score='" + score + '\'' +
                ", category=" + category +
                ", orgName='" + orgName + '\'' +
                ", step=" + step +
                ", attachment='" + attachment + '\'' +
                ", attachmentText='" + attachmentText + '\'' +
                '}';
    }
}
