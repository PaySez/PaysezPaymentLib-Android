package com.paysez.library;
public class AppConfig {
    // Prod

    public static   String    baseurl =                   "https://pg.credopay.in";
    public static   String    ENDPOINT_FIRSTLEG_REQUEST = "/credopay/api/CPDirectPG.php";
    public  static  String   cardSubmit =                 "https://pg.credopay.in/credopay/api/visasubmit1.php";
    public static   String    RupayLeg =                  "https://pg.credopay.in/credopay/api/CPDirectPG.php";
    public static   String    redirectionUrl =            "https://pg.credopay.in/credopaylogin/digitalpaySuccess.php";
    public static   String    returnUrl =                 "https://pg.credopay.in/credopay/api/appresponsemerchant.php?randomgen=";


    //UAT


//    public static String baseurl= "https://pguat.credopay.info";
//    public static String ENDPOINT_FIRSTLEG_REQUEST = "/credopay/api/CPDirectPG.php";
//    public  static String cardSubmit = "https://pguat.credopay.info/credopay/api/visasubmit1.php";
//    public static String RupayLeg = "https://pguat.credopay.info/credopay/api/CPDirectPG.php";
//    public static String redirectionUrl = "https://pguat.credopay.info/credopaylogin/digitalpaySuccess.php";
//    public static String returnUrl = "https://pguat.credopay.info/credopay/api/appresponsemerchant.php?randomgen=";

}
