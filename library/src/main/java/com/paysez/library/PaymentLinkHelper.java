package com.paysez.library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.google.gson.Gson;
import com.paysez.library.Responses.Datum;
import com.paysez.library.Responses.ExpiredLinksResponse;
import com.paysez.library.Responses.LinkLists;
import com.paysez.library.Responses.LoginResponse;
import com.paysez.library.Responses.PushResponse;
import com.paysez.library.Responses.SMS_Response;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentLinkHelper extends AppCompatActivity {
    PushAPIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_link);
        String modeOfOperation = getIntent().getStringExtra("request_for");
        apiInterface = PushAPIClient.getClient().create(PushAPIInterface.class);
        // Toast.makeText(getApplicationContext(), modeOfOperation, Toast.LENGTH_LONG).show();
        progressDialog = new ProgressDialog(this);
        showProgressDialog();
        switch (modeOfOperation) {


            case "create_link":

                String amt = getIntent().getStringExtra("amt");
                String nm = getIntent().getStringExtra("nm");
                String mid = getIntent().getStringExtra("mid");
                String uid = getIntent().getStringExtra("uid");
                String prps = getIntent().getStringExtra("prps");
                String request_for = getIntent().getStringExtra("request_for");


                final RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("amt", amt)
                        .addFormDataPart("nm", nm)
                        .addFormDataPart("mid", mid)
                        .addFormDataPart("uid", uid)
                        .addFormDataPart("prps", prps)
                        .addFormDataPart("request_for", request_for)


                        .build();

                apiInterface.generateLink(requestBody).enqueue(new Callback<PushResponse>() {
                    @Override
                    public void onResponse(Call<PushResponse> call, Response<PushResponse> response) {
                        PushResponse pushResponse = response.body();


                        if (pushResponse.getStatus().equalsIgnoreCase("00")) {

                            hideProgressDialog();

                            Intent intent = new Intent();

                            intent.putExtra("status", pushResponse.getStatus());
                            intent.putExtra("unique_id", pushResponse.getUniqueId());
                            intent.putExtra("mid", pushResponse.getMid());
                            intent.putExtra("merchant_name", pushResponse.getMerchantName());
                            intent.putExtra("paylink", pushResponse.getPaylink());
                            setResult(2, intent);
                            finish();


                        }

                    }

                    @Override
                    public void onFailure(Call<PushResponse> call, Throwable t) {
                        hideProgressDialog();
                    }
                });

                break;

            case "login":


                request_for = getIntent().getStringExtra("request_for");
                String mid_val_intent = getIntent().getStringExtra("mid");
                String tid_val_intent = getIntent().getStringExtra("tid");


                final RequestBody login_requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mid", mid_val_intent)
                        .addFormDataPart("tid", tid_val_intent)
                        .addFormDataPart("request_for", request_for)


                        .build();
                apiInterface.Login(login_requestBody).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            String mid = loginResponse.getMerchant_id();
                            String unmae = loginResponse.getUser_name();

                            if (mid != null && unmae != null) {
                                Log.v("data", loginResponse.getMerchant_id());
                                Log.v("data", loginResponse.getUser_name());
                                Intent intent = new Intent();
                                intent.putExtra("mid", loginResponse.getMerchant_id());
                                intent.putExtra("username", loginResponse.getUser_name());
                                intent.putExtra("status", "00");
                                setResult(1, intent);
                                finish();

                            } else {

                                Log.v("data", "null");
                                Log.v("data", "null");
                                Intent intent = new Intent();
                                intent.putExtra("mid", loginResponse.getMerchant_id());
                                intent.putExtra("username", loginResponse.getUser_name());
                                intent.putExtra("status", "01");
                                setResult(1, intent);
                                finish();
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hideProgressDialog();
//                        Log.v("data", "null");
//                        Log.v("data", "null");
                        Intent intent = new Intent();
                        intent.putExtra("mid", "null");
                        intent.putExtra("error_msg", "something went wrong!");
                        intent.putExtra("status", "02");
                        setResult(1, intent);
                        finish();
                    }
                });
                break;


            case "SMS":


                amt = getIntent().getStringExtra("amt");
                nm = getIntent().getStringExtra("nm");
                mid = getIntent().getStringExtra("mid");
                uid = getIntent().getStringExtra("uid");
                prps = getIntent().getStringExtra("prps");
                request_for = getIntent().getStringExtra("request_for");
                String mobile = getIntent().getStringExtra("mobile");
                String cust_name = getIntent().getStringExtra("cust_name");
                Log.v("request_for", request_for);

                apiInterface = PushAPIClient.getClient().create(PushAPIInterface.class);
                final RequestBody smsBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("phone_no", mobile)
                        .addFormDataPart("amt", amt)
                        .addFormDataPart("nm", nm)
                        .addFormDataPart("mid", mid)
                        .addFormDataPart("uid", uid)
                        .addFormDataPart("prps", prps)
                        .addFormDataPart("cust_name", cust_name)
                        .addFormDataPart("request_for", "SMS")


                        .build();
                apiInterface.sendSMS(smsBody).enqueue(new Callback<SMS_Response>() {
                    @Override
                    public void onResponse(Call<SMS_Response> call, Response<SMS_Response> response) {
                        hideProgressDialog();
                        SMS_Response pushResponse = response.body();
                        Intent intent = new Intent();

                        intent.putExtra("sms_status", pushResponse.getSmsStatus());
                        intent.putExtra("unique_id", pushResponse.getUniqueId());
                        intent.putExtra("mid", pushResponse.getMid());
                        intent.putExtra("merchant_name", pushResponse.getMerchantName());
                        setResult(4, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<SMS_Response> call, Throwable t) {
                        hideProgressDialog();
                    }
                });


                break;

            case "MAIL":

                amt = getIntent().getStringExtra("amt");
                nm = getIntent().getStringExtra("nm");
                mid = getIntent().getStringExtra("mid");
                uid = getIntent().getStringExtra("uid");
                prps = getIntent().getStringExtra("prps");
                request_for = getIntent().getStringExtra("request_for");
                String cust_name_val = getIntent().getStringExtra("cust_name");
                String email = getIntent().getStringExtra("email");
                Log.v("request_for", request_for);


                apiInterface = PushAPIClient.getClient().create(PushAPIInterface.class);
                final RequestBody mailBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mail", email)
                        .addFormDataPart("amt", amt)
                        .addFormDataPart("nm", nm)
                        .addFormDataPart("mid", mid)
                        .addFormDataPart("uid", uid)
                        .addFormDataPart("cust_name", cust_name_val)
                        .addFormDataPart("prps", prps)
                        .addFormDataPart("request_for", "MAIL")


                        .build();


                apiInterface.sendMail(mailBody).enqueue(new Callback<MailResponse>() {
                    @Override
                    public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                        hideProgressDialog();
                        MailResponse mailResponse = response.body();

                        Intent intent = new Intent();

                        intent.putExtra("mail_status", mailResponse.getMailStatus());
                        intent.putExtra("unique_id", mailResponse.getUniqueId());
                        intent.putExtra("mid", mailResponse.getMid());
                        intent.putExtra("merchant_name", mailResponse.getMerchantName());
                        setResult(3, intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<MailResponse> call, Throwable t) {
                        hideProgressDialog();
                    }
                });
                break;


            case "link_status":
                final String mid_val = getIntent().getStringExtra("mid");
                final String request_for_val = getIntent().getStringExtra("request_for");



                new Thread(new Runnable() {
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    // .addFormDataPart("mid", mid_val)
                                    .addFormDataPart("mid", mid_val)
                                    .addFormDataPart("request_for", request_for_val)
                                    .build();
                            Request request = new Request.Builder()
                                    // .url("https://pguat.credopay.info/credopaylogin/pushpay_MobReq.php")
                                    .url("https://pg.credopay.in/credopaylogin/pushpay_MobReq.php")
                                    .method("POST", body)
                                    .build();
                            okhttp3.Response response = client.newCall(request).execute();
                            String response_data = response.body().string();
                            Log.v("CHECK_DATA", response_data);
                            hideProgressDialog();
                            Intent intent = new Intent();
                            intent.putExtra("link_data", response_data);
                            setResult(5, intent);
                            finish();


                        } catch (IOException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }
                    }
                }).start();


                break;

        }
    }

    private void showProgressDialog() {

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Processing Request...");
        progressDialog.show();

        // To Dismiss progress dialog
        //progressDialog.dismiss();
    }

    private void hideProgressDialog() {

        if (progressDialog != null) {

            progressDialog.dismiss();
        }

    }
}
