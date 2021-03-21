package com.roadstar_serviceprovider.models.serviceMedium;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roadstar_serviceprovider.models.serviceType.ServiceMediumData;

import java.util.ArrayList;

public class ServiceMediumResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("data")
    @Expose
    private ArrayList<ServiceMediumData> data;

    @SerializedName("message")
    @Expose
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<ServiceMediumData> getData() {
        return data;
    }

    public void setData(ArrayList<ServiceMediumData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClassPojo [code = " + code + ", data = " + data + ", message = " + message + "]";
    }

}
