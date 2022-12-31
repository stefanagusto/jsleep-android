package com.stefanagustohutapeajsleepdn.jsleep_android.model;
/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */
public class Account extends Serializable {
    public String name;
    public String password;
    public Renter renter;
    public String email;
    public double balance;

    @Override
    public String toString(){
        return "Account{" +
                "balance=" + balance +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", renter=" + renter +
                '}';
    }
}
