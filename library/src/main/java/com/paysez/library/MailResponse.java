package com.paysez.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MailResponse {

    @SerializedName("mail_status")
    @Expose
    private String mailStatus;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
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