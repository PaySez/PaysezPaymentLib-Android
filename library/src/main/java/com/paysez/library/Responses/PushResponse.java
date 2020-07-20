package com.paysez.library.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushResponse  {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;
    @SerializedName("paylink")
    @Expose
    private String paylink;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPaylink() {
        return paylink;
    }

    public void setPaylink(String paylink) {
        this.paylink = paylink;
    }

}