package com.infectdistrack.model;

import androidx.annotation.NonNull;

public class COVID19Form {
    private Integer id, parentUserId, age;
    private String name, phoneNumber, wilaya, suspectedCases, symptoms, category, confirmedCovid19Cases, evolution;
    private Boolean gendre;

    public COVID19Form() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Integer parentUserId) {
        this.parentUserId = parentUserId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getSuspectedCases() {
        return suspectedCases;
    }

    public void setSuspectedCases(String suspectedCases) {
        this.suspectedCases = suspectedCases;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConfirmedCovid19Cases() {
        return confirmedCovid19Cases;
    }

    public void setConfirmedCovid19Cases(String confirmedCovid19Cases) {
        this.confirmedCovid19Cases = confirmedCovid19Cases;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public Boolean getGendre() {
        return gendre;
    }

    public void setGendre(Boolean gendre) {
        this.gendre = gendre;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nom : " + getName() + "\n"
                + "Tel : " + getPhoneNumber() + "\n"
                + "Genre : " + getGendre() + "\n"
                + "Age : " + getAge() + "\n"
                + "Wilaya : " + getWilaya() + "\n"
                + "Cas suspects : " + getSuspectedCases();
    }
}
