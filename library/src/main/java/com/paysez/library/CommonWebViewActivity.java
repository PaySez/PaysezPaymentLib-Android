package com.paysez.library;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class CommonWebViewActivity extends Activity {
    WebView webview;

    private class MyWebViewClient extends WebViewClient {
        private int webViewPreviousState;
        private final int PAGE_STARTED = 0x1;
        private final int PAGE_REDIRECTED = 0x2;

        Dialog dialog = new Dialog(getApplicationContext());

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webViewPreviousState = PAGE_STARTED;
            Log.d("asdasdsds", "WebViewClient: onPageStarted:  url: " + url);
            if (url.contains("https://pg.credopay.net/resp.php?")) {

                if (url.contains("success=true")) {
                    Toast.makeText(getApplicationContext(), "Transaction Success", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Transaction Failure", Toast.LENGTH_LONG).show();
                }

            }

        }


        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("asdasdsds", "WebViewClient: onPageFinished: url: " + url);
            if (webViewPreviousState == PAGE_STARTED) {
                //                dialog.dismiss();
                //                dialog = null;

            }

            if(url.contains("success=Success"))
            {

                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                finish();


            }
            if(url.contains("success=Failed"))
            {

                Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                finish();


            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
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
        String amount = getIntent().getStringExtra("amount");
        String id = getIntent().getStringExtra("id");
        openURL(amount, id);
    }

    private void openURL(String amount, String id) {


        String postData;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String time = dateFormat.format(date);

            String merchant_id = id;
            //time="20190705201134";

            postData =
                    //"ponumber=" + time
                    "&merchant_id=" + merchant_id +
                            "&amount=" + amount +
                            "&currency=" + "INR" +
                            "&env=" + "live" +
                            "&timestamp=" + time +
                            "&Transaction_id=" + merchant_id + time +
                            "&TransactionType=" + "AA" +
                            "&PaymentChannel=" + "Pg" +
                            "&redirectionurl=" + "https://paymentgateway.test.credopay.in/shop/checkout.php?" +
                            "&buyerEmail=" + "buyer@example.com" +
                            "&buyerPhone=" + "9874563210" +
                            "&orderid=" + merchant_id + time +
                            "&buyerFirstName=" + "SampleFirst" +
                            "&buyerLastName=" + "SampleFirst" +
                            "&WIDout_trade_no=" + "test20200206162831" +
                            "&WIDsubject=" + "test123" +
                            "&WIDtotal_fee=" + "1" +
                            "&WIDbody=" + "test" +
                            "&WIDproduct_code=" + "NEW_OVERSEAS_SELLER" +
                            "&tran_req_type=" + "cb1" +
                            "&payment_method=" + "smartro" +
                            "&ponumber=" + time;


            //  + "&encData=" + URLencode(AESEncrypt(encdata));
            //https://pguat.credopay.info/credopay/CPWebPG.php
          //  Log.v("checkval", postData);
        //    webview.postUrl("http://paymentgateway.test.credopay.in/testspaysez/payform_scratch_othr.php", postData.getBytes());

            webview.postUrl("https://pguat.credopay.info/credopay/CPWebPG.php",null);
            webview.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //        {
        //            "ponumber": "20200206162831",
        //                "merchant_id": "E01010000000038",
        //                "amount": "1",
        //                "currency": "INR",
        //                "env": "live",
        //                "timestamp": "20200206162831",
        //                "Transaction_id": "E0101000000003820200206162831",
        //                "TransactionType": "AA",
        //                "redirectionurl": "https://paymentgateway.test.credopay.in/shop/checkout.php?",
        //                "buyerEmail": "buyer@example.com",
        //                "buyerPhone": "9874563210",
        //                "orderid": "E0101000000003820200206162831",
        //                "buyerFirstName": "SampleFirst",
        //                "buyerLastName": "SampleLast",
        //                "WIDout_trade_no": "test20200206162831",
        //                "WIDsubject": "test123",
        //                "WIDtotal_fee": "1",
        //                "WIDbody": "test",
        //                "WIDproduct_code": "NEW_OVERSEAS_SELLER",
        //                "tran_req_type": "cb1",
        //                "payment_method": "smartro"
        ////        }


    }
}