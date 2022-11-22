package com.stefanagustohutapeajsleepdn.jsleep_android.request;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.Account;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Room;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);
    @GET("room/{id}")
    Call<Room> getRoom(@Path("id") int id);
    @GET("renter/{id}")
    Call<Room> getRenter(@Path("id") int id);
}
