package com.tiyuzazhi.beans;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String email;
    private String mobile;
    private int favCount;
    private int msgCount;
    private int role;

    public User() {
    }

    public User(JSONObject jsonObject) throws JSONException {
        setId(jsonObject.getInt("id"));
        setAddress(jsonObject.getString("address"));
        setCompany(jsonObject.getString("company"));
        setEmail(jsonObject.getString("email"));
        setName(jsonObject.getString("name"));
        setIconPath(jsonObject.getString("iconPath"));
        setMobile(jsonObject.getString("mobile"));
        if (jsonObject.has("favCount"))
            setFavCount(jsonObject.getInt("favCount"));
        if (jsonObject.has("msgCount"))
            setMsgCount(jsonObject.getInt("msgCount"));
        String isAuthor = jsonObject.getString("role_author");
        String isExternal = jsonObject.getString("role_external");
        String type = jsonObject.getString("type");
        int role = (TextUtils.equals("T", isAuthor) ? 1 : 0)
                | (TextUtils.equals("U", type) ? 2 : 0)
                | (TextUtils.equals("C", type) ? 4 : 0)
                | (TextUtils.equals("T", isExternal) ? 8 : 0);
        setRole(role);
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", favCount=" + favCount +
                ", msgCount=" + msgCount +
                '}';
    }


}
