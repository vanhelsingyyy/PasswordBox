package com.vanhely.passwordbox.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class PasswordBean extends DataSupport implements Parcelable {
    private int id;
    private String type;
    private String title;
    private String desc;
    private String image;
    private String imagePath;
    private int imageId;
    private String password;
    private String username;
    private String saveTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.image);
        dest.writeString(this.password);
        dest.writeString(this.username);
        dest.writeString(this.saveTime);
    }

    public PasswordBean() {
    }

    protected PasswordBean(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.image = in.readString();
        this.password = in.readString();
        this.username = in.readString();
        this.saveTime = in.readString();
    }

    public static final Parcelable.Creator<PasswordBean> CREATOR = new Parcelable.Creator<PasswordBean>() {
        public PasswordBean createFromParcel(Parcel source) {
            return new PasswordBean(source);
        }

        public PasswordBean[] newArray(int size) {
            return new PasswordBean[size];
        }
    };

    @Override
    public String toString() {
        return "PasswordBean{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", image='" + image + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", saveTime='" + saveTime + '\'' +
                '}';
    }
}
