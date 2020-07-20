package com.paysez.export;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paysez.library.PaymentLinkHelper;

import java.util.UUID;

public class PaymentLinksActivity extends AppCompatActivity {
    EditText amount, purpose, mobile, whatsapp_number, email_id,customer_name;
    Button Generate, links;
    String mid, uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_links);
        customer_name = findViewById(R.id.customer_name);
        amount = findViewById(R.id.amount);
        purpose = findViewById(R.id.purpose);
        mobile = findViewById(R.id.mobile);
        whatsapp_number = findViewById(R.id.whatsapp_number);
        email_id = findViewById(R.id.email_id);
        Generate = findViewById(R.id.generate);
        links = findViewById(R.id.get_links);
        mid = getIntent().getStringExtra("mid");
        uname = getIntent().getStringExtra("uname");

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(PaymentLinksActivity.this, PaymentLinkHelper.class);
                intent.putExtra("amt", amount.getText().toString());
                intent.putExtra("nm", uname);
                intent.putExtra("mid", mid);
                intent.putExtra("uid", UUID.randomUUID().toString().substring(0, 7));
                intent.putExtra("prps", purpose.getText().toString());
                intent.putExtra("request_for", "create_link");
                startActivityForResult(intent, 2);

            }
        });


        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentLinksActivity.this, PaymentLinkHelper.class);
                intent.putExtra("mid", mid);
                intent.putExtra("request_for", "link_status");
                startActivityForResult(intent, 5);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {


            Intent intent = new Intent(PaymentLinksActivity.this, EmailSmsActivity.class);


            intent.putExtra("status", data.getStringExtra("status"));
            intent.putExtra("mid", data.getStringExtra("mid"));
            intent.putExtra("merchant_name", data.getStringExtra("merchant_name"));
            intent.putExtra("paylink", data.getStringExtra("paylink"));
            intent.putExtra("mobile", mobile.getText().toString());
            intent.putExtra("cust_name",customer_name.getText().toString());
            intent.putExtra("email", email_id.getText().toString());
            intent.putExtra("amt", amount.getText().toString());
            intent.putExtra("nm", uname);
            intent.putExtra("mid", mid);
            intent.putExtra("uid", data.getStringExtra("unique_id"));
            intent.putExtra("prps", purpose.getText().toString());
            startActivity(intent);

        }
        if (requestCode == 5 && resultCode == 5) {
            String response = data.getStringExtra("link_data");
            Intent intent = new Intent(this, RecyclerViewActivity.class);
            intent.putExtra("data", response);
            startActivity(intent);
        }

    }
}
