package com.tiyuzazhi.beans;

import com.tiyuzazhi.utils.DatetimeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chris.xue
 */
public class Magazine implements Serializable {
    private Pattern pattern = Pattern.compile("(第[一二三四五六七八九十百千万0-9]期)");
    private int id;
    private String title;
    private String subTitle;
    private Date publishTime;
    private String publishNo;

    public Magazine() {
    }

    public Magazine(JSONObject obj) throws JSONException {
        this.id = obj.getInt("id");
        this.title = obj.getString("title");
        this.subTitle = obj.getString("subTitle");
        this.publishTime = DatetimeUtils.parse(obj.getString("publishTime"));
        this.publishNo = obj.getString("publishNo");
        Matcher matcher = pattern.matcher(publishNo);
        if (matcher.find()) {
            publishNo = matcher.group(0);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishNo() {
        return publishNo;
    }

    public void setPublishNo(String publishNo) {
        this.publishNo = publishNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", publishTime=" + publishTime +
                ", publishNo=" + publishNo +
                '}';
    }
}
