package com.example.e_wallet_fptu.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BankService {
    @GET("v2/banks")
    Call<BankResponse> getBanks();
}
