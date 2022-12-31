package com.stefanagustohutapeajsleepdn.jsleep_android.model;
/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */
public class Invoice extends Serializable {
    public int buyerId, renterId;

    public RoomRating rating;
    public PaymentStatus status;
    //enum
    public enum RoomRating
    {
        NONE, BAD, NEUTRAL, GOOD
    }

    //enum
    public enum PaymentStatus
    {
        FAILED, WAITING, SUCCESS
    }
}
