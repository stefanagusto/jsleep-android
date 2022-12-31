package com.stefanagustohutapeajsleepdn.jsleep_android.model;
/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */
import java.util.Date;

public class Payment extends Invoice {
    public Date to, from;
    private int roomId;
    public double totalPrice;
}
