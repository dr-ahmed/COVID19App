package com.infectdistrack.model;

import androidx.annotation.NonNull;

public class Covid19Form {
    private Integer id, parentUserId;
    private String name, phoneNumber, gendre, wilaya, age, suspectedCases, symptoms, terrain, confirmedCovid19Case, evolution;

    public Covid19Form() {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getConfirmedCovid19Case() {
        return confirmedCovid19Case;
    }

    public void setConfirmedCovid19Case(String confirmedCovid19Case) {
        this.confirmedCovid19Case = confirmedCovid19Case;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getGendre() {
        return gendre;
    }

    public void setGendre(String gendre) {
        this.gendre = gendre;
    }

    @NonNull
    @Override
    public String toString() {
        return "Parent ID : " + getParentUserId() + "\n"
                + "Nom : " + getName() + "\n"
                + "Tel : " + getPhoneNumber() + "\n"
                + "Genre : " + getGendre() + "\n"
                + "Age : " + getAge() + "\n"
                + "Wilaya : " + getWilaya() + "\n"
                + "Cas suspects : " + getSuspectedCases() + "\n"
                + "Symptômes : " + getSymptoms() + "\n"
                + "Terrain : " + getTerrain() + "\n"
                + "Cas confirmé de COVID-19 : " + getConfirmedCovid19Case() + "\n"
                + "Evolution : " + getEvolution();
    }
}
