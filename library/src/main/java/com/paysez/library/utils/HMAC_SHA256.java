package com.paysez.library.utils;


import android.util.Log;

import com.paysez.library.Responses.MakePaymentFirstResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC_SHA256 {
    private static HMAC_SHA256 instance;

    public static HMAC_SHA256 getInstance() {
        if (instance == null) {
            instance = new HMAC_SHA256();
        }
        return instance;
    }

    private HMAC_SHA256() {

    }

    public String getEncodedHashWithHmac256(MakePaymentFirstResponse paymentFirstResponse, String session) {




        String message = paymentFirstResponse.getTransactionId() + "&" + paymentFirstResponse.getAccuCardHolderId() + "&" + paymentFirstResponse.getAccuGUID() + "&" + session;
        String key = paymentFirstResponse.getAccuHkey();

//        Log.d("transactionId", "Testing Two" + paymentFirstResponse.transactionId);
//        Log.d("accuCardHolderId", "Testing Two" + paymentFirstResponse.accuCardHolderId);
//        Log.d("accuGUID", "Testing Two" + paymentFirstResponse.accuGUID);
//        Log.d("session", "Testing" + session);
//        Log.d("message", "Testing" + message);
//        Log.d("key", "Testing" + key);
        String messageDigest = generateHashWithHmac256(message, key);
        String encodedHmac = org.bouncycastle.util.encoders.Base64.toBase64String(messageDigest.getBytes());
        System.out.println("testing" + encodedHmac);

        Log.d("testtttttt", "sssssss" + encodedHmac);
        return encodedHmac;
    }

    public String generateHashWithHmac256(String message, String key) {
        try {
            final String hashingAlgorithm = "HmacSHA256"; // or "HmacSHA1", "HmacSHA512"

            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(), message.getBytes());

            String messageDigest = bytesToHex(bytes);
            System.out.println(messageDigest);
            return messageDigest;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] hmac(String algorithm, byte[] key, byte[] message)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    private String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public String getHMapKey(String transactionId, String accuGUID, String session, String accu000, String accuHkey) {

        String message = transactionId + "&" + accuGUID + "&" + session + "&" + accu000;
        String key = accuHkey;

        Log.d("transactionId ", "Testing Two getHMapKey" + transactionId);
        Log.d("accuCardHolderId", "Testing Two getHMapKey" + accuGUID);
        Log.d("accuResponseCode", "Testing Two getHMapKey" + accu000);
        Log.d("session", "Testing getHMapKey" + session);
        Log.d("message", "Testing getHMapKey" + message);
        Log.d("key", "Testing getHMapKey" + key);
        String messageDigest = generateHashWithHmac256(message, key);
        String encodedHmac = org.bouncycastle.util.encoders.Base64.toBase64String(messageDigest.getBytes());
        System.out.println("testing" + encodedHmac);

        //Log.d("testtttttt", "sssssss" + encodedHmac);
        return encodedHmac;
    }

}
