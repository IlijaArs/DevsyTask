package com.example.devsytask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/api/v3/coins/markets?vs_currency=usd")
//endpoint

    Call<ResponseBody> getMarket();


}