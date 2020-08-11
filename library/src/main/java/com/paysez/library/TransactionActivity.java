package com.paysez.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.paysez.library.utils.HMAC_SHA256;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class TransactionActivity extends Activity {
    public static final int REQUEST_CODE_TRANSACTION = 1;
    public static final int RESULT_CODE_TRANSACTION = 2;
    public String AccuRequestId;
    WebView webview;
    ProgressDialog pd;
    String Tag;
    String redirectionurl;
    String session, AccuGuid;

    public static String AESEncrypt(String value, String keyvalue, String ivvvalue) {
        try {
            String key = sha256(keyvalue).substring(0, 32);
            String ivv = sha256(ivvvalue).substring(0, 16);
            IvParameterSpec iv = new IvParameterSpec(ivv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            String enc = Base64.encodeToString(encrypted, 2);
            return enc;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String URLencode(String value) {
        String urlEncoded = Uri.encode(value);
        return urlEncoded;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Tag = this.getClass().getSimpleName();
        //Toast.makeText(getApplicationContext(),"asdsda",Toast.LENGTH_LONG).show();
        webview = findViewById(R.id.webview);
        pd = new ProgressDialog(TransactionActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
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
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(false);
        String merchantId = getIntent().getStringExtra("merchantID");
        String purchaseAmount = getIntent().getStringExtra("purchaseAmount");
        String currencyCodeChr = getIntent().getStringExtra("currencyCodeChr");
        String env = getIntent().getStringExtra("env");
        String timestamp = getIntent().getStringExtra("timeStamp");
        String Transaction_id = getIntent().getStringExtra("transactionId");
        String TransactionType = getIntent().getStringExtra("transactionType");
        String PaymentChannel = getIntent().getStringExtra("paymentChannel");
        redirectionurl = getIntent().getStringExtra("redirectionUrl");
        String nameoncard = getIntent().getStringExtra("nameOnCard");
        String pan = getIntent().getStringExtra("cardNo");
        String ExpiryMonth = getIntent().getStringExtra("expiryMonth");
        String ExpiryYear = getIntent().getStringExtra("expiryYear");
        String CardCvv = getIntent().getStringExtra("cardCvv");
        String returnUrl = getIntent().getStringExtra("returnUrl");
        String currencyCodeNum = getIntent().getStringExtra("currencyCodeNum");
        String currencyExponent = getIntent().getStringExtra("currencyExponent");
        String key = getIntent().getStringExtra("key");
        String ivv = getIntent().getStringExtra("iv");
        FirstLegRequest(merchantId, purchaseAmount, currencyCodeChr, env, timestamp, Transaction_id, TransactionType, PaymentChannel, redirectionurl, nameoncard, pan, ExpiryMonth, ExpiryYear, CardCvv, currencyCodeNum, currencyExponent, returnUrl, key, ivv);
    }

    /**
     * Opens the URL in a browser.
     */
    private void FirstLegRequest(final String merchant_id, final String amount, final String currencyCodeChr, final String env, final String timestamp, final String Transaction_id, String TransactionType, String PaymentChannel, String redirectionurl, String nameOnCard, final String cardnumber, final String expiryMM, final String expiryYYYY, String cardCvv, final String currencyCodeNum, final String currencyExponent, final String returnUrl, String key, String ivv) {
        String tax = "";
        String schema = "";
        String terminal_id = "E0002214";
        int rupayflag = 0;
        int mastroflag = 0;
        int visafalg = 0;
        try {
            if (cardnumber.startsWith("4")) {
                tax = "VISA";
                visafalg = 1;
                schema = "VISA3DSPIT";
                Log.v(Tag, "found Visa Card");
            } else if (cardnumber.startsWith("5")) {
                if (cardnumber.contains("5089")) {
                    tax = "RUPAY";
                    schema = "";
                    rupayflag = 1;
                    Log.v(Tag, "found RUPAY Card");
                } else {
                    tax = "MASTER";
                    schema = "MC";
                    mastroflag = 1;
                    Log.v(Tag, "found Master Card");
                }
            } else if (cardnumber.startsWith("6")) {
                tax = "RUPAY";
                schema = "";
                rupayflag = 1;
                Log.v(Tag, "found RUPAY Card");
            }
            if (rupayflag == 1) {
                rupayflag = 0;
//                String encdata = merchant_id + amount + currencyCodeChr + "live"
//                        + timestamp +
//
//                        Transaction_id + TransactionType + PaymentChannel + redirectionurl;
//                Log.v("encdata", encdata);
                RupayFlow(merchant_id, amount, currencyCodeChr, "live",
                        timestamp, Transaction_id, TransactionType, PaymentChannel,
                        redirectionurl, tax, nameOnCard, cardnumber, expiryMM, expiryYYYY, cardCvv);
            } else if (visafalg == 1 || mastroflag == 1) {
                visafalg = 0;
                mastroflag = 0;
                String encdata = merchant_id + amount + currencyCodeChr + env
                        + timestamp +
                        Transaction_id + TransactionType + PaymentChannel + redirectionurl;
                Log.v("data-encdata", encdata);
//                String generate = "merchant_id" + ":" + merchant_id + "\n" +
//                        "amount" + ":" + amount + "\n" +
//
//                        "currency" + ":" + currencyCodeChr + "\n" +
//                        "env" + ":" + env + "\n" +
//                        "timestamp" + ":" + timestamp + "\n" +
//                        "Transaction_id" + ":" + Transaction_id + "\n" +
//                        "TransactionType" + ":" + TransactionType + "\n" +
//                        "PaymentChannel" + ":" + PaymentChannel + "\n" +
//                        "redirectionurl" + ":" + redirectionurl + "\n" +
//                        "encData" + ":" + URLencode(AESEncrypt(encdata, key, ivv)) + "\n" +
//                        "tax" + ":" + tax + "\n" +
//                        "nameoncard" + ":" + nameOnCard + "\n" +
//                        "card_num" + ":" + cardnumber + "\n" +
//                        "expiry_mm" + ":" + expiryMM + "\n" +
//                        "expiry_yy" + ":" + expiryYYYY + "\n" +
//
//                        "card_cvv" + ":" + cardCvv + "\n" +
//                        "ponumber" + ":"+ Transaction_id + "\n" +
//
//
//                        "buyerEmail" + ":" + "buyer@example.com" + "\n" +
//                        "buyerPhone" + ":" + "9874563210" + "\n" +
//
//                        "orderid" + ":" + Transaction_id + "\n" +
//                        "buyerFirstName" + ":" + "SampleFirst" + "\n" +
//                        "buyerLastName" + ":" + "SampleLast";
//
//                Log.v("gendata", "==============generateddata============");
//                Log.v("gendata", "=======                       =========");
//                Log.v("gendata", "=======                       =========");
//                Log.v("gendata", "=======                       =========");
//                Log.v("gendata", generate);
//                Log.v("gendata", "=======                       =========");
//                Log.v("gendata", "=======                       =========");
//                Log.v("gendata", "=======                       =========");
//                Log.v("gendata", "==============generateddata============");
                String modifiedExpiration = expiryYYYY.substring(2, 4) + expiryMM;
                Log.v("data-modifiedExpiration", modifiedExpiration);
                try {
                    // String encdata = merchantId + purchaseAmount + currencyCodeChr + env + time + merchantId + time + TransactionType + PaymentChannel + redirectionurl;
                    APIInterface apiInterface = (APIInterface) APIClient.getClient().create(APIInterface.class);
                    Data data = new Data();
                    data.setMerchantId(merchant_id);
                    data.setAmount(amount);
                    data.setCurrency(currencyCodeChr);
                    data.setEnv("livem");
                    data.setTimestamp(timestamp);
                    data.setTransactionId(Transaction_id);
                    data.setTransactionType(TransactionType);
                    data.setPaymentChannel("Pg");
                    data.setRedirectionurl(redirectionurl);
                    data.setEncData(URLencode(AESEncrypt(encdata, key, ivv)));
                    data.setTax("VISA");
                    data.setNameoncard(nameOnCard);
                    data.setCardNum(cardnumber);
                    data.setExpiryMm(expiryMM);
                    data.setExpiryYy(expiryYYYY);
                    data.setCardCvv(cardCvv);
                    data.setPonumber(Transaction_id);
                    data.setBuyerEmail("buyer@example.com");
                    data.setBuyerPhone("9999999999");
                    data.setOrderid(Transaction_id);
                    data.setBuyerFirstName("SampleFirst");
                    data.setBuyerLastName("SampleLast");
                    MultipartBody multipartBody = (new MultipartBody.Builder()).setType(MultipartBody.FORM).addFormDataPart("merchant_id", data.getMerchantId()).addFormDataPart("amount", data.getAmount()).addFormDataPart("currency", data.getCurrency()).addFormDataPart("env", data.getEnv()).addFormDataPart("timestamp", data.getTimestamp()).addFormDataPart("Transaction_id", data.getTransactionId()).addFormDataPart("TransactionType", data.getTransactionType()).addFormDataPart("PaymentChannel", data.getPaymentChannel()).addFormDataPart("redirectionurl", data.getRedirectionurl()).addFormDataPart("encData", data.getEncData()).addFormDataPart("tax", data.getTax()).addFormDataPart("nameoncard", data.getNameoncard()).addFormDataPart("card_num", data.getCardNum()).addFormDataPart("expiry_mm", data.getExpiryMm()).addFormDataPart("expiry_yy", data.getExpiryYy()).addFormDataPart("card_cvv", data.getCardCvv()).addFormDataPart("ponumber", data.getPonumber()).addFormDataPart("buyerEmail", data.getBuyerEmail()).addFormDataPart("buyerPhone", data.getBuyerPhone()).addFormDataPart("orderid", data.getOrderid()).addFormDataPart("buyerFirstName", data.getBuyerFirstName()).addFormDataPart("buyerLastName", data.getBuyerLastName()).build();
                    final String finalSchema = schema;
                    apiInterface.FirstRequest(multipartBody).enqueue(new Callback<FirstResponse>() {
                        public void onResponse(Call<FirstResponse> call, Response<FirstResponse> response) {
                            if (response.isSuccessful()) {
                                FirstResponse firstResponse = (FirstResponse) response.body();
                                if (firstResponse.getStatus().equalsIgnoreCase("success")) {
                                    Log.v("response", firstResponse.getTranId() + "");
                                    Log.v("response", firstResponse.getErrorcode() + "");
                                    Log.v("response", firstResponse.getExponent() + "");
                                    Log.v("response", firstResponse.getGuid() + "");
                                    Log.v("response", firstResponse.getMermapid() + "");
                                    Log.v("response", firstResponse.getModulus() + "");
                                    Log.v("response", firstResponse.getStatus() + "");
                                    String modifiedExpiration = expiryYYYY.substring(2, 4) + expiryMM;
                                    Log.v("code-modifiedExpiration", modifiedExpiration);
                                    // TransactionActivity.this.cardRequest(purchaseAmount,firstResponse.getTranId() + "", pan, modifiedExpiration , merchantId, returnUrl,currencyCodeNum,currencyCodeChr,currencyExponent);
                                    cardSubmit(finalSchema, merchant_id, firstResponse.getTranId() + "", cardnumber, modifiedExpiration, timestamp, amount, currencyCodeNum, currencyCodeChr, currencyExponent, env, returnUrl);
                                }
                            }
                        }

                        public void onFailure(Call<FirstResponse> call, Throwable t) {
                            Log.v("error", "error");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cardSubmit(String schema, String merchant_id, String Transaction_id, String cardnumber, String modifiedExpiration, String timestamp, String amount, String currencyCodeNum, String currencyCodeChr, String currencyExponent, String env, String returnUrl) {
//String merchantId,String transactionId,String pan,String expiration,String purchaseDate,String purchaseAmount,String currencyCodeNum,String currencyCodeChr,String currencyExponent,String mac,String env,String returnUrl
        Log.v("flow", "into cardSubmit");
        Log.v(Tag, "CardSubmit");
        Log.v(Tag, schema);
        Log.v(Tag, merchant_id);
        Log.v(Tag, Transaction_id);
        Log.v(Tag, cardnumber);
        Log.v(Tag, modifiedExpiration);
        Log.v(Tag, timestamp);
        Log.v(Tag, amount);
        Log.v(Tag, currencyCodeNum);
        Log.v(Tag, currencyCodeChr);
        Log.v(Tag, currencyExponent);
        Log.v(Tag, env);
        Log.v(Tag, returnUrl);
        try {
            int length = 12 - amount.length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                stringBuilder.append("0");
            }


            String formated_amount = stringBuilder.toString() + amount;
            Log.v(Tag, "data-amount" + formated_amount);
            String purchaseDate = timestamp.substring(0, 8) + " " + timestamp.substring(8, 10) + ":" +
                    timestamp.substring(10, 12) + ":" + timestamp.substring(12, 14);
            Log.v("data-purchase", purchaseDate);
            if (pd != null) {
                pd.dismiss();
            }

            //$got=$schema. ';' .$merchantId. ';' . $transactionId .';'. $pan .';'. $expiration. ';' .$purchaseDate. ';' .$purchaseAmount. ';' .$currencyCodeNum.';'.$currencyCodeChr. ';' .$currencyExponent. ';'.$returnUrl.';4884jdlojdj389ue';


            //https://pguat.credopay.info/credopay_UAT/api/CPDirectPG.php
            String postData =
                    "schema=" + schema +
                            "&merchantId=" + merchant_id +
                            "&transactionId=" + Transaction_id +
                            "&pan=" + cardnumber +
                            "&expiration=" + modifiedExpiration +
                            "&purchaseDate=" + purchaseDate +
                            "&purchaseAmount=" + formated_amount +
                            "&currencyCodeNum=" + currencyCodeNum +
                            "&currencyCodeChr=" + currencyCodeChr +
                            "&currencyExponent=" + currencyExponent +
                            "&mac=" + cardnumber +
                            "&env=" + env +
                            "&returnUrl=" + returnUrl +
                            "&serviceUrl=mpi.jsp";

            webview.postUrl(AppConfig.cardSubmit, postData.getBytes()); //UAT
            webview.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RupayFlow(String merchant_id, String amount, String currency, String env, String timestamp, String Transaction_id, String TransactionType,
                           String PaymentChannel, String redirectionurl, String tax, String nameoncard, String card_num, String expiry_mm, String expiry_yy, String card_cvv) {
        int amount_val = Integer.parseInt(amount) / 100;
        String postData = "merchant_id=" + merchant_id +
                "&amount=" + amount_val +
                "&currency=" + currency +
                "&env=" + env +
                "&timestamp=" + timestamp +
                "&Transaction_id=" + Transaction_id +
                "&TransactionType=" + TransactionType +
                "&PaymentChannel=" + PaymentChannel +
                "&redirectionurl=" + redirectionurl +
                "&tax=" + tax +
                "&nameoncard=" + nameoncard +
                "&card_num=" + card_num +
                "&expiry_mm=" + expiry_mm +
                "&expiry_yy=" + expiry_yy +
                "&card_cvv=" + card_cvv +
                "&buyerEmail=" + "buyer@example.com" +
                "&buyerPhone=" + "9874563210" +
                "&orderid=" + Transaction_id +
                "&buyerFirstName=" + "SampleFirst" +
                "&buyerLastName=" + "SampleLast" +
                "&payment_method=" + "smartro";
        Log.v("postdata", postData);

        webview.postUrl(AppConfig.RupayLeg, postData.getBytes());
        webview.requestFocus();
    }

    private void RupayFlow2(String merchant_id, String amount, String currency, String env, String timestamp, String Transaction_id, String TransactionType,
                            String PaymentChannel, String redirectionurl, String tax, String nameoncard, String card_num, String expiry_mm, String expiry_yy, String card_cvv) {
        int amount_val = Integer.parseInt(amount) / 100;

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("merchant_id", merchant_id)
                .addFormDataPart("amount", amount_val + "")
                .addFormDataPart("currency", currency)
                .addFormDataPart("env", env)
                .addFormDataPart("timestamp", timestamp)
                .addFormDataPart("Transaction_id", Transaction_id)
                .addFormDataPart("TransactionType", TransactionType)
                .addFormDataPart("PaymentChannel", PaymentChannel)
                .addFormDataPart("redirectionurl", redirectionurl)
                .addFormDataPart("tax", tax)
                .addFormDataPart("nameoncard", nameoncard)
                .addFormDataPart("card_num", card_num)
                .addFormDataPart("expiry_mm", expiry_mm)
                .addFormDataPart("expiry_yy", expiry_yy)
                .addFormDataPart("card_cvv", card_cvv)
                .addFormDataPart("buyerEmail", "buyer@example.com")
                .addFormDataPart("buyerPhone", "9940271313")
                .addFormDataPart("orderid", Transaction_id)
                .addFormDataPart("buyerFirstName", "SampleFirst")
                .addFormDataPart("buyerLastName", "buyerLastName")
                .addFormDataPart("payment_method", "smartro")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                //.url("https://pg.credopay.net/api/PaysezDirectPG.php")
                .url("https://pg.credopay.net/api/PaysezDirectPG.php")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("response", e.toString());
                if (pd != null) {
                    pd.dismiss();
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    TransactionActivity.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            Log.d("response", myResponse);
                            try {
                                JSONObject jsonObject = new JSONObject(myResponse);
                                String status = jsonObject.getString("status");
                                String errorcode = jsonObject.getString("errorcode");
                                String tran_id = jsonObject.getString("tran_id");
                                String guid = jsonObject.getString("guid");
                                String modulus = jsonObject.getString("modulus");
                                String exponent = jsonObject.getString("exponent");
                                String Implements_Redirect = jsonObject.getString("Implements_Redirect");
                                String RedirectURL = jsonObject.getString("RedirectURL");
                                AccuGuid = jsonObject.getString("AccuGUID");
                                String AccuCardholderId = jsonObject.getString("AccuCardholderId");
                                String AccuHkey = jsonObject.getString("AccuHkey");
                                String AuthenticationNotRequired = jsonObject.getString("AuthenticationNotRequired");
                                String EndURL = jsonObject.getString("EndURL");
                                session = jsonObject.getString("session");
                                Log.v("DATA-AccuCardholderId", AccuCardholderId);
                                Log.v("DATA-AccuHkey", AccuHkey);
                                Log.v("DATA-RedirectURL", RedirectURL);
                                Log.v("DATA-EndURL", EndURL);
                                Log.v("DATA-tran", tran_id);
                                Log.v("DATA-AccuCardholderId", AccuCardholderId);
                                Log.v("DATA-AccuGuid", AccuGuid);
                                Log.v("DATA-session", session);
                                String message = tran_id + "&" + AccuCardholderId + "&" + AccuGuid + "&" + session;
                                String messageDigest = HMAC_SHA256.getInstance().generateHashWithHmac256(message, AccuHkey);
                                String encodedHmac = org.bouncycastle.util.encoders.Base64.toBase64String(messageDigest.getBytes());
                                AccuRequestId = encodedHmac;
                                Log.v("DATA-message", message);
                                Log.v("Data-messageDigest", messageDigest);
                                Log.v("Data-encodedHmac", encodedHmac);
                                String postData = "AccuCardholderId=" + AccuCardholderId + "&AccuGuid=" + AccuGuid + "&AccuReturnURL=" + RedirectURL + "&session=" + session + "&AccuRequestId=" + encodedHmac;
                                Log.v("Data-Check-postData", postData);
                                webview.postUrl(RedirectURL, postData.getBytes());
                                webview.requestFocus();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (pd != null) {
                                pd.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    public String getSdkVersion() {
        return "SDK version 5.10";
    }

    private class MyWebViewClient extends WebViewClient {
        private final int PAGE_STARTED = 0x1;
        private final int PAGE_REDIRECTED = 0x2;
        private int webViewPreviousState;

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // String request_header = request.getRequestHeaders().toString();
                //Log.v("Check_Check", "==========================================>" + request_header);
                Log.d("DATA-REQHEADER", "request.getRequestHeaders()::" + request.getRequestHeaders());
                Log.d("DATA-REQURL", "request.getRequestHeaders()::" + request.getUrl());
                Log.d("DATA-REQURL", "request.getRequestHeaders()::" + request.getUrl());
                // Log.d("DATA-REQURL", "request.getRequestHeaders()::" + request.get());
//                String headers = String.valueOf(request.getUrl());
//                Log.v("============>", headers);
//                if (headers.contains("msg=ACCU000"))
//                {
//
//
//                    //  Toast.makeText(getApplicationContext(),AccuResponseCode+session+AccuGuid,Toast.LENGTH_LONG).show();
//
//                    final String postData =
//                            "AccuResponseCode=" + "ACCU000" +
//                                    "&session=" + "sessxx=" + session + "==" +
//                                    "&AccuRequestId=" + AccuRequestId +
//                                    "&AccuGuid=" + AccuGuid;
//
//                    Log.v("DATA-", postData);
//
//                    webview.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            webview.postUrl("https://pg.credopay.net/api/smartropin_mobile.php", postData.getBytes());
//
//                            webview.requestFocus();
//                        }
//                    });
//
//                    // Log.v("check_point",url);
//
//
//                }
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
                    Log.v(Tag, "success");
                    Intent intent = getIntent();
                    intent.putExtra("key", "success");
                    setResult(RESULT_CODE_TRANSACTION, intent);
                    finish();
                } else if (url.contains("success=false")) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                }
            }
        }

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
            if (url.contains("success=false")) {
                if (pd != null) {
                    pd.dismiss();
                }


// http://example.com/?responsecode=00&merchant_id=E01100000000009&transaction_id=E0110000000000920200811171245&amount=1.00&currency=INR&request_type=AA&success=true&errordesc=Success&rrn=&apprcode=
                //String value = "http://example.com/responsecode=05&merchant_id=E01100000000009&transaction_id=E0110000000000920200811173052&amount=1.00&currency=INR&TransactionType=AA&success=false&errordesc=Do%20not%20honor%20%20&rrn=022417012866&refNbr=012866";
                url = url.replace(redirectionurl, "");


                System.out.println(url);

                String[] split = url.split("&");


                try {
                    String responsecode = split[0];
                    String merchant_id = split[1];
                    String transaction_id = split[2];
                    String amount = split[3];
                    String currency = split[4];
                    String TransactionType = split[5];
                    String success = split[6];
                    String errordesc = URLDecoder.decode(split[7], "UTF-8");
                    String rrn = split[8];
                    String refNbr = split[9];


                    Log.v(Tag, "failure");
                    Intent intent = getIntent();
                    intent.putExtra("key", url);
                    intent.putExtra("responsecode", responsecode.split("=")[1]);
                    intent.putExtra("merchant_id", merchant_id.split("=")[1]);
                    intent.putExtra("transaction_id", transaction_id.split("=")[1]);
                    intent.putExtra("amount", amount.split("=")[1]);
                    intent.putExtra("currency", currency.split("=")[1]);
                    intent.putExtra("TransactionType", TransactionType.split("=")[1]);
                    intent.putExtra("success", success.split("=")[1]);
                    intent.putExtra("errordesc", errordesc.split("=")[1]);
                    intent.putExtra("rrn", rrn.split("=")[1]);
                    intent.putExtra("refNbr", refNbr.split("=")[1]);

                    intent.putExtra("status", "failure");
                    setResult(RESULT_CODE_TRANSACTION, intent);
                    finish();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
            if (url.contains("success=true")) {
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
                    String rrn = split[8];
                    String refNbr = split[9];

                    Log.v(Tag, "success");
                    Intent intent = getIntent();
                    intent.putExtra("key", url);
                    intent.putExtra("responsecode", responsecode.split("=")[1]);
                    intent.putExtra("merchant_id", merchant_id.split("=")[1]);
                    intent.putExtra("transaction_id", transaction_id.split("=")[1]);
                    intent.putExtra("amount", amount.split("=")[1]);
                    intent.putExtra("currency", currency.split("=")[1]);
                    intent.putExtra("TransactionType", TransactionType.split("=")[1]);
                    intent.putExtra("success", success.split("=")[1]);
                    intent.putExtra("errordesc", errordesc.split("=")[1]);
                    intent.putExtra("rrn", rrn.split("=")[1]);
                    intent.putExtra("refNbr", refNbr.split("=")[1]);
                    intent.putExtra("status", "success");
                    setResult(RESULT_CODE_TRANSACTION, intent);
                    finish();
                } catch (Exception e) {

                    Log.e("err", e.toString());

                }
            }
        }
    }
}