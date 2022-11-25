package com.stefanagustohutapeajsleepdn.jsleep_android.model;

public enum BedType {
    ENUM("DOUBLE, SINGLE, QUEEN, KING");

    private String BedTypeEnum;

    private BedType(String BedTypeEnum){
        this.BedTypeEnum = BedTypeEnum;
    }

    @Override public String toString(){
        return BedTypeEnum;
    }
}
