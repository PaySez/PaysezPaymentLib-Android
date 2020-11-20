package com.paysez.library;


import com.paysez.library.Responses.Datum;
import com.paysez.library.Responses.ExpiredLinksResponse;
import com.paysez.library.Responses.LinkLists;
import com.paysez.library.Responses.LoginResponse;
import com.paysez.library.Responses.PushResponse;
import com.paysez.library.Responses.SMS_Response;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PushAPIInterface {
String url="/credopaylogin/services/slim_services/pushpay_MobReq_mobile.php";






    @POST(url)
    Call<PushResponse> generateLink(@Body RequestBody body);

    @POST(url)
    Call<SMS_Response> sendSMS(@Body RequestBody body);



    @POST(url)
    Call<MailResponse> sendMail(@Body RequestBody body);


    @POST(url)
    Call<ExpiredLinksResponse> getLinks(@Body RequestBody body);

    @POST(url)
    Call<LoginResponse> Login(@Body RequestBody body);




}