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

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class CommonWebViewActivity extends AppCompatActivity {
    WebView webview;
    ProgressDialog pd;
    private String Tag = "";

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
        webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        //  DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //  Date date = new Date();

        if (getIntent().getStringExtra("action").equals("sale")) {

            Intent data = getIntent();
            String merchant_id = data.getStringExtra("merchant_id");
            String amount = data.getStringExtra("amount");
            String transaction_id = data.getStringExtra("transaction_id");
            String redirectionurl = data.getStringExtra("redirectionurl");
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

            Log.v("asdasdasd", postData);
            webview.postUrl("https://pguat.credopay.info/credopay/CPWebPG.php", postData.getBytes());
            webview.requestFocus();

        }

        if (getIntent().getStringExtra("action").equals("query")) {
            Intent data = getIntent();
            String merchant_id = data.getStringExtra("merchant_id");
            String Transaction_id = data.getStringExtra("Transaction_id");
            String vendor_pay_option = data.getStringExtra("vendor_pay_option");
//Transaction_id = "E0110000000000920200810230915";
            String postData =

                    "&merchant_id=" + merchant_id +
                            "&Transaction_id=" + Transaction_id +
                            "&vendor_pay_option=" + vendor_pay_option;
            Log.v("sadasdasd",postData);
            webview.postUrl("https://pguat.credopay.info/credopaylogin/NBQuery_status.php", postData.getBytes());
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
            if (url.contains(" https://pg.credopay.net/api/fbtestbackground.php?")) {
                if (pd != null) {

                    pd.dismiss();
                }
                if (url.contains("success=true")) {
                    if (pd != null) {

                        pd.dismiss();
                    }
                    //Success
//                    Log.v(Tag, "success");
//                    Intent intent = getIntent();
//                    intent.putExtra("key", "success");
//                    setResult(RESULT_CODE_TRANSACTION, intent);
//                    finish();

                } else if (url.contains("success=false")) {
                    if (pd != null) {

                        pd.dismiss();
                    }

                }

            }

            //http://example.com/?responsecode=200&merchant_id=E01100000000009&transaction_id=E0110000000000920200811002718&amount=1.00&currency=INR&TransactionType=NetBanking&success=Success&errordesc=NA&refNbr=U1230001412545
            if (url.contains("responsecode=200") && url.contains("success=Success")) {

                Intent intent = new Intent();
                intent.putExtra("result", url);
                setResult(100, intent);
                finish();

            }

        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.v("onPageFinished", url);
            if (pd != null) {

                pd.dismiss();
            }
            Log.d(Tag, "WebViewClient: onPageFinished: url: " + url);
            if (webViewPreviousState == PAGE_STARTED) {


            }
            if (url.contains("NPCI/server/AcquirerHandler")) {
                if (pd != null) {

                    pd.dismiss();
                }
                Log.v(Tag, "inside the otp page");
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
            if (url.contains("success=false")) {
                if (pd != null) {

                    pd.dismiss();
                }
                //Failure
                Log.v(Tag, "failure");
//                Intent intent = getIntent();
//                intent.putExtra("key", url);
//                intent.putExtra("status", "failure");
//                setResult(RESULT_CODE_TRANSACTION, intent);
//                finish();
            }
            if (url.contains("success=true")) {
                if (pd != null) {

                    pd.dismiss();
                }
                //Failure
                Log.v(Tag, "success");
//                Intent intent = getIntent();
//                intent.putExtra("key", url);
//                intent.putExtra("status", "success");
//                setResult(RESULT_CODE_TRANSACTION, intent);
//                finish();
            }

        }
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        public void showHTML(String html) {
            Log.v("url_value", html);
        }

    }
}