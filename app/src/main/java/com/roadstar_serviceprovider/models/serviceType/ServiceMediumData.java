package com.roadstar_serviceprovider.models.serviceType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceMediumData {

    @SerializedName("service_id")
    @Expose
    private String service_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

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
        return "ClassPojo [service_id = " + service_id + ", name = " + name + ", id = " + id + "]";
    }


}


