package com.paysez.library.Responses;

public class MakePaymentFirstResponse {

    private String transactionId;
    private String accuCardHolderId;
    private String accuGUID;
    private String accuHkey;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccuCardHolderId() {
        return accuCardHolderId;
    }

    public void setAccuCardHolderId(String accuCardHolderId) {
        this.accuCardHolderId = accuCardHolderId;
    }

    public String getAccuGUID() {
        return accuGUID;
    }

    public void setAccuGUID(String accuGUID) {
        this.accuGUID = accuGUID;
    }

    public String getAccuHkey() {
        return accuHkey;
    }

    public void setAccuHkey(String accuHkey) {
        this.accuHkey = accuHkey;
    }
}
