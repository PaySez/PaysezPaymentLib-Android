package com.paysez.library;
import com.paysez.library.Responses.ReversalResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface APIInterface {
    @POST("/credopay/api/CPDirectPG.php")
    Call<FirstResponse> FirstRequest(@Body RequestBody body);

//    @POST("/api/RPaymentAPI.php")
//    Call<FirstResponse> FirstRequest(@Body RequestBody body);
//https://pg.credopay.net/api/reversal_process_sdk.php



    @POST("/api/reversal_process_sdk.php")
    Call<ReversalResponse> DoReversal(@Body RequestBody body);
}