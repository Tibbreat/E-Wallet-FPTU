package com.example.e_wallet_fptu.Activity.Payment;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_wallet_fptu.API.BankApiClient;
import com.example.e_wallet_fptu.API.BankService;
import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Adapter.BankAdapter;
import com.example.e_wallet_fptu.Entity.Bank;
import com.example.e_wallet_fptu.databinding.ActivityListPaymentMethodBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPaymentMethodActivity extends BaseActivity {
    ActivityListPaymentMethodBinding binding;
    private ArrayList<Bank> listBanks = new ArrayList<>();
    private BankAdapter bankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bankAdapter = new BankAdapter(listBanks);
        binding.rvBank.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBank.setAdapter(bankAdapter);

        // Lấy danh sách ngân hàng từ API
        fetchBankList();
    }

    private void fetchBankList() {
        BankApiClient bankApiClient = new BankApiClient();
        BankService bankService = bankApiClient.getBankService();

        // Gọi API để lấy danh sách ngân hàng
        Call<List<Bank>> call = bankService.getBanks();
        call.enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                if (!response.isSuccessful()) {
                    Log.e("API Error", "Error code: " + response.code());
                    Toast.makeText(ListPaymentMethodActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Bank> banks = response.body();
                if (banks != null) {
                    listBanks.addAll(banks);
                    bankAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListPaymentMethodActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch bank list: " + t.getMessage());
                Toast.makeText(ListPaymentMethodActivity.this, "Đã xảy ra lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
