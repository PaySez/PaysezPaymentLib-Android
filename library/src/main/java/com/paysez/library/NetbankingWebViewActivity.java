package com.paysez.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class NetbankingWebViewActivity extends AppCompatActivity {
    WebView webview;
    ProgressDialog pd;
    private String Tag = "";
    public String redirectionurl;
    int REQUEST_CODE_SALE = 1;
    int REQUEST_CODE_QUERY = 2;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netbanking);
        webview = findViewById(R.id.webview);
        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setCacheMode(LOAD_NO_CACHE);
        webview.getSettings().setDomStorageEnabled(true);
        webview.clearHistory();
        webview.clearCache(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(false);



        if (getIntent().getStringExtra("action").equals("sale")) {

            Intent data = getIntent();
            String merchant_id = data.getStringExtra("merchant_id");
            String amount = data.getStringExtra("amount");
            String transaction_id = data.getStringExtra("transaction_id");
            redirectionurl = data.getStringExtra("redirectionurl");
            String time = data.getStringExtra("time");

            String postData =

                    "&merchantId=" + merchant_id +
                            "&amount=" + amount +
                            "&currency=" + "INR" +
                            "&env=" + "live" +
                            "&timestamp=" + time +
                            "&transactionId=" + transaction_id +
                            "&TransactionType=" + "AA" +
                            "&redirectionurl=" + redirectionurl;


            webview.postUrl(AppConfig.netbanking_sale, postData.getBytes());
            webview.requestFocus();

        }

        if (getIntent().getStringExtra("action").equals("query")) {
            Intent data = getIntent();
            String merchant_id = data.getStringExtra("merchant_id");
            String Transaction_id = data.getStringExtra("Transaction_id");
            String vendor_pay_option = data.getStringExtra("vendor_pay_option");
            String postData = "&merchant_id=" + merchant_id +
                            "&Transaction_id=" + Transaction_id +
                            "&vendor_pay_option=" + vendor_pay_option;

            webview.postUrl(AppConfig.netbanking_query, postData.getBytes());
            webview.requestFocus();

        }


    }


    private class MyWebViewClient extends WebViewClient {

        private int webViewPreviousState;

        private final int PAGE_STARTED = 0x1;


        private final int PAGE_REDIRECTED = 0x2;

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                // String request_header = request.getRequestHeaders().toString();
                //Log.v("Check_Check", "==========================================>" + request_header);
                Log.d("DATA-REQHEADER", "request.getRequestHeaders()::" + request.getRequestHeaders());
                Log.d("DATA-REQURL", "request.getRequestHeaders()::" + request.getUrl());


            }

            return null;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.v("overloading", url);
            Log.d("1111", "WebViewClient: shouldOverrideUrlLoading");
            return true;
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webViewPreviousState = PAGE_STARTED;
            Log.v("onPageStarted", url);

            Log.d(Tag, "WebViewClient: onPageStarted:  url: " + url);


        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onPageFinished(WebView view, String url)
        {
            Log.v("onPageFinished", url);
            if (pd != null) {

                pd.dismiss();
            }



            if (url.contains("https://pguat.credopay.info/credopaylogin/NBQuery_status.php")) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webview.evaluateJavascript(
                            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    Log.d("HTML", html);
                                    Intent intent = new Intent();
                                    intent.putExtra("result", html);
                                    setResult(200, intent);
                                    finish();

                                }
                            });
                }

            }


            if (url.contains("success=Failed"))
            {
                if (pd != null) {

                    pd.dismiss();
                }


                try {
                    url = url.replace(redirectionurl, "");


                    System.out.println(url);


                    String[] split = url.split("&");
                    String responsecode = split[0];
                    String merchant_id = split[1];
                    String transaction_id = split[2];
                    String amount = split[3];
                    String currency = split[4];
                    String TransactionType = split[5];
                    String success = split[6];
                    String errordesc = URLDecoder.decode(split[7], "UTF-8");

                    String refNbr = split[8];


                    Log.v(Tag, "failure");
                    Intent intent = getIntent();

                    intent.putExtra("responsecode", responsecode.split("=")[1]);
                    intent.putExtra("merchant_id", merchant_id.split("=")[1]);
                    intent.putExtra("transaction_id", transaction_id.split("=")[1]);
                    intent.putExtra("amount", amount.split("=")[1]);
                    intent.putExtra("currency", currency.split("=")[1]);
                    intent.putExtra("TransactionType", TransactionType.split("=")[1]);
                    intent.putExtra("success", success.split("=")[1]);
                    intent.putExtra("errordesc", errordesc.split("=")[1]);

                    intent.putExtra("refNbr", refNbr.split("=")[1]);
                    intent.putExtra("status", "failure");
                    setResult(100, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();


                }

            }


            if (url.contains("responsecode=200") && url.contains("success=Success")) {


                try {
                    url = url.replace(redirectionurl, "");


                    System.out.println(url);


                    String[] split = url.split("&");
                    String responsecode = split[0];
                    String merchant_id = split[1];
                    String transaction_id = split[2];
                    String amount = split[3];
                    String currency = split[4];
                    String TransactionType = split[5];
                    String success = split[6];
                    String errordesc = URLDecoder.decode(split[7], "UTF-8");
                    String refNbr = split[8];


                    Log.v(Tag, "success");
                    Intent intent = getIntent();

                    intent.putExtra("responsecode", responsecode.split("=")[1]);
                    intent.putExtra("merchant_id", merchant_id.split("=")[1]);
                    intent.putExtra("transaction_id", transaction_id.split("=")[1]);
                    intent.putExtra("amount", amount.split("=")[1]);
                    intent.putExtra("currency", currency.split("=")[1]);
                    intent.putExtra("TransactionType", TransactionType.split("=")[1]);
                    intent.putExtra("success", success.split("=")[1]);
                    intent.putExtra("errordesc", errordesc.split("=")[1]);
                    intent.putExtra("refNbr", refNbr.split("=")[1]);
                    intent.putExtra("status", "success");
                    setResult(100, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();

                }


            }


        }
    }


}