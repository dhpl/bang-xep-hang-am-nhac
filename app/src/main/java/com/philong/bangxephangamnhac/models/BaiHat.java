package com.philong.bangxephangamnhac.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Long on 6/15/2017.
 */

public class BaiHat implements Parcelable{

    private String mTen;
    private String mCaSi;
    private String mLink;
    private String mLinkHinh;

    public BaiHat(String ten, String caSi, String link, String linkHinh) {
        mTen = ten;
        mCaSi = caSi;
        mLink = link;
        mLinkHinh = linkHinh;
    }

    protected BaiHat(Parcel in) {
        mTen = in.readString();
        mCaSi = in.readString();
        mLink = in.readString();
        mLinkHinh = in.readString();
    }

    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
        @Override
        public BaiHat createFromParcel(Parcel in) {
            return new BaiHat(in);
        }

        @Override
        public BaiHat[] newArray(int size) {
            return new BaiHat[size];
        }
    };

    public String getTen() {
        return mTen;
    }

    public void setTen(String ten) {
        mTen = ten;
    }

    public String getCaSi() {
        return mCaSi;
    }

    public void setCaSi(String caSi) {
        mCaSi = caSi;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getLinkHinh() {
        return mLinkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        mLinkHinh = linkHinh;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTen);
        dest.writeString(mCaSi);
        dest.writeString(mLink);
        dest.writeString(mLinkHinh);
    }
}
