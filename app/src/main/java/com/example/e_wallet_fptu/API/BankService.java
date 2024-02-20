package com.example.e_wallet_fptu.API;

import com.example.e_wallet_fptu.Entity.Bank;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BankService {
    @GET("v2/banks")
    Call<List<Bank>> getBanks();
}
