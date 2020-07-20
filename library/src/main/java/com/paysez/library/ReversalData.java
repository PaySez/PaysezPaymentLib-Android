package com.paysez.library;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReversalData implements Serializable {




//    card_num:4018060001038559
//    card_cvv:822
//    expiry_mm:02
//    expiry_yy:2023
//    env:livem
//    Transaction_id:E0110000000000920190905144518
//    merchant_id:E01100000000009
//    TransactionType:RV
//    Rev_reason:68


    public String card_num;


    public String card_cvv;


    public String expiry_mm;


    public String expiry_yy;


    public String env;


    public String Transaction_id;


    public String merchant_id;


    public String TransactionType;
    public String Rev_reason;

    public String amount;
    public String currency;
    private String timestamp;
    private String PaymentChannel;
    private String nameoncard;
    private String redirectionurl;
    private  String encData;
    private String SecretKey;
    private String IV;

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getIV() {
        return IV;
    }

    public void setIV(String IV) {
        this.IV = IV;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getCard_cvv() {
        return card_cvv;
    }

    public void setCard_cvv(String card_cvv) {
        this.card_cvv = card_cvv;
    }

    public String getExpiry_mm() {
        return expiry_mm;
    }

    public void setExpiry_mm(String expiry_mm) {
        this.expiry_mm = expiry_mm;
    }

    public String getExpiry_yy() {
        return expiry_yy;
    }

    public void setExpiry_yy(String expiry_yy) {
        this.expiry_yy = expiry_yy;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTransaction_id() {
        return Transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        Transaction_id = transaction_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getRev_reason() {
        return Rev_reason;
    }

    public void setRev_reason(String rev_reason) {
        Rev_reason = rev_reason;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentChannel() {
        return PaymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        PaymentChannel = paymentChannel;
    }

    public String getNameoncard() {
        return nameoncard;
    }

    public void setNameoncard(String nameoncard) {
        this.nameoncard = nameoncard;
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
}
