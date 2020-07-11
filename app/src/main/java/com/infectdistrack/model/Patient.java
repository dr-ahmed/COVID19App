package com.infectdistrack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient implements Parcelable {
    private String phoneNumber, name, gender, dateOfBirth, wilaya, moughataa;

    public Patient(String phoneNumber, String name, String gender, String dateOfBirth, String wilaya, String moughataa) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.wilaya = wilaya;
        this.moughataa = moughataa;
    }

    protected Patient(Parcel in) {
        phoneNumber = in.readString();
        name = in.readString();
        gender = in.readString();
        dateOfBirth = in.readString();
        wilaya = in.readString();
        moughataa = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getMoughataa() {
        return moughataa;
    }

    public void setMoughataa(String moughataa) {
        this.moughataa = moughataa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneNumber);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(dateOfBirth);
        dest.writeString(wilaya);
        dest.writeString(moughataa);
    }
}
