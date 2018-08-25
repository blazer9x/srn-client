package com.srn.crm.core.net;

public class RestConstants {

    //headers
    public static final String CONTENT_TYPE = "Content-Type: application/json";
    public static final String ACCEPT = "Accept: application/json";

    //base api
    public static final String BASE_URL = "/srn-api/v1/";

    //user api
    public static final String LOGIN = BASE_URL + "login.json";
    public static final String PROVISIONING = BASE_URL + "provision.json";
    public static final String PROFILE = BASE_URL + "profile.json";
    public static final String FCM_TOKEN_UPDATE = BASE_URL + "notiftoken.json";

    //promotion api
    public static final String OFFER = BASE_URL + "offer.json";
    public static final String REWARDS = BASE_URL + "rewards.json";
    public static final String VOUCHER = BASE_URL + "voucher.json";
    public static final String REDEEM = BASE_URL + "redeem.json";
    public static final String CLAIM = BASE_URL + "claim.json";

    //srn brand
    public static final String BRAND_STORE = BASE_URL + "store.json";
}
