package com.paysez.library;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errorcode")
    @Expose
    private String errorcode;
    @SerializedName("tran_id")
    @Expose
    private Integer tranId;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("modulus")
    @Expose
    private String modulus;
    @SerializedName("exponent")
    @Expose
    private String exponent;
    @SerializedName("Mermapid")
    @Expose
    private String mermapid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public Integer getTranId() {
        return tranId;
    }

    public void setTranId(Integer tranId) {
        this.tranId = tranId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public String getMermapid() {
        return mermapid;
    }

    public void setMermapid(String mermapid) {
        this.mermapid = mermapid;
    }

}
