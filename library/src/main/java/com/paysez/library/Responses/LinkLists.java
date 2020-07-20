package com.paysez.library.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LinkLists implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("purchase_purpose")
    @Expose
    private String purchasePurpose;
    @SerializedName("link_creation_date")
    @Expose
    private String linkCreationDate;
    @SerializedName("link_end_date")
    @Expose
    private String linkEndDate;
    @SerializedName("link_expiry_status")
    @Expose
    private String linkExpiryStatus;
    @SerializedName("paylink_url")
    @Expose
    private String paylinkUrl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurchasePurpose() {
        return purchasePurpose;
    }

    public void setPurchasePurpose(String purchasePurpose) {
        this.purchasePurpose = purchasePurpose;
    }

    public String getLinkCreationDate() {
        return linkCreationDate;
    }

    public void setLinkCreationDate(String linkCreationDate) {
        this.linkCreationDate = linkCreationDate;
    }

    public String getLinkEndDate() {
        return linkEndDate;
    }

    public void setLinkEndDate(String linkEndDate) {
        this.linkEndDate = linkEndDate;
    }

    public String getLinkExpiryStatus() {
        return linkExpiryStatus;
    }

    public void setLinkExpiryStatus(String linkExpiryStatus) {
        this.linkExpiryStatus = linkExpiryStatus;
    }

    public String getPaylinkUrl() {
        return paylinkUrl;
    }

    public void setPaylinkUrl(String paylinkUrl) {
        this.paylinkUrl = paylinkUrl;
    }

}
