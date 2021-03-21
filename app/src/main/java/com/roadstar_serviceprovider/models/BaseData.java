package com.roadstar_serviceprovider.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roadstar_serviceprovider.models.otp.OTPData;

public class BaseData {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + ", id = " + id + "]";
    }
}
