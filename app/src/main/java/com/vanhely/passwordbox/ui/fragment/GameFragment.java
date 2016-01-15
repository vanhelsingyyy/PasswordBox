package com.vanhely.passwordbox.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class GameFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "GameFragment";
    private ContentAdapter adapter;
    private RecyclerView recyclerView;
    private List<PasswordBean> games;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View creatView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(this);
        setAdapterandNotify();
        adapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
                PasswordBean passwordBean = games.get(position);
                intent.putExtra("password", passwordBean);
                intent.putExtra("type", "game");
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(String str) {

        if (str.equals("game")) {
            setAdapterandNotify();
        }
    }

    private void setAdapterandNotify() {
        games = DataSupport.where("type = ?", "game").find(PasswordBean.class);
        if (adapter == null) {
            adapter = new ContentAdapter(games, Config.gameImages);
            recyclerView.setLayoutManager(new LinearLayoutManager(BoxAppliction.getmContext()));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updataPasswordList(games);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BoxAppliction.getmContext(), PaddingDataActivity.class);
        intent.putExtra("type", "game");
        startActivity(intent);

    }
}
