package com.paysez.export;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.paysez.library.Pojo.GenerateStaticQrData;
import com.paysez.library.utils.Encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UPI_Activity extends AppCompatActivity {
    EditText vpa_address, merchant_name, amount, bill;
    Button generate;
    ImageView image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_);


        vpa_address = findViewById(R.id.vpa_address);
        merchant_name = findViewById(R.id.merchant_name);
        amount = findViewById(R.id.amount);
        bill = findViewById(R.id.bill);
        generate = findViewById(R.id.generate);
        image_view = findViewById(R.id.image_view);
        //vpa_address.setText("8825788696@ybl");
        //merchant_name.setText("vinothkumar");

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                QRCodeWriter writer = new QRCodeWriter();
                Encryption encryption = new Encryption();
                try
                {
                    String signature = Base64.encodeToString(encryption.getSHA256("secret_key").getBytes(), 0);
                    GenerateStaticQrData generateStaticQrData = new GenerateStaticQrData();
                    generateStaticQrData.setVPA(vpa_address.getText().toString());
                    generateStaticQrData.setName(merchant_name.getText().toString());


                    generateStaticQrData.setMode("02");
                    generateStaticQrData.setSign("werwrwrwrrwrwe");
                    generateStaticQrData.setOriginId(UUID.randomUUID().toString());
                    generateStaticQrData.setUpiId("upi://pay?");
                    generateStaticQrData.setSecretKey("secret_key");
                    generateStaticQrData.setCu("INR");
                    generateStaticQrData.setSignature(signature);
                    generateStaticQrData.setAm(amount.getText().toString());
                    generateStaticQrData.setTr(bill.getText().toString());
                    String finaldata = generateStaticQrData.GenerateMapDynamic(generateStaticQrData);
                    Log.e("finaldata", finaldata);


                    try {
                        BitMatrix bitMatrix = writer.encode(finaldata, BarcodeFormat.QR_CODE, 1000, 1000);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();
                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        image_view.setImageBitmap(bmp);


                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
