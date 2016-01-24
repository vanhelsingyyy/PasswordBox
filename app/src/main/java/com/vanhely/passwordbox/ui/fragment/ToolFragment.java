package com.vanhely.passwordbox.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.adapter.ContentAdapter;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.PaddingDataActivity;
import com.vanhely.passwordbox.ui.base.BaseFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class ToolFragment extends BaseFragment implements View.OnClickListener {
    private ContentAdapter adapter;
    private List<PasswordBean> tools;
    private View view;
    private int longPosotion;
    private PopupWindow popupWindow;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View creatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        return view;
    }


    @Override
    public void initViewId() {
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

    public void onEventMainThread(String str) {
        if (str.equals("tool")) {
            setAdapterandNotify();
        } else if (str.equals("resetContent")) {
            setAdapterandNotify();
        }
    }


    private void startPaddingData(int position) {
        Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
        PasswordBean passwordBean = tools.get(position);
        intent.putExtra("password", passwordBean);
        intent.putExtra("type", "tool");
        startActivity(intent);
    }

    private void setAdapterandNotify() {
        tools = DataSupport.where("type = ?", "tool").find(PasswordBean.class);
        if (adapter == null) {
            adapter = new ContentAdapter(tools, Config.toolImages);
            recyclerView.setLayoutManager(new LinearLayoutManager(BoxAppliction.getmContext()));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updataPasswordList(tools);
            adapter.notifyDataSetChanged();
        }
    }

    private void showPopupWindow(View v, int posotion) {
        View popView = LayoutInflater.from(BoxAppliction.getmContext()).inflate(R.layout.pop_window, null);
        LinearLayout popEdit = (LinearLayout) popView.findViewById(R.id.pop_edit);
        LinearLayout popClear = (LinearLayout) popView.findViewById(R.id.pop_clear);
        longPosotion = posotion;
        popEdit.setOnClickListener(this);
        popClear.setOnClickListener(this);

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
        PasswordBean passwordBean = tools.get(position);
        String image = passwordBean.getImage();
        if ("1".equals(image)) {
            Toast.makeText(BoxAppliction.getmContext(), "请不要删除初始Item哦!", Toast.LENGTH_SHORT).show();
        }else {
            DataSupport.deleteAll(PasswordBean.class, "title = ? and saveTime = ?", passwordBean.getTitle(), passwordBean.getSaveTime());
            setAdapterandNotify();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
                intent.putExtra("type", "tool");
                startActivity(intent);
                break;
            case R.id.pop_edit:
                startPaddingData(longPosotion);
                break;
            case R.id.pop_clear:
                clearItem(longPosotion);
                break;
        }

    }
}
