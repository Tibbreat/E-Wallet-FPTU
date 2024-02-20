package com.example.e_wallet_fptu.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankApiClient {
    private static final String BASE_URL = "https://api.vietqr.io/v2/banks";
    private Retrofit retrofit;
    private BankService bankService;

    public BankApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bankService = retrofit.create(BankService.class);
    }

    public BankService getBankService() {
        return bankService;
    }
}
