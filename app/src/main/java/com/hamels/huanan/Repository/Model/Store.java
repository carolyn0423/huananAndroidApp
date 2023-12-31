package com.hamels.huanan.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Store implements Parcelable {
    public static final String TAG = Store.class.getSimpleName();

    @SerializedName("location_name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("business_hours")
    private String opening;
    @SerializedName("content")
    private String description;
    @SerializedName("distance")
    private double distance;
    @SerializedName("isOften")
    private String isOften;
    @SerializedName("location_id")
    private String location_id;
    @SerializedName("Often_uid")
    private String Often_uid;
    @SerializedName("head_location_flag")
    private String HeadLocationFlag;
    @SerializedName("order_source")
    private String order_source;
    @SerializedName("picture_url")
    private String picture_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getIsOften() {
        return isOften;
    }

    public void setIsOften(String isOften) {
        this.isOften = isOften;
    }

    public String getLocationID() {
        return location_id;
    }

    public void setLocationID(String location_id) {
        this.location_id = location_id;
    }

    public String getOften_uid() {
        return Often_uid;
    }

    public void setOften_uid(String Often_uid) { this.Often_uid = Often_uid; }

    public String getHeadLocationFlag() {
        return HeadLocationFlag;
    }

    public String getOrderSource() {
        return order_source;
    }

    public String getPictureUrl() {
        return picture_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(opening);
        dest.writeString(description);
        dest.writeDouble(distance);
        dest.writeString(isOften);
        dest.writeString(Often_uid);
        dest.writeString(HeadLocationFlag);
        dest.writeString(order_source);
        dest.writeString(picture_url);
    }
}
