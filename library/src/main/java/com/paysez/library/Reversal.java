package com.paysez.library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paysez.library.Responses.ReversalResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reversal extends AppCompatActivity {
    ProgressDialog pd;
    public static final int RESULT_CODE_REVERSAL = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversal);
        Intent i = getIntent();
        ReversalData reversalData = (ReversalData) i.getSerializableExtra("object");
        //  Toast.makeText(getApplicationContext(), dene.card_num, Toast.LENGTH_LONG).show();
        pd = new ProgressDialog(Reversal.this);
        pd.setMessage("loading");
        pd.show();
        doReversal(reversalData);
        Log.v("logg", "inside oncreate");


    }

    private void doReversal(ReversalData reversalData) {


        try {

            Log.v("logg", "inside doReversal");
            // String encdata = merchantId + purchaseAmount + currencyCodeChr + env + time + merchantId + time + TransactionType + PaymentChannel + redirectionurl;

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

            final RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("card_num", reversalData.getCard_num())
                    .addFormDataPart("card_cvv", reversalData.getCard_cvv())
                    .addFormDataPart("expiry_mm", reversalData.getExpiry_mm())
                    .addFormDataPart("expiry_yy", reversalData.getExpiry_yy())
                    .addFormDataPart("env", reversalData.getEnv())
                    .addFormDataPart("Transaction_id", reversalData.getTransaction_id())
                    .addFormDataPart("merchant_id", reversalData.getMerchant_id())
                    .addFormDataPart("TransactionType", reversalData.getTransactionType())
                    .addFormDataPart("Rev_reason", reversalData.getRev_reason())

                    .build();

            apiInterface.DoReversal(requestBody).enqueue(new Callback<ReversalResponse>() {
                @Override
                public void onResponse(Call<ReversalResponse> call, Response<ReversalResponse> response) {
                    if (response.isSuccessful()) {
                        Log.v("logg", "onResponse");
                        ReversalResponse reversalResponse = response.body();

                        //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                        Log.v("check_response", reversalResponse.getAmount() + "");
                        Log.v("response", reversalResponse.getCurrency() + "");
                        Log.v("response", reversalResponse.getErrordesc() + "");
                        Log.v("response", reversalResponse.getMerchantId() + "");
                        Log.v("response", reversalResponse.getResponsecode() + "");
                        Log.v("response", reversalResponse.getSuccess() + "");
                        Log.v("response", reversalResponse.getTransactionId() + "");
                        Log.v("response", reversalResponse.getTransactionType() + "");


                        Intent intent = getIntent();
                        intent.putExtra("amount", reversalResponse.getAmount() + "");
                        intent.putExtra("currecny", reversalResponse.getCurrency() + "");
                        intent.putExtra("error_desc", reversalResponse.getErrordesc() + "");
                        intent.putExtra("merchant_id", reversalResponse.getMerchantId() + "");
                        intent.putExtra("response_code", reversalResponse.getResponsecode() + "");
                        intent.putExtra("status", reversalResponse.getSuccess() + "");
                        intent.putExtra("transaction_id", reversalResponse.getTransactionId() + "");
                        intent.putExtra("transaction_type", reversalResponse.getTransactionType() + "");

                        if (pd != null) {
                            pd.dismiss();

                        }
                        setResult(RESULT_CODE_REVERSAL, intent);
                        finish();


                    }
                }

                @Override
                public void onFailure(Call<ReversalResponse> call, Throwable t) {


                    Log.v("error", "uncaught exception occured!");
                    finish();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("logg", "erroreee");
        }


    }


}