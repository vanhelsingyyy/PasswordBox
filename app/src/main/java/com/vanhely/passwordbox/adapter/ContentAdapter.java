package com.vanhely.passwordbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.model.PasswordBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class ContentAdapter extends RecyclerView.Adapter {


    private List<PasswordBean> passwordBeans;
    private int[] imageRes;
    public OnItemClickListener onItemClickListener;

    public ContentAdapter(List<PasswordBean> passwordBeans, int[] imageRes) {
        this.passwordBeans = passwordBeans;
        this.imageRes = imageRes;

    }

    public void updataPasswordList(List<PasswordBean> passwordBeans) {
        this.passwordBeans = passwordBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onitemLongClick(View v, int posotion);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(BoxAppliction.getmContext()).inflate(R.layout.content_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        PasswordBean passwordBean = passwordBeans.get(position);

        String image = passwordBean.getImage();
        if ("1".equals(image)) {

            viewHolder.icon.setImageResource(imageRes[position]);
        } else {
            viewHolder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        viewHolder.title.setText(passwordBean.getTitle());
        viewHolder.desc.setText(passwordBean.getDesc());
        viewHolder.time.setText(passwordBean.getSaveTime());

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(viewHolder.itemView, pos);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener.onitemLongClick(viewHolder.itemView, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return passwordBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView desc;
        public TextView title;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.content_icon);
            desc = (TextView) itemView.findViewById(R.id.content_desc);
            title = (TextView) itemView.findViewById(R.id.content_title);
            time = (TextView) itemView.findViewById(R.id.content_time);
        }


    }
}
