package com.vanhely.passwordbox.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/1/12 0012.
 *
 */
public abstract class BaseFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = creatView(inflater, container, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        initViewId();
        initListener();
        return view;
    }

    public abstract void initViewId();
    public abstract void initListener();



    public abstract View creatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}


