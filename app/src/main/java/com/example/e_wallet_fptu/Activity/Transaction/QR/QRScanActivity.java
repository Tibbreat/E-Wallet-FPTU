package com.example.e_wallet_fptu.Activity.Transaction.QR;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_wallet_fptu.databinding.ActivityQrscanBinding;

public class QRScanActivity extends AppCompatActivity {
    private ActivityQrscanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrscanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
