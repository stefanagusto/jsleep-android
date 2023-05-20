package com.stefanagustohutapeajsleepdn.jsleep_android.request;

import com.stefanagustohutapeajsleepdn.jsleep_android.PaymentActivity;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Account;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.BedType;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.City;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Facility;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Renter;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Room;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Payment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);

    @POST("/account/login")
    Call<Account> loginAccount(@Query("email") String username,
                               @Query("password") String password);

    @POST("/account/register")
    Call<Account> registerRequest(@Query("name") String name,
                                  @Query("email") String email,
                                  @Query("password") String password);

    @POST("/account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("/account/{id}/topUp")
    Call<Boolean>topUp(@Path("id") int id,
                       @Query("balance") double balance);

    @GET("/room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page,
                                @Query("pageSize") int pageSize);

    @POST("/room/create")
    Call<Room> create (@Query("accountId") int accountId,
                       @Query("name") String name,
                       @Query("size") int size,
                       @Query("price") int price,
                       @Query("facility") ArrayList<Facility> facility,
                       @Query("city") City city,
                       @Query("address") String address,
                       @Query("bedType") BedType bedType);

    @POST("/payment/create")
    Call<Payment> createPayment(@Query("buyerId") int buyerId,
                                @Query("renterId") int renterId,
                                @Query("roomId") int roomId,
                                @Query("from") String from,
                                @Query("to") String to);

    @DELETE("/room/{id}/delete")
    Call<Boolean> deleteRoom(@Path("id") int id);
}
