package com.bakon.base_lib.model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
    public int age;
    public int area;
    public int city;
    public int province;
    public int id;
    public int user_id;
    public String code;
    public String imgurl;
    public String img_url;
    public String user_img_url;

    protected User(Parcel in) {
        age = in.readInt();
        area = in.readInt();
        city = in.readInt();
        province = in.readInt();
        id = in.readInt();
        user_id = in.readInt();
        code = in.readString();
        imgurl = in.readString();
        img_url = in.readString();
        user_img_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeInt(area);
        dest.writeInt(city);
        dest.writeInt(province);
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeString(code);
        dest.writeString(imgurl);
        dest.writeString(img_url);
        dest.writeString(user_img_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
