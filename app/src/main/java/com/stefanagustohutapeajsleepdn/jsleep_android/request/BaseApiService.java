package com.stefanagustohutapeajsleepdn.jsleep_android.request;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.Account;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);
}
