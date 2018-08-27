package com.srn.crm.core.net;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
//import retrofit.converter.GsonConverter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import timber.log.Timber;

public class RestHelper {


    private static final Object LOCK = new Object();
    private static final Object CALL_TAG = new Object();

    private static RestHelper sInstance;

    private OkHttpClient okHttpClient;

    public static RestHelper getInstance() {
        synchronized (LOCK) {
            if(sInstance == null) {
                sInstance = new RestHelper();
            }
        }
        return sInstance;
    }

    public RestService buildRestService(boolean serverEndpoint, String endPoint, long connectTimeout, long readTimeout, RequestInterceptor requestInterceptor, RestAdapter.LogLevel logLevel) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setClient(getOkClient(connectTimeout, readTimeout))
                .setEndpoint(endPoint)
                .setLogLevel(logLevel)
                .setLog(new Log());
                //.setConverter(new GsonConverter(new Gson(), serverEndpoint));
        if (requestInterceptor != null) {
            builder.setRequestInterceptor(requestInterceptor);
        }
        return builder.build().create(RestService.class);
    }

    private OkClient getOkClient(long connectTimeout, long readTimeout) {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient() {
                @Override
                public Call newCall(Request request) {
                    Request.Builder builder = request.newBuilder();
                    builder.tag(CALL_TAG);
                    return super.newCall(builder.build());
                }
            };
        }
        okHttpClient.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);
        return new OkClient(okHttpClient);
    }


    private static class GsonConverter extends retrofit.converter.GsonConverter {

        protected Gson gson;
        protected boolean mFromServer;

        public GsonConverter(Gson gson, boolean fromServer) {
            super(gson);
            this.gson = gson;
            mFromServer = fromServer;
        }

        @Override
        public Object fromBody(TypedInput body, Type type) throws ConversionException {
            String charset = "UTF-8";
            if (body.mimeType() != null) {
                charset = MimeUtil.parseCharset(body.mimeType(), "UTF-8");
            }
            InputStreamReader isr = null;
            BufferedReader r = null;
            try {
                isr = new InputStreamReader(body.in(), charset);
                r = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                String resultCode, data = null, timestamp, output = null;
                while ((line = r.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JSONObject jObject = new JSONObject(stringBuilder.toString());
                resultCode = jObject.getString("resultCode");
                timestamp = jObject.getString("timestamp");
                if (!jObject.isNull("data")) {
                    //data = SecurityUtils.AESDecrypt(jObject.getString("data"), mFromServer);
                    data = jObject.getString("data");
                }

                output = "{\"resultCode\":" + resultCode + ", \"timestamp\":" + timestamp + ",\"data\":" + data + "}";
                //Timber.d("retrofit::decrypt:%s", getSimpleData(output, type));
                return gson.fromJson(output, type);
            } catch (JSONException | JsonParseException | IOException e) {
                throw new ConversionException(e);
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException ignored) {
                    }
                }
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        public TypedOutput toBody(Object object) {
            return super.toBody(object);
        }

        /**
         * <p>Simplify log string, leaving only important output to be logged into log files.</p>
         * <p>Currently only used for Offer and Voucher.</p>
         *
         * @param output Original string output.
         * @param type   Type token to convert JSON string into output.
         * @return Simplified.
         */
        /*private String getSimpleData(String output, Type type) {
            Object objOutput = gson.fromJson(output, type);
            if (objOutput instanceof DataResponse) {
                DataResponse dataResponse = (DataResponse) objOutput;
                Object dataObj = dataResponse.getData();
                if (dataObj == null) {
                    return output;
                }

                List<Object> objectList = new ArrayList<>();
                if (dataObj instanceof OfferList) {
                    OfferList offerList = (OfferList) dataObj;
                    boolean isOffer = false;
                    for (Object obj : offerList.getOfferList()) {
                        if (!(obj instanceof Offer)) {
                            continue;
                        }
                        isOffer = true;
                        Offer offer = (Offer) obj;
                        objectList.add(new OfferLog(offer));
                    }
                    if (isOffer) {
                        offerList.setOfferList(objectList);
                        dataResponse.setData(offerList);
                        output = gson.toJson(dataResponse);
                    }
                } else if (dataObj instanceof List && ((List) dataObj).size() > 0 && ((List) dataObj).get(0) instanceof Voucher) {
                    List<Voucher> voucherList = (List<Voucher>) dataObj;
                    for (Voucher voucher : voucherList) {
                        objectList.add(new VoucherLog(voucher));
                    }
                    dataResponse.setData(voucherList);
                    output = gson.toJson(dataResponse);
                }
            }
            return output;
        }*/
    }

    private static class Log implements RestAdapter.Log {

        @Override
        public void log(String msg) {
            Timber.d("retrofit::%s", msg);
        }
    }


}
