package com.paysez.library;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class PaymentWebviewActivity extends Activity {
    int BACK_PRESSED = 0;
    String redirectionurl;
    String merchant_id;
    String amount;
    String Transaction_id;
    WebView webview;
    String MsgId, key, initVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new MyWebViewClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setCacheMode(LOAD_NO_CACHE);
        webview.getSettings().setDomStorageEnabled(true);
        webview.clearHistory();
        webview.clearCache(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(false);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                // Your custom code.
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                return super.onJsAlert(view, url, message, result);


            }
        });

        Intent data = getIntent();
        merchant_id = data.getStringExtra("merchant_id");
        amount = data.getStringExtra("amount");
        key = data.getStringExtra("key");
        initVector = data.getStringExtra("iv");

        key = sha256(key).substring(0, 32);
        initVector = sha256(initVector).substring(0, 16);


        String timestamp = data.getStringExtra("timestamp");
        Transaction_id = data.getStringExtra("Transaction_id");
        redirectionurl = data.getStringExtra("redirectionurl");
        MsgId = data.getStringExtra("MsgId");

        openURL(merchant_id, amount, Transaction_id, redirectionurl, timestamp, MsgId);
    }


    /**
     * Opens the URL in a browser.
     */

    private void openURL(String merchant_id, String amount, String Transaction_id, String redirectionurl, String timestamp, String MsgId) {

        String postData;
        try {

            postData = "&merchant_id=" + merchant_id
                    + "&amount=" + amount
                    + "&currency=" + "INR"
                    + "&env=" + "live"
                    + "&timestamp=" + timestamp
                    + "&Transaction_id=" + "CUB" + Transaction_id
                    + "&TransactionType=" + "AA"
                    + "&location=" + "india"
                    + "&PaymentChannel=" + "Pg"
                    + "&terminal_id=" + "O0000010"
                    + "&MsgId=" + MsgId
                    //+ "&payee_vpa_addrs=" + "OzE000010@CUBUPI"
                    + "&payee_vpa_addrs=" + "EZE0000226@cubupi"
                    + "&redirectionurl=" + redirectionurl;
            webview.postUrl("https://pguat.credopay.info/credopay/CPWebPG.php", postData.getBytes());
           // webview.postUrl("https://pg.credopay.in/credopay/CPWebPG.php", postData.getBytes());
            //
            //webview.postUrl("https://pguat.credopay.info/credopay/Upipay_test.php",postData.getBytes());
            webview.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ++BACK_PRESSED;

        if (BACK_PRESSED < 2) {

            Toast.makeText(getApplicationContext(), "press again to cancel the transaction", Toast.LENGTH_LONG).show();
        }
        if (BACK_PRESSED == 2) {
            BACK_PRESSED = 0;


            Intent intent = getIntent();

            intent.putExtra("full_response", "null");
            intent.putExtra("responsecode", "80");
            intent.putExtra("merchant_id", merchant_id);
            intent.putExtra("transaction_id", Transaction_id);
            intent.putExtra("amount", amount);
            intent.putExtra("success", "failure");
            intent.putExtra("errordesc", "user back pressed");
            intent.putExtra("refNbr", "null");
            intent.putExtra("rrn", "null");
            intent.putExtra("mode", "null");
            intent.putExtra("status", "failure");
            setResult(2, intent);
            //finish();

            if (webview != null) {
                webview.stopLoading();
            }

            super.onBackPressed();

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class MyWebViewClient extends WebViewClient {


        Dialog dialog = new Dialog(getApplicationContext());

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);



        }


        @Override
        public void onPageFinished(WebView view, String url) {
            // Log.d("onPageFinished", "WebViewClient: onPageFinished: url: " + url);

            if (url.contains("encData")) {


               String data = decrypt(URLDecoder.decode(url.split("encData=")[1]), key, initVector);
                if (data.contains("success=true") && data.contains("mode=CP"))
                {
                    Toast.makeText(getApplicationContext(), "Transaction Success", Toast.LENGTH_LONG).show();
                    data= "https://google.com?"+data;
                    Map<String, List<String>> values = getQueryParams(data);
                    List<String> responsecode = values.get("responsecode");
                    List<String> merchant_id = values.get("merchant_id");
                    List<String> transaction_id = values.get("transaction_id");
                    List<String> amount = values.get("amount");
                    List<String> success = values.get("success");
                    List<String> errordesc = values.get("errordesc");
                    List<String> rrn = values.get("rrn");
                    List<String> refNbr = values.get("refNbr");
                    List<String> mode = values.get("mode");
                    Intent intent = getIntent();

                    intent.putExtra("full_response", data);
                    intent.putExtra("responsecode", responsecode.get(0));
                    intent.putExtra("merchant_id", merchant_id.get(0));
                    intent.putExtra("transaction_id", transaction_id.get(0));
                    intent.putExtra("amount", amount.get(0));
                    intent.putExtra("success", success.get(0));
                    intent.putExtra("errordesc", errordesc.get(0));
                    intent.putExtra("refNbr", refNbr.get(0));
                    intent.putExtra("rrn", rrn.get(0));
                    intent.putExtra("mode", mode.get(0));
                    intent.putExtra("status", "success");
                    setResult(2, intent);
                    finish();

                }

                if (data.contains("success=false") && data.contains("mode=CP")) {
                    Toast.makeText(getApplicationContext(), "Transaction Failure", Toast.LENGTH_LONG).show();
                    data= "https://www.google.com?"+data;
                    Map<String, List<String>> values = getQueryParams(data);
                    List<String> responsecode = values.get("responsecode");
                    List<String> merchant_id = values.get("merchant_id");
                    List<String> transaction_id = values.get("transaction_id");
                    List<String> amount = values.get("amount");
                    List<String> success = values.get("success");
                    List<String> errordesc = values.get("errordesc");
                    List<String> refNbr = values.get("refNbr");
                    List<String> mode = values.get("mode");

                    Intent intent = getIntent();

                    intent.putExtra("full_response", data);

                    intent.putExtra("responsecode", responsecode.get(0));
                    intent.putExtra("merchant_id", merchant_id.get(0));
                    intent.putExtra("transaction_id", transaction_id.get(0));
                    intent.putExtra("amount", amount.get(0));
                    intent.putExtra("success", success.get(0));
                    intent.putExtra("errordesc", errordesc.get(0));
                    intent.putExtra("refNbr", refNbr.get(0));
                    intent.putExtra("rrn", "null");
                    intent.putExtra("mode", mode.get(0));
                    intent.putExtra("status", "failure");
                    setResult(2, intent);
                    finish();

                }

                if (data.contains("success=Success") && data.contains("mode=NB")) {
                    data= "https://www.google.com?"+data;
                    Toast.makeText(getApplicationContext(), "Transaction Success netbank", Toast.LENGTH_LONG).show();

                    Map<String, List<String>> values = getQueryParams(data);
                    List<String> responsecode = values.get("responsecode");
                    List<String> merchant_id = values.get("merchant_id");
                    List<String> transaction_id = values.get("transaction_id");
                    List<String> amount = values.get("amount");
                    List<String> success = values.get("success");
                    List<String> errordesc = values.get("errordesc");
                    List<String> refNbr = values.get("refNbr");
                    List<String> mode = values.get("mode");

                    Intent intent = getIntent();

                    intent.putExtra("full_response", data);
                    intent.putExtra("responsecode", responsecode.get(0));
                    intent.putExtra("merchant_id", merchant_id.get(0));
                    intent.putExtra("transaction_id", transaction_id.get(0));
                    intent.putExtra("amount", amount.get(0));
                    intent.putExtra("success", success.get(0));
                    intent.putExtra("errordesc", errordesc.get(0));
                    intent.putExtra("refNbr", refNbr.get(0));
                    intent.putExtra("rrn", "null");
                    intent.putExtra("mode", mode.get(0));
                    intent.putExtra("status", "success");
                    setResult(2, intent);
                    finish();

                }

                if (data.contains("success=Failed") && data.contains("mode=NB"))
                {
                    data= "https://www.google.com?"+data;
                    Toast.makeText(getApplicationContext(), "Transaction Failed netbank", Toast.LENGTH_LONG).show();

                    Map<String, List<String>> values = getQueryParams(data);
                    List<String> responsecode = values.get("responsecode");
                    List<String> merchant_id = values.get("merchant_id");
                    List<String> transaction_id = values.get("transaction_id");
                    List<String> amount = values.get("amount");
                    List<String> success = values.get("success");
                    List<String> errordesc = values.get("errordesc");
                    List<String> refNbr = values.get("refNbr");
                    List<String> mode = values.get("mode");

                    Intent intent = getIntent();

                    intent.putExtra("full_response", data);
                    intent.putExtra("responsecode", responsecode.get(0));
                    intent.putExtra("merchant_id", merchant_id.get(0));
                    intent.putExtra("transaction_id", transaction_id.get(0));
                    intent.putExtra("amount", amount.get(0));
                    intent.putExtra("success", success.get(0));
                    intent.putExtra("errordesc", errordesc.get(0));
                    intent.putExtra("refNbr", refNbr.get(0));
                    intent.putExtra("rrn", "null");
                    intent.putExtra("mode", mode.get(0));
                    intent.putExtra("status", "failure");
                    setResult(2, intent);
                    finish();


                }

            }

            // view.scrollTo(50, view.getContentHeight());


        }
    }

    public Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<String, List<String>>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    List<String> values = params.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(key, values);
                    }
                    values.add(value);
                }
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }



    public static String decrypt(String encrypted, String key, String initVector) {

        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            System.out.println(new String(original));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }




}
