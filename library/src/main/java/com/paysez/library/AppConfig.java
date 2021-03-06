package com.paysez.library;

public class AppConfig {


    //Prod
//    public static String sdk_version = "v2.7-beta";
//    public static String baseurl = "https://pg.credopay.in";
//    public static String ENDPOINT_FIRSTLEG_REQUEST = "/credopay/api/CPDirectPG.php";
//    public static String cardSubmit = "https://pg.credopay.in/credopay/api/visasubmit1.php";
//    public static String RupayLeg = "https://pg.credopay.in/credopay/api/CPDirectPG.php";
//    public static String returnUrl = "https://pg.credopay.in/credopay/api/appresponsemerchant.php?randomgen=";
//    public static String netbanking_sale = "https://pg.credopay.in/credopay/CPWebPG.php";
//    public static String netbanking_query = "https://pg.credopay.in/credopaylogin/NBQuery_status.php";
//    public static String push_payment = "https://pg.credopay.in/credopaylogin/pushpay_MobReq.php";
    //UAT


    public static String sdk_version = "v0.7-alpha";
    public static String baseurl = "https://pguat.credopay.info";
    public static String ENDPOINT_FIRSTLEG_REQUEST = "/credopay/api/CPDirectPG.php";
    public static String cardSubmit = "https://pguat.credopay.info/credopay/api/visasubmit1.php";
    public static String RupayLeg = "https://pguat.credopay.info/credopay/api/CPDirectPG.php";
    public static String returnUrl = "https://pguat.credopay.info/credopay/api/appresponsemerchant.php?randomgen=";
    public static String netbanking_sale = "https://pguat.credopay.info/credopay/CPWebPG.php";
    public static String netbanking_query = "https://pguat.credopay.info/credopaylogin/NBQuery_status.php";
   // public static String push_payment = "https://pguat.credopay.info/credopaylogin/pushpay_MobReq.php";
   public static String push_payment = "https://pguat.credopay.info/credopaylogin/services/slim_services/pushpay_MobReq_mobile.php";


//local debug
//    public static   String    baseurl =                   "https://pg.credopay.in";
//    public static   String    ENDPOINT_FIRSTLEG_REQUEST = "/credopay/api/CPDirectPG.php";
//    public  static  String   cardSubmit =                 "http://192.168.42.219:8080";
//    public static   String    RupayLeg =                  "https://pg.credopay.in/credopay/api/CPDirectPG.php";
//    public static   String    redirectionUrl =            "https://pg.credopay.in/credopaylogin/digitalpaySuccess.php";
//    public static   String    returnUrl =                 "https://pg.credopay.in/credopay/api/appresponsemerchant.php?randomgen=";
}
