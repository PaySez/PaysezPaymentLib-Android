package com.paysez.export;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paysez.library.CommonWebViewActivity;
public class NetBanking extends AppCompatActivity {
    EditText merchant_id, amount;
    Button trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_banking);

        trans = findViewById(R.id.trans);
        merchant_id = findViewById(R.id.merchant_id);
        amount = findViewById(R.id.amount);


        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NetBanking.this, CommonWebViewActivity.class);

                intent.putExtra("id", merchant_id.getText().toString());
                intent.putExtra("amount", amount.getText().toString());
                startActivity(intent);
            }
        });
    }
}
