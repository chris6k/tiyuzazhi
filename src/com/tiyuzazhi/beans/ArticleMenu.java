package com.tiyuzazhi.beans;

import com.tiyuzazhi.utils.TiHttp;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author chris.xue
 */
public class ArticleMenu implements Serializable {

    private static final long serialVersionUID = -1961751315009657279L;
    private int id;
    private String title;
    private String author;
    private String summary;
    private String bsummary;
    private String keyword;
    private String attachment;

    public ArticleMenu() {
    }

    public ArticleMenu(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        title = jsonObject.getString("title");
        author = jsonObject.getString("author");
        if (jsonObject.has("summary")) {
            summary = jsonObject.getString("summary");
        } else {
            summary = "";
        }
        if (jsonObject.has("bsummary")) {
            Object temp = jsonObject.get("bsummary");
        }
        if (jsonObject.has("keyword")) {
            keyword = jsonObject.getString("keyword");
        }
        if (jsonObject.has("attachment")) {
            attachment = TiHttp.HOST + "/" + jsonObject.getString("attachment");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getKeyword() {
        return keyword;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "ArticleMenu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

}
