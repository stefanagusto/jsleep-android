package com.stefanagustohutapeajsleepdn.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;
/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */
public class Room extends Serializable {
    public int accountId;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public ArrayList<Facility> facility;
}
