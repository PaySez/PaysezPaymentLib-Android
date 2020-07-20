package com.paysez.library.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    public String dec(String key, String data) {
        //System.out.println(System.currentTimeMillis());
        //System.out.println(key);
        try {
            // byte[] decoded = Base64.decodeBase64(key);


            String sss = Decrypt(data, key);
            System.out.println(System.currentTimeMillis());
            return sss;
        } catch (Exception e) {

            e.printStackTrace();
            return e.toString();

        }
    }

    public String enc(String key, String data) {

        //System.out.println(System.currentTimeMillis());
        //System.out.println(key);
        try {
            // byte[] decoded = Base64.decodeBase64(key);

            // System.out.println("Decoded message: "+new String(decoded, "UTF-8") + "\n");
            String sss = Encrypt(data, key);
            System.out.println(sss);
            System.out.println(System.currentTimeMillis());
            return sss;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }


    }

    public static String Encrypt(String plaintext, String sharedkey) throws Exception {
        byte[] keysharedkey = null;
        try {
            keysharedkey = sharedkey.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] sharedvector = {8, 7, 5, 6, 4, 1, 2, 3, 18, 17, 15, 16, 14, 11, 12, 13};

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String str = new String(sharedvector, StandardCharsets.UTF_8);
        }
        //System.out.println("IV : "+str);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keysharedkey, "AES"), new IvParameterSpec(sharedvector));
        byte[] encrypted = c.doFinal(plaintext.getBytes("UTF-8"));
        String strret = encodeBase64URLSafeString(encrypted);
        strret = strret.replace("\n", "");
        return URLEncoder.encode(strret, "UTF-8");
    }

    private static String encodeBase64URLSafeString(byte[] binaryData) {

        return android.util.Base64.encodeToString(binaryData, 0);

    }
    private static byte[] decodebase64(String s) {

        return android.util.Base64.decode(s,0);

    }
    public static String Decrypt(String ciphertext, String sharedkey) throws Exception {
        byte[] keysharedkey = null;
        try {
            keysharedkey = sharedkey.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] sharedvector = {8, 7, 5, 6, 4, 1, 2, 3, 18, 17, 15, 16, 14, 11, 12, 13};
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keysharedkey, "AES"), new IvParameterSpec(sharedvector));
        byte[] decrypted = c.doFinal(decodebase64(URLDecoder.decode(ciphertext, "UTF-8")));
        return new String(decrypted, "UTF-8");
    }




    public static String  getSHA256(String input) throws NoSuchAlgorithmException
    {

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


}






