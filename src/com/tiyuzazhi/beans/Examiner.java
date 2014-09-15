package com.tiyuzazhi.beans;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chris.xue
 */
public class Examiner extends User {
    private int type;
    private int status;

    public Examiner() {

    }

    public Examiner(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        this.status = jsonObject.getInt("status");
        this.type = jsonObject.getInt("type");
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Examiner{" +
                "type=" + type +
                ", status=" + status +
                "} " + super.toString();
    }
}
