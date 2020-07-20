package com.paysez.library.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SMS_Response {

    @SerializedName("sms_status")
    @Expose
    private String smsStatus;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

}