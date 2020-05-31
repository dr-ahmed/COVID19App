package com.infectdistrack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private Integer id, associate_admin;
    private String fullName, email, password, category, wilaya, establishment;

    public User(Integer id, String fullName, String email, String password, String category, Integer associate_admin, String wilaya, String establishment) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.category = category;
        this.associate_admin = associate_admin;
        this.wilaya = wilaya;
        this.establishment = establishment;
    }

    public Integer getId() {
        return id;
    }

    protected User(Parcel in) {
        id = in.readInt();
        fullName = in.readString();
        email = in.readString();
        password = in.readString();
        category = in.readString();
        associate_admin = in.readInt();
        wilaya = in.readString();
        establishment = in.readString();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAssociateAdmin() {
        return associate_admin;
    }

    public void setAssociateAdmin(Integer associate_admin) {
        this.associate_admin = associate_admin;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(category);
        dest.writeInt(associate_admin);
        dest.writeString(wilaya);
        dest.writeString(establishment);
    }
}
