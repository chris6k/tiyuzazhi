package com.tiyuzazhi.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author chris.xue
 */
public class StatsDashboard implements Serializable {
    private int userCenterTaskNo;
    private int masterCenterTaskNo;
    private int editorTaskNo;
    private int chiefEditorTaskNo;

    public StatsDashboard() {
    }

    public StatsDashboard(JSONObject jsonObject) throws JSONException {
        userCenterTaskNo = jsonObject.getInt("author");
        masterCenterTaskNo = jsonObject.getInt("external");
        editorTaskNo = jsonObject.getInt("editor");
        chiefEditorTaskNo = jsonObject.getInt("ceditor");
    }

    public int getUserCenterTaskNo() {
        return userCenterTaskNo;
    }

    public void setUserCenterTaskNo(int userCenterTaskNo) {
        this.userCenterTaskNo = userCenterTaskNo;
    }

    public int getMasterCenterTaskNo() {
        return masterCenterTaskNo;
    }

    public void setMasterCenterTaskNo(int masterCenterTaskNo) {
        this.masterCenterTaskNo = masterCenterTaskNo;
    }

    public int getEditorTaskNo() {
        return editorTaskNo;
    }

    public void setEditorTaskNo(int editorTaskNo) {
        this.editorTaskNo = editorTaskNo;
    }

    public int getChiefEditorTaskNo() {
        return chiefEditorTaskNo;
    }

    public void setChiefEditorTaskNo(int chiefEditorTaskNo) {
        this.chiefEditorTaskNo = chiefEditorTaskNo;
    }

    @Override
    public String toString() {
        return "StatsDashboard{" +
                "userCenterTaskNo=" + userCenterTaskNo +
                ", masterCenterTaskNo=" + masterCenterTaskNo +
                ", editorTaskNo=" + editorTaskNo +
                ", chiefEditorTaskNo=" + chiefEditorTaskNo +
                '}';
    }
}
