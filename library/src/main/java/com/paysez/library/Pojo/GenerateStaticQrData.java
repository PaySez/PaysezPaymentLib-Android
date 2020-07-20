package com.paysez.library.Pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class GenerateStaticQrData {

    private String VPA;
    private String name;
    private String mode;
    private String sign;
    private String originId;
    private String upiId;
    private String secretKey;
    private String cu;
    private String signature;
    private String am;
    private String tr;


    public String getAm() {
        return am;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCu() {
        return cu;
    }

    public void setCu(String cu) {
        this.cu = cu;
    }

    public String getVPA() {
        return VPA;
    }

    public void setVPA(String VPA) {
        this.VPA = VPA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String GenerateMapStatic(GenerateStaticQrData data) throws UnsupportedEncodingException {
        String fulldata = "";
        Map<String, String> map = new HashMap<>();
        map.put("pa", data.getVPA());
        map.put("pn", data.getName());
        map.put("cu", data.getCu());
        map.put("mode", data.getMode());
        map.put("sign", data.getSign());
        map.put("orgid", data.getOriginId());
        //map.put("sign", data.getSignature());

        for (Map.Entry<String, String> parameter : map.entrySet()) {

            final String encodedKey = URLEncoder.encode(parameter.getKey().toString(), "UTF-8");
            final String encodedValue = URLEncoder.encode(parameter.getValue().toString(), "UTF-8");

            fulldata += "&" + encodedKey + "=" + encodedValue;
        }


        return data.getUpiId() + fulldata + "&sign=" + URLEncoder.encode(data.getSignature(), "UTF-8");
    }

    public String GenerateMapDynamic(GenerateStaticQrData data) throws UnsupportedEncodingException {
        String fulldata = "";
        Map<String, String> map = new HashMap<>();
        map.put("pa", data.getVPA());
        map.put("pn", data.getName());
        map.put("cu", data.getCu());
        map.put("mode", data.getMode());
        map.put("sign", data.getSign());
        map.put("orgid", data.getOriginId());
        map.put("am",data.getAm());
        map.put("tr",data.getTr());
        //map.put("sign", data.getSignature());

        for (Map.Entry<String, String> parameter : map.entrySet()) {

            final String encodedKey = URLEncoder.encode(parameter.getKey().toString(), "UTF-8");
            final String encodedValue = URLEncoder.encode(parameter.getValue().toString(), "UTF-8");

            fulldata += "&" + encodedKey + "=" + encodedValue;
        }


        return data.getUpiId() + fulldata + "&sign=" + URLEncoder.encode(data.getSignature(), "UTF-8");
    }


}
