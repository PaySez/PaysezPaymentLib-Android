package com.paysez.export;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paysez.library.AppConfig;
import com.paysez.library.TransactionActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CardPaymentActivity extends AppCompatActivity {
    EditText merchant_id_view, amount_view, name_view, cardno_view, expiry_month_view, expiry_year_view, cvv;
    Button trans;

    public static final int REQUEST_CODE_TRANSACTION = 1;
    public static final int RESULT_CODE_TRANSACTION = 2;


    public static final int REQUEST_CODE_REVERSAL = 3;
    public static final int RESULT_CODE_REVERSAL = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        merchant_id_view = findViewById(R.id.merchant_id);
        amount_view = findViewById(R.id.amount);
        name_view = findViewById(R.id.purpose);
        cardno_view = findViewById(R.id.cardno);
        expiry_month_view = findViewById(R.id.expiry_month);
        cvv = findViewById(R.id.cvv);
        expiry_year_view = findViewById(R.id.expiry_year);
        trans = findViewById(R.id.trans);
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                String time = dateFormat.format(date);

                String merchant_id = merchant_id_view.getText().toString();
                String amount = amount_view.getText().toString();
                String timestamp = time;
                String Transaction_id = merchant_id + timestamp;
                String redirectionUrl = "https://www.example.com/?";
                String key = "ec89434eca0835";
                String ivv = "347637a3e64493";
                merchant_id = "E01100000000009";


                String nameoncard = name_view.getText().toString();
                String card_num = cardno_view.getText().toString();
                String expiry_mm = expiry_month_view.getText().toString();
                String expiry_yy = expiry_year_view.getText().toString();
                String card_cvv = cvv.getText().toString();




                Intent intent = new Intent(CardPaymentActivity.this, TransactionActivity.class);
                intent.putExtra("merchantID", merchant_id);
                intent.putExtra("purchaseAmount", amount);
                intent.putExtra("timeStamp", timestamp);
                intent.putExtra("transactionId", Transaction_id);
                intent.putExtra("redirectionUrl", redirectionUrl);
                intent.putExtra("nameOnCard", nameoncard);
                intent.putExtra("cardNo", card_num);
                intent.putExtra("expiryMonth", expiry_mm);
                intent.putExtra("expiryYear", expiry_yy);
                intent.putExtra("cardCvv", card_cvv);
                intent.putExtra("key", key);
                intent.putExtra("iv", ivv);
                startActivityForResult(intent, REQUEST_CODE_TRANSACTION);

                TransactionActivity transactionActivity = new TransactionActivity();
                Log.v("sdkversion", transactionActivity.getSdkVersion());


            }
        });


//        ReversalData reversalData = new ReversalData();
//        reversalData.setCard_num(card_num);
//        reversalData.setCard_cvv(card_cvv);
//        reversalData.setExpiry_mm(expiry_mm);
//        reversalData.setExpiry_yy(expiry_yyyy);
//        reversalData.setEnv("livem");
//        reversalData.setTransaction_id("E0110000000000920191010154525");
//        reversalData.setMerchant_id("E01100000000009");
//        reversalData.setTransactionType("RV");
//        reversalData.setRev_reason("68");
//
//        reversalData.setAmount("");
//        reversalData.setCurrency("");
//        reversalData.setTimestamp("");
//        reversalData.setPaymentChannel("");
//        reversalData.setNameoncard("");
//        reversalData.setRedirectionurl("");
//        reversalData.setEncData("");
//        Intent intent = new Intent(this, Reversal.class);
//        intent.putExtra("object", reversalData);
//        startActivityForResult(intent, REQUEST_CODE_REVERSAL);

        //  startService(new Intent(TransactionActivity.this, NetworkService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try {

            super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == REQUEST_CODE_TRANSACTION && resultCode == RESULT_CODE_TRANSACTION)
            {
                String full_response = data.getStringExtra("full_response");
                String responsecode = data.getStringExtra("responsecode");
                String merchant_id = data.getStringExtra("merchant_id");
                String transaction_id = data.getStringExtra("transaction_id");
                String amount = data.getStringExtra("amount");
                String success = data.getStringExtra("success");
                String errordesc = data.getStringExtra("errordesc");
                String rrn = data.getStringExtra("rrn");
                String status = data.getStringExtra("status");


if(status.equals("failure"))
{

    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Transaction failed")
            .setContentText(full_response)

            .show();

}
    else if(status.equalsIgnoreCase("success"))
{
    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Transaction Success")
            .setContentText(full_response)

            .show();


}
    else if(status.equalsIgnoreCase("failed"))
{

    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Transaction failed")
            .setContentText(full_response)

            .show();

}

                Log.v("DATA_response", "=================RESPONSE===========================");
                Log.v("DATA_response", full_response);
                Log.v("DATA_response", responsecode);
                Log.v("DATA_response", merchant_id);
                Log.v("DATA_response", transaction_id);
                Log.v("DATA_response", amount);
                Log.v("DATA_response", success);
                Log.v("DATA_response", errordesc);
                Log.v("DATA_response", rrn);
                Log.v("DATA_response", status);
                Log.v("DATA_response", "=================RESPONSE===========================");

                Toast.makeText(this, full_response,
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(this, status,
                        Toast.LENGTH_SHORT).show();
            }


            if (requestCode == REQUEST_CODE_REVERSAL && resultCode == RESULT_CODE_REVERSAL)
            {
                String amount = data.getStringExtra("amount");
                String currecny = data.getStringExtra("currecny");
                String error_desc = data.getStringExtra("error_desc");
                String merchant_id = data.getStringExtra("merchant_id");
                String response_code = data.getStringExtra("response_code");
                String status = data.getStringExtra("status");
                String transaction_id = data.getStringExtra("transaction_id");
                String transaction_type = data.getStringExtra("transaction_type");


                Log.v("amount", amount);
                Log.v("currecny", currecny);
                Log.v("error_desc", error_desc);
                Log.v("merchant_id", merchant_id);
                Log.v("response_code", response_code);
                Log.v("status", status);
                Log.v("transaction_id", transaction_id);
                Log.v("transaction_type", transaction_type);


            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
