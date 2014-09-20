package com.tiyuzazhi.beans;

import java.io.Serializable;

/**
 * @author chris.xue
 */
public class ArticleMenu implements Serializable {

    private static final long serialVersionUID = -1961751315009657279L;
    private int id;
    private String title;
    private String author;

    public ArticleMenu() {
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
