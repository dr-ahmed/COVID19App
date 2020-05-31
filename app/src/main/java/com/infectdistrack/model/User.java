package com.infectdistrack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name, email, password, category, associate_admin, location, establishment;

    public User(String name, String email, String password, String category, String associate_admin, String location, String establishment) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.associate_admin = associate_admin;
        this.location = location;
        this.establishment = establishment;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        category = in.readString();
        associate_admin = in.readString();
        location = in.readString();
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

    public String getAssociateAdmin() {
        return associate_admin;
    }

    public void setAssociateAdmin(String associate_admin) {
        this.associate_admin = associate_admin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(category);
        dest.writeString(associate_admin);
        dest.writeString(location);
        dest.writeString(establishment);
    }
}
