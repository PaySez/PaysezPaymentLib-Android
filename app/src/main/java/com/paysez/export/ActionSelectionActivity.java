package com.paysez.export;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionSelectionActivity extends AppCompatActivity {
Button card,upi,net_banking,payment_links;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        card = findViewById(R.id.card);
        upi = findViewById(R.id.upi);
        net_banking = findViewById(R.id.net_banking);
        payment_links = findViewById(R.id.payment_links);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActionSelectionActivity.this, CardPaymentActivity.class);
                startActivity(intent);
            }
        });

        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActionSelectionActivity.this,UPI_Activity.class);
                startActivity(intent);
            }
        });
        net_banking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActionSelectionActivity.this,NetBanking.class);
                startActivity(intent);
            }
        });
        payment_links.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActionSelectionActivity.this, MerchantLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
