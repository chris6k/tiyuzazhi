package com.tiyuzazhi.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author kun
 */
public class Notice implements Serializable {
    private int id;
    private String title;
    private String summary;
    private static final long serialVersionUID = 3328099869721708478L;

    public Notice() {
    }

    public Notice(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("id")) {
            id = jsonObject.getInt("id");
        }
        if (jsonObject.has("title")) {
            title = jsonObject.getString("title");
        }
        if (jsonObject.has("summary")) {
            summary = jsonObject.getString("summary");
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
