package com.srn.crm.core.api;

import android.content.Context;

import com.srn.crm.core.SRNApplication;
import com.srn.crm.core.net.RestHelper;
import com.srn.crm.core.net.RestService;
import com.srn.crm.core.utils.TimeUtils;

import retrofit.RequestInterceptor;

public abstract class BaseApi {

    public static final int DEFAULT_ENDPOINT = 0;

    protected Context mContext;
    protected static  final long REQUEST_TIMEOUT = 2L * TimeUtils.ONE_MINUTE;

    public BaseApi(Context context) {
        this.mContext = context;
    }

    public static RestService buildRestService(Context context) {
        return buildRestService(context, REQUEST_TIMEOUT);
    }

    public static RestService buildRestService(Context context, long timeout) {
        return buildRestService(context, timeout, DEFAULT_ENDPOINT);
    }

    public static RestService buildRestService(Context context, long restTimeout, int idxEndPoint) {
        return RestHelper.getInstance().buildRestService(true,
                relativeEndPoint(context, idxEndPoint),
                restTimeout,
                restTimeout, new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //TODO: get default required url parameter
                    }
                }, ((SRNApplication)context.getApplicationContext()).getRetrofitLogLevel());
    }

    public static String relativeEndPoint(Context context, int idxEndPoint) {
        final Context appContext = context.getApplicationContext();
        String strEndPoint = "";
        if (appContext instanceof SRNApplication) {
            switch (idxEndPoint) {
                case DEFAULT_ENDPOINT:
                    strEndPoint = ((SRNApplication)appContext).getServerEndPoint();
                    break;
                default:
                    throw new IllegalStateException("Application must be extended from " + SRNApplication.class.getName());
            }
            return strEndPoint;
        } else {
            throw new IllegalStateException("Application must be extended from " + SRNApplication.class.getName());
        }
    }


}
