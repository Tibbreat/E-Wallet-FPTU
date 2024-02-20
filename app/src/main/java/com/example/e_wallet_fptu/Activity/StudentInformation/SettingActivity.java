package com.example.e_wallet_fptu.Activity.StudentInformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_wallet_fptu.Activity.Security.SetUpPINCodeActivity;
import com.example.e_wallet_fptu.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        handleButton();
    }

    private void handleButton() {
        binding.btnToChangePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Handle "Thiết lập mã PIN"
        binding.btnToSetupPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SetUpPINCodeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}