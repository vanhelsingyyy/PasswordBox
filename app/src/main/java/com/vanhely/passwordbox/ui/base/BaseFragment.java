package com.vanhely.passwordbox.ui.base;

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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.adapter.ContentAdapter;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.PaddingDataActivity;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CODE_RESULT_REQUEST = 2;
    private View view;
    private ContentAdapter adapter;
    public List<PasswordBean> listData;
    private String type = null;
    public PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private int longPosotion;
    private FileOutputStream fos;
    private com.melnykov.fab.FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.type = setType();
        EventBus.getDefault().register(this);
    }

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        fab = (com.melnykov.fab.FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        setAdapterandNotify();
        setListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.type = setType();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置和更新适配器
     */
    public void setAdapterandNotify() {
        listData = DataSupport.where("type = ?", type).find(PasswordBean.class);
        if (adapter == null) {
            adapter = new ContentAdapter(listData);
            recyclerView.setLayoutManager(new LinearLayoutManager(BoxAppliction.getmContext()));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updataPasswordList(listData);
            adapter.notifyDataSetChanged();

        }
    }

    /**
     * 打开数据填充页
     */
    public void startPaddingData(int position) {
        Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
        PasswordBean passwordBean = listData.get(position);
        intent.putExtra("password", passwordBean);
        intent.putExtra("type", type);
        startActivity(intent);
    }


    /**
     * 点击进入数据填充页和滚动师dismiss PupopWindow
     */
    public void setListener() {
        adapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                startPaddingData(position);
            }

            @Override
            public void onitemLongClick(View v, int posotion) {
                showPopupWindow(v, posotion);
            }

            @Override
            public void onIconClick(View v, int posotion) {
                longPosotion = posotion;
                setIcon();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

    }


    /**
     * 显示PopupWindow
     */
    private void showPopupWindow(View v, int posotion) {
        longPosotion = posotion;
        View popView = LayoutInflater.from(BoxAppliction.getmContext()).inflate(R.layout.pop_window, null);
        LinearLayout popEdit = (LinearLayout) popView.findViewById(R.id.pop_edit);
        LinearLayout popClear = (LinearLayout) popView.findViewById(R.id.pop_clear);
        LinearLayout popIcon = (LinearLayout) popView.findViewById(R.id.pop_icon);

        popEdit.setOnClickListener(this);
        popClear.setOnClickListener(this);
        popIcon.setOnClickListener(this);

        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        int[] location = new int[2];
        v.getLocationInWindow(location);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view, Gravity.RIGHT + Gravity.TOP, 80, location[1]);

    }


    /**
     * 删除条目
     *
     * @param position
     */
    public void clearItem(int position) {
        PasswordBean passwordBean = listData.get(position);
        DataSupport.deleteAll(PasswordBean.class, "title = ? and saveTime = ?", passwordBean.getTitle(), passwordBean.getSaveTime());
        setAdapterandNotify();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.pop_edit:
                startPaddingData(longPosotion);
                break;
            case R.id.pop_clear:
                clearItem(longPosotion);
                break;
            case R.id.pop_icon:
                setIcon();
                break;
        }
        if (view.getId() != R.id.fab) popupWindow.dismiss();
    }


    /**
     * 设置图标
     */
    public void setIcon() {
        Intent photeIntent = new Intent(Intent.ACTION_PICK, null);
        photeIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(photeIntent, CODE_PHOTO_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case CODE_PHOTO_REQUEST:
                cropPhoto(data.getData());
                break;
            case CODE_RESULT_REQUEST:
                if (data != null) {
                    savaImagePath(data);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void savaImagePath(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap icon = extras.getParcelable("data");
        String storageState = Environment.getExternalStorageState();
        if (!storageState.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/PwrodBOX/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        PasswordBean bean = listData.get(longPosotion);
        String fileName = path + bean.getTitle() + ".jpg";
        try {
            fos = new FileOutputStream(fileName);
            if (icon != null) {
                icon.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                ContentValues values = new ContentValues();
                values.put("imagePath", fileName);
                DataSupport.updateAll(PasswordBean.class, values, "title = ? and saveTime = ?", bean.getTitle(), bean.getSaveTime());
                setAdapterandNotify();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


    public abstract String setType();

    public abstract View creatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

}


