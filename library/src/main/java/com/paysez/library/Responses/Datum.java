package com.paysez.library.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

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
    @SerializedName("trans_id")
    @Expose
    private String transId;


    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("link_creation_date")
    @Expose
    private String linkCreationDate;
    @SerializedName("link_expiry_date")
    @Expose
    private String linkExpiryDate;
    @SerializedName("paylink_url")
    @Expose
    private String paylinkUrl;
    @SerializedName("link_expiry_status")
    @Expose
    private String linkExpiryStatus;
    @SerializedName("pay_status_code")
    @Expose
    private String payStatusCode;
    @SerializedName("rrn_no")
    @Expose
    private String rrnNo;
    @SerializedName("trans_type")
    @Expose
    private String transType;
    @SerializedName("trans_datetime")
    @Expose
    private String transDatetime;
    @SerializedName("masked_card")
    @Expose
    private String maskedCard;


    public String getPayStatusCode() {
        return payStatusCode;
    }
    public void setPayStatusCode(String payStatusCode) {
        this.payStatusCode = payStatusCode;
    }
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

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }


    public String getLinkCreationDate() {
        return linkCreationDate;
    }

    public void setLinkCreationDate(String linkCreationDate) {
        this.linkCreationDate = linkCreationDate;
    }

    public String getLinkExpiryDate() {
        return linkExpiryDate;
    }

    public void setLinkExpiryDate(String linkExpiryDate) {
        this.linkExpiryDate = linkExpiryDate;
    }

    public String getPaylinkUrl() {
        return paylinkUrl;
    }

    public void setPaylinkUrl(String paylinkUrl) {
        this.paylinkUrl = paylinkUrl;
    }

    public String getLinkExpiryStatus() {
        return linkExpiryStatus;
    }

    public void setLinkExpiryStatus(String linkExpiryStatus) {
        this.linkExpiryStatus = linkExpiryStatus;
    }

    public String getRrnNo() {
        return rrnNo;
    }

    public void setRrnNo(String rrnNo) {
        this.rrnNo = rrnNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransDatetime() {
        return transDatetime;
    }

    public void setTransDatetime(String transDatetime) {
        this.transDatetime = transDatetime;
    }

    public String getMaskedCard() {
        return maskedCard;
    }

    public void setMaskedCard(String maskedCard) {
        this.maskedCard = maskedCard;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}