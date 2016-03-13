package com.vanhely.passwordbox.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.BoxAppliction;
import com.vanhely.passwordbox.config.Config;
import com.vanhely.passwordbox.model.PasswordBean;
import com.vanhely.passwordbox.ui.fragment.GameFragment;
import com.vanhely.passwordbox.ui.fragment.SocialFragment;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class ContentAdapter extends RecyclerView.Adapter {



    private List<PasswordBean> passwordBeans;
    public OnItemClickListener onItemClickListener;

    public ContentAdapter(List<PasswordBean> passwordBeans) {
        this.passwordBeans = passwordBeans;
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

        void onIconClick(View v, int posotion);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        CircularImageView icon = ((ViewHolder) holder).icon;
        PasswordBean passwordBean = passwordBeans.get(position);
        String image = passwordBean.getImage();
        String imagePath = passwordBean.getImagePath();
        if (TextUtils.isEmpty(imagePath)) {
            if ("1".equals(image)) {
               icon.setImageResource(passwordBean.getImageId());
            } else {
                viewHolder.icon.setImageResource(R.drawable.ic_launcher);
            }
        }else {
            File file = new File(imagePath);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imagePath));
                viewHolder.icon.setImageBitmap(bitmap);
            }else {
                Toast.makeText(BoxAppliction.getmContext(),"图片不存在",Toast.LENGTH_SHORT).show();
            }
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
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener.onIconClick(viewHolder.itemView,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return passwordBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CircularImageView icon;
        public TextView desc;
        public TextView title;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (CircularImageView) itemView.findViewById(R.id.content_icon);
            desc = (TextView) itemView.findViewById(R.id.content_desc);
            title = (TextView) itemView.findViewById(R.id.content_title);
            time = (TextView) itemView.findViewById(R.id.content_time);
        }


    }
}
