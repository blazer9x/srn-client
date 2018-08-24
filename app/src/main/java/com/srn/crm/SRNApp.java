package com.srn.crm;


import com.srn.crm.core.SRNApplication;

public class SRNApp extends SRNApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean isProduction() {
        return false;
    }
}
