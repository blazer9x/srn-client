package com.srn.crm.core.net;

import com.srn.crm.core.model.BaseResponse;
import com.srn.crm.core.model.request.ClaimRequest;
import com.srn.crm.core.model.request.FcmTokenUpdatesRequest;
import com.srn.crm.core.model.request.RedeemRequest;
import com.srn.crm.core.model.response.FcmToken;
import com.srn.crm.core.model.response.UserProfile;
import com.srn.crm.core.model.response.Offer;
import com.srn.crm.core.model.response.PointRewards;
import com.srn.crm.core.model.response.Provisioning;
import com.srn.crm.core.model.response.Redeem;
import com.srn.crm.core.model.response.Store;
import com.srn.crm.core.model.response.Voucher;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public interface RestService {

    /* device and user service */
    @Headers(RestConstants.CONTENT_TYPE)
    @POST(RestConstants.PROVISIONING)
    void srnProvisioning(@Body TypedInput request, Callback<BaseResponse<Provisioning>> callback);

    @Headers(RestConstants.CONTENT_TYPE)
    @POST(RestConstants.LOGIN)
    void srnLogin(@Body TypedInput request, Callback<BaseResponse<UserProfile>> callback);


    @Headers(RestConstants.CONTENT_TYPE)
    @POST(RestConstants.FCM_TOKEN_UPDATE)
    void srnUpdateToken(@Body FcmTokenUpdatesRequest request, Callback<BaseResponse<FcmToken>> callback);

    /* promotion service */
    @Headers(RestConstants.CONTENT_TYPE)
    @GET(RestConstants.OFFER)
    void srnOffer(Callback<BaseResponse<Offer>> callback);

    @Headers(RestConstants.CONTENT_TYPE)
    @GET(RestConstants.VOUCHER)
    void srnVoucher(Callback<BaseResponse<Voucher>> callback);

    @Headers(RestConstants.CONTENT_TYPE)
    @POST(RestConstants.REDEEM)
    void srnRedeem(@Body RedeemRequest redeemRequest, Callback<BaseResponse<Redeem>> callback);

    @Headers(RestConstants.CONTENT_TYPE)
    @POST(RestConstants.CLAIM)
    void srnClaim(@Body ClaimRequest claimRequest, Callback<BaseResponse<ClaimRequest>> callback);

    @Headers(RestConstants.CONTENT_TYPE)
    @GET(RestConstants.REWARDS)
    void srnRewards(Callback<BaseResponse<PointRewards>> callback);

    /* brand and store service */
    @Headers(RestConstants.CONTENT_TYPE)
    @GET(RestConstants.BRAND_STORE)
    void srnStore(@Query("brandId") int brandId, Callback<BaseResponse<Store>> callback);


}
