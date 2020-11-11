package com.paysez.export;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.paysez.library.PaymentWebviewActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestActivity extends AppCompatActivity {
    Button button;
    public static final int REQUEST_CODE_TRANSACTION = 1;
    public static final int RESULT_CODE_TRANSACTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();

                //String merchant_id = "E01100000000123";
                String merchant_id = "E01100000000009";
                String amount = "1";
                String timestamp = dateFormat.format(date);
                String Transaction_id = merchant_id + timestamp;
                String redirectionurl = "https://example.com/?";
                String key = "pg89434tmd0835";
                String iv = "347637p3g64493";


                Intent intent = new Intent(RequestActivity.this, PaymentWebviewActivity.class);
                intent.putExtra("merchant_id", merchant_id);
                intent.putExtra("amount", amount);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("Transaction_id", Transaction_id);
                intent.putExtra("MsgId", Transaction_id);
                intent.putExtra("redirectionurl", redirectionurl);
                intent.putExtra("key", key);
                intent.putExtra("iv", iv);
                startActivityForResult(intent, REQUEST_CODE_TRANSACTION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_TRANSACTION && resultCode == RESULT_CODE_TRANSACTION) {
            String full_response = data.getStringExtra("full_response");
            String responsecode = data.getStringExtra("responsecode");
            String merchant_id = data.getStringExtra("merchant_id");
            String transaction_id = data.getStringExtra("transaction_id");
            String amount = data.getStringExtra("amount");
            String success = data.getStringExtra("success");
            String errordesc = data.getStringExtra("errordesc");
            String refNbr = data.getStringExtra("refNbr");
            String rrn = data.getStringExtra("rrn");
            String mode = data.getStringExtra("mode");
            String status = data.getStringExtra("status");


            Log.v("status_check", "=================================================");

            Log.v("status_check", responsecode);
            Log.v("status_check", merchant_id);
            Log.v("status_check", transaction_id);
            Log.v("status_check", amount);
            Log.v("status_check", success);
            Log.v("status_check", errordesc);
            Log.v("status_check", refNbr);
            Log.v("status_check", rrn);
            Log.v("status_check", mode);
            Log.v("status_check", status);


            Log.v("status_check", "=================================================");
        }
    }

}

