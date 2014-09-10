package com.tiyuzazhi.beans;

import java.io.Serializable;

/**
 * @author chris.xue
 */
public class User implements Serializable {
    private int id;
    private String name;
    private String iconPath;
    private String company;
    private String address;
    private int favCount;
    private int msgCount;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", favCount=" + favCount +
                ", msgCount=" + msgCount +
                '}';
    }
}
