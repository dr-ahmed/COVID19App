package com.infectdistrack.model;

public class Patient {
    private String phoneNumber, name, gender, dateOfBirth, wilaya, moughataa;

    public Patient(String phoneNumber, String name, String gender, String dateOfBirth, String wilaya, String moughataa) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.wilaya = wilaya;
        this.moughataa = moughataa;
    }

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
}
