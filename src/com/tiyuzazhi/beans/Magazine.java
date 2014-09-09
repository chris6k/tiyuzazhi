package com.tiyuzazhi.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chris.xue
 */
public class Magazine implements Serializable {
    private int id;
    private String title;
    private String publisher;
    private Date publishTime;
    private int publishNo;

    public Magazine() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getPublishNo() {
        return publishNo;
    }

    public void setPublishNo(int publishNo) {
        this.publishNo = publishNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishTime=" + publishTime +
                ", publishNo=" + publishNo +
                '}';
    }
}
