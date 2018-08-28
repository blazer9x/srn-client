package com.srn.crm.core.callback;

public interface Callback<T> {

    void onSuccess(T result);

    void onFailed(Throwable throwable);
}
