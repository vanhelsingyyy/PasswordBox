package com.vanhely.passwordbox.ui.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.melnykov.fab.FloatingActionButton;
import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.adapter.ContentAdapter;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.PaddingDataActivity;
import com.vanhely.passwordbox.ui.base.BaseFragment;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class SocialFragment extends BaseFragment implements View.OnClickListener {

    public static final String TYPE = "social";
    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CODE_RESULT_REQUEST = 2;
    private FileOutputStream fos;




    @Override
    public View creatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }


    @Override
    public String setType() {
        return TYPE;
    }

    public void onEventMainThread(String str) {
        if (str.equals("social")) {
            setAdapterandNotify();
        } else if (str.equals("resetContent")) {
            setAdapterandNotify();
        }
    }



}
