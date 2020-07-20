package com.paysez.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("env")
    @Expose
    private String env;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("Transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("TransactionType")
    @Expose
    private String transactionType;
    @SerializedName("PaymentChannel")
    @Expose
    private String paymentChannel;
    @SerializedName("redirectionurl")
    @Expose
    private String redirectionurl;
    @SerializedName("encData")
    @Expose
    private String encData;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("nameoncard")
    @Expose
    private String nameoncard;
    @SerializedName("card_num")
    @Expose
    private String cardNum;
    @SerializedName("expiry_mm")
    @Expose
    private String expiryMm;
    @SerializedName("expiry_yy")
    @Expose
    private String expiryYy;
    @SerializedName("card_cvv")
    @Expose
    private String cardCvv;
    @SerializedName("ponumber")
    @Expose
    private String ponumber;
    @SerializedName("buyerEmail")
    @Expose
    private String buyerEmail;
    @SerializedName("buyerPhone")
    @Expose
    private String buyerPhone;
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("buyerFirstName")
    @Expose
    private String buyerFirstName;
    @SerializedName("buyerLastName")
    @Expose
    private String buyerLastName;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getRedirectionurl() {
        return redirectionurl;
    }

    public void setRedirectionurl(String redirectionurl) {
        this.redirectionurl = redirectionurl;
    }

    public String getEncData() {
        return encData;
    }

    public void setEncData(String encData) {
        this.encData = encData;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getNameoncard() {
        return nameoncard;
    }

    public void setNameoncard(String nameoncard) {
        this.nameoncard = nameoncard;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpiryMm() {
        return expiryMm;
    }

    public void setExpiryMm(String expiryMm) {
        this.expiryMm = expiryMm;
    }

    public String getExpiryYy() {
        return expiryYy;
    }

    public void setExpiryYy(String expiryYy) {
        this.expiryYy = expiryYy;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public String getPonumber() {
        return ponumber;
    }

    public void setPonumber(String ponumber) {
        this.ponumber = ponumber;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getBuyerFirstName() {
        return buyerFirstName;
    }

    public void setBuyerFirstName(String buyerFirstName) {
        this.buyerFirstName = buyerFirstName;
    }

    public String getBuyerLastName() {
        return buyerLastName;
    }

    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }

}
