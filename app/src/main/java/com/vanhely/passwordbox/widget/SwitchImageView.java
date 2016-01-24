package com.vanhely.passwordbox.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vanhely.passwordbox.R;

/**
 * Created by Administrator on 2016/1/18 0018.
 */
public class SwitchImageView extends ImageView {


    private boolean imageViewStatus = true;

    public boolean getImageViewStatus() {
        return imageViewStatus;
    }

    public void setImageViewStatus(boolean imageViewStatus) {
        this.imageViewStatus = imageViewStatus;
        if(imageViewStatus){
            setImageResource(R.drawable.app_btn_check_on);
        }else{
            setImageResource(R.drawable.app_btn_check_off_focused);
        }
    }

    public void changeimageViewStatus(){
        setImageViewStatus(!imageViewStatus);
    }



    public SwitchImageView(Context context) {
        super(context);
    }

    public SwitchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
