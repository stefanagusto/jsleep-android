package com.stefanagustohutapeajsleepdn.jsleep_android.model;

public enum City {
    ENUM("BALI, BANDUNG, SURABAYA, JAKARTA, SEMARANG, MEDAN, DEPOK, BEKASI, LAMPUNG");

    private String CityEnum;

    private City(String CityEnum){
        this.CityEnum = CityEnum;
    }

    @Override public String toString(){
        return CityEnum;
    }
}
