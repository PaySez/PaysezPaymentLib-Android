package com.paysez.library.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


//    {"merchant_id":"E01030000000008","user_name":"Transtrac"}
//{"status":"00","mid":"E01010000000133","user_name":"TransTracTechnology"}



    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("mid")
    private String merchant_id;

    @Expose
    @SerializedName("user_name")
    private String user_name;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
