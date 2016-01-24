package com.vanhely.passwordbox.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vanhely.passwordbox.R;
import com.vanhely.passwordbox.config.BoxAppliction;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public class ProtectbView extends LinearLayout {


    private Context context;
    private LayoutInflater inflater;
    private ImageView[] dots = new ImageView[4];
    private GridView number;
    private String infoText;
    public StringBuilder sbPassword = new StringBuilder();
    private NumberAdapter numberAdapter;
    private TextView title;
    private onClickNumberListener onClickNumberListener;

    private int[] numberImgs = {R.drawable.selector_password_button1,
            R.drawable.selector_password_button2,
            R.drawable.selector_password_button3,
            R.drawable.selector_password_button4,
            R.drawable.selector_password_button5,
            R.drawable.selector_password_button6,
            R.drawable.selector_password_button7,
            R.drawable.selector_password_button8,
            R.drawable.selector_password_button9,
            R.drawable.selector_password_button0};


    public ProtectbView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ProtectbView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ProtectbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    private void initView() {
        inflater = LayoutInflater.from(context);
        LayoutInflater.from(context).inflate(R.layout.widget_protect, this);
        number = (GridView) findViewById(R.id.number);
        title = (TextView) findViewById(R.id.protect_title);
        LinearLayout llDot = (LinearLayout) findViewById(R.id.ll_dot);
        for (int i = 0; i < 4; i++) {
            dots[i] = (ImageView) llDot.getChildAt(i);
            dots[i].setEnabled(true);
        }

        updataDot();
        numberAdapter = new NumberAdapter();
        number.setAdapter(numberAdapter);
        number.setSelector(new ColorDrawable(
                Color.TRANSPARENT));
        number.setOnItemClickListener(new NumberItemClickListener());

    }


    private void updataDot() {
        int length = sbPassword.length();
        for (int i = 0; i < length; i++) {
            dots[i].setEnabled(false);
        }
        for (int i = 0; i < 4 - length; i++) {
            dots[3 - i].setEnabled(true);
        }
    }

    private void changePassword(String passowrd) {
        int startLength = sbPassword.length();
        if (TextUtils.isEmpty(sbPassword) || startLength < 4) {
            sbPassword.append(passowrd);
            int endLength = sbPassword.length();
            if (onClickNumberListener != null) {
                onClickNumberListener.onClick(endLength, sbPassword.toString());
            }
            updataDot();
        }
    }

    public void clearAllPassword() {
        sbPassword.setLength(0);
        updataDot();

    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
        numberAdapter.notifyDataSetChanged();
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setDotCountListener(onClickNumberListener onClickNumberListener) {
        this.onClickNumberListener = onClickNumberListener;
    }

    private void deletePassword() {
        int length = sbPassword.length();
        if (!TextUtils.isEmpty(sbPassword) || length > 0) {
            sbPassword.setLength(length - 1);
            updataDot();
        } else {
            Toast.makeText(BoxAppliction.getmContext(), "密码为空", Toast.LENGTH_SHORT).show();
        }

    }


    class NumberAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return numberImgs.length + 2;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (position < 9) {
                convertView = inflater.inflate(R.layout.number_layout, null);
                ImageView ivNumber = (ImageView) convertView.findViewById(R.id.iv_number);
                ivNumber.setImageResource(numberImgs[position]);
            } else if (position == 9) {
                convertView = inflater.inflate(R.layout.number_text_layout, null);
                TextView numberInfo = (TextView) convertView.findViewById(R.id.number_info);
                numberInfo.setText(getInfoText());
            } else if (position == 10) {
                convertView = inflater.inflate(R.layout.number_layout, null);
                ImageView ivNumber = (ImageView) convertView.findViewById(R.id.iv_number);
                ivNumber.setImageResource(numberImgs[9]);
            } else if (position == 11) {
                convertView = inflater.inflate(
                        R.layout.number_text_layout, null);
                TextView numberInfo = (TextView) convertView
                        .findViewById(R.id.number_info);
                numberInfo.setText("删除");
            }
            return convertView;
        }

    }

    class NumberItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position < 9) {
                changePassword((position + 1) + "");
            } else if (position == 9) {
                if (onClickNumberListener != null) {
                    if (!TextUtils.isEmpty(infoText)) {
                        onClickNumberListener.onInfoClick();
                    }
                }
            } else if (position == 10) {
                changePassword(0 + "");
            } else if (position == 11) {
                deletePassword();
            }
        }
    }


    public interface onClickNumberListener {
        void onClick(int length, String password);

        void onInfoClick();
    }


}

