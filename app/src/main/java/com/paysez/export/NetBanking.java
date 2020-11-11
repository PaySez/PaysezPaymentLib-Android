package com.paysez.export;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paysez.library.NetbankingWebViewActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NetBanking extends AppCompatActivity {

    int REQUEST_CODE_SALE = 1;
    int REQUEST_CODE_QUERY = 2;
    EditText merchant_id, amount;
    Button trans, query, generate_tran;
    DateFormat dateFormat;
    String time;
    Date date;
    EditText trans_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_banking);
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        query = findViewById(R.id.query);
        trans = findViewById(R.id.trans);
        merchant_id = findViewById(R.id.merchant_id);
        generate_tran = findViewById(R.id.generate_tran);
        trans_id = findViewById(R.id.trans_id);
        amount = findViewById(R.id.amount);

        generate_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = new Date();
                time = dateFormat.format(date);
                trans_id.setText("");
                trans_id.setText(merchant_id.getText().toString() + time);

            }
        });

        trans.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                Intent intent = new Intent(NetBanking.this, NetbankingWebViewActivity.class);
                intent.putExtra("action", "sale");
                intent.putExtra("merchant_id", merchant_id.getText().toString());
                intent.putExtra("amount", amount.getText().toString());
                intent.putExtra("transaction_id", trans_id.getText().toString());
                intent.putExtra("redirectionurl", "http://example.com/?");
                intent.putExtra("time", time);
                startActivityForResult(intent, REQUEST_CODE_SALE);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(NetBanking.this, NetbankingWebViewActivity.class);
                intent.putExtra("action", "query");
                intent.putExtra("merchant_id", merchant_id.getText().toString());
                intent.putExtra("Transaction_id", trans_id.getText().toString());
                intent.putExtra("vendor_pay_option", "NB");


                startActivityForResult(intent, REQUEST_CODE_QUERY);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("asdasdsad", requestCode + " " + resultCode);

        if (requestCode == REQUEST_CODE_SALE && resultCode == 100)
        {




            String full_response = data.getStringExtra("full_response");
            String responsecode = data.getStringExtra("responsecode");
            String merchant_id = data.getStringExtra("merchant_id");
            String transaction_id = data.getStringExtra("transaction_id");
            String amount = data.getStringExtra("amount");

            String success = data.getStringExtra("success");
            String errordesc = data.getStringExtra("errordesc");
            String refNbr = data.getStringExtra("refNbr");
            String status = data.getStringExtra("status");


            Log.v("response", full_response);
            Log.v("response", responsecode);
            Log.v("response", merchant_id);
            Log.v("response", transaction_id);
            Log.v("response", amount);
            Log.v("response", success);
            Log.v("response", errordesc);
            Log.v("response", refNbr);
            Log.v("response", status);



            if(success.equals("Success"))
            {

                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Transaction Success")
                        .setContentText(full_response)

                        .show();

            }
            else if(success.equals("Failed"))
            {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Transaction Failed")
                        .setContentText(full_response)
                        .show();


            }


        } else if (requestCode == REQUEST_CODE_QUERY && resultCode == 200) {
            String result = data.getStringExtra("result");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_SALE && resultCode == 300)
        {
            String result = data.getStringExtra("result");
            String errordesc = data.getStringExtra("errordesc");
            Toast.makeText(getApplicationContext(), errordesc, Toast.LENGTH_SHORT).show();
        }
    }
}
