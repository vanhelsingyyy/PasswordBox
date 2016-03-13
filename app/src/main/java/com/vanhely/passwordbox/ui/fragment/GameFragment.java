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
 *
 */
public class GameFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "GameFragment";
    private ContentAdapter adapter;
    private List<PasswordBean> games;
    private PopupWindow popupWindow;
    private View view;
    private int longPosotion;
    private RecyclerView recyclerView;
    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CODE_RESULT_REQUEST = 2;
    private FileOutputStream fos;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public View creatView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout, container, false);
        return view;
    }


    @Override
    public void initViewId() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(this);
        setAdapterandNotify();
    }


    @Override
    public void initListener() {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void startPaddingData(int position) {
        Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
        PasswordBean passwordBean = games.get(position);
        intent.putExtra("password", passwordBean);
        intent.putExtra("type", "game");
        startActivity(intent);
    }

    private void showPopupWindow(View v, int posotion) {
        View popView = LayoutInflater.from(BoxAppliction.getmContext()).inflate(R.layout.pop_window, null);
        LinearLayout popEdit = (LinearLayout) popView.findViewById(R.id.pop_edit);
        LinearLayout popClear = (LinearLayout) popView.findViewById(R.id.pop_clear);
        LinearLayout popIcon = (LinearLayout) popView.findViewById(R.id.pop_icon);
        longPosotion = posotion;
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

    private void clearItem(int position) {
        PasswordBean passwordBean = games.get(position);
        DataSupport.deleteAll(PasswordBean.class, "title = ? and saveTime = ?", passwordBean.getTitle(), passwordBean.getSaveTime());
        setAdapterandNotify();

    }

    public void onEventMainThread(String str) {

        if (str.equals("game")) {
            setAdapterandNotify();
        } else if (str.equals("resetContent")) {
            setAdapterandNotify();
        }
    }

    private void setAdapterandNotify() {
        games = DataSupport.where("type = ?", "game").find(PasswordBean.class);
        if (adapter == null) {
            adapter = new ContentAdapter(games);
            recyclerView.setLayoutManager(new LinearLayoutManager(BoxAppliction.getmContext()));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updataPasswordList(games);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
                intent.putExtra("type", "game");
                startActivity(intent);
                break;
            case R.id.pop_edit:
                startPaddingData(longPosotion);
                popupWindow.dismiss();
                break;
            case R.id.pop_clear:
                clearItem(longPosotion);
                popupWindow.dismiss();
                break;
            case R.id.pop_icon:
                setIcon();
                popupWindow.dismiss();
                break;
        }

    }
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
        PasswordBean bean = games.get(longPosotion);
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

}
