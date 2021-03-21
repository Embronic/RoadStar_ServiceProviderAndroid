package com.roadstar_serviceprovider.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("provider_type")
    @Expose
    private String provider_type;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;

    public String getProvider_type() {
        return provider_type;
    }

    public void setProvider_type(String provider_type) {
        this.provider_type = provider_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ClassPojo [provider_type = " + provider_type + ", id = " + id + ", token = " + token + "]";
    }

}
