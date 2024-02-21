package com.example.e_wallet_fptu.Activity.Payment;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.e_wallet_fptu.API.BankAPIClient;
import com.example.e_wallet_fptu.API.BankResponse;
import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Adapter.BankAdapter;
import com.example.e_wallet_fptu.Entity.Banks;
import com.example.e_wallet_fptu.databinding.ActivityListPaymentMethodBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPaymentMethodActivity extends BaseActivity {
    ActivityListPaymentMethodBinding binding;
    private ArrayList<Banks> banksList;
    private BankAdapter bankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        banksList = new ArrayList<>();
        bankAdapter = new BankAdapter(this, banksList);

        binding.rvBank.setLayoutManager(new GridLayoutManager(this, 4));

        binding.rvBank.setAdapter(bankAdapter);

        // Lấy danh sách ngân hàng từ API
        fetchBankList();
    }

    private void fetchBankList() {
        BankAPIClient bankAPIClient = new BankAPIClient();
        bankAPIClient.getBankService().getBanks().enqueue(new Callback<BankResponse>() {
            @Override
            public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BankResponse bankResponse = response.body();
                    List<Banks> bankList = bankResponse.getBankList();
                    banksList.clear();
                    banksList.addAll(bankList);
                    bankAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ListPaymentMethodActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BankResponse> call, @NonNull Throwable t) {
                Log.d("APICHECKLOG", Objects.requireNonNull(t.getMessage()));
                Toast.makeText(ListPaymentMethodActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
