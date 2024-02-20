package com.example.e_wallet_fptu.Activity.Base;

import android.content.Intent;
import android.os.Bundle;

import com.example.e_wallet_fptu.Activity.Security.SetUpPINCodeActivity;
import com.example.e_wallet_fptu.databinding.ActivityPinintroBinding;

public class PINIntroActivity extends BaseActivity {
    ActivityPinintroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinintroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //handle button
        binding.btnPINIntroBack.setOnClickListener(v -> finish());

        binding.btnPINIntroContinue.setOnClickListener(v ->
                startActivity(new Intent(PINIntroActivity.this, SetUpPINCodeActivity.class)));
    }

}