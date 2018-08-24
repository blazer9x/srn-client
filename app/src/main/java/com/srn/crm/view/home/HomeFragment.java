package com.srn.crm.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srn.crm.R;
import com.srn.crm.view.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

}
