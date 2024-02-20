package com.example.e_wallet_fptu.Activity.Transaction.TransactionStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.databinding.ActivityTransactionResultBinding;

public class TransactionResultActivity extends AppCompatActivity {
    ActivityTransactionResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTransactionResultId.setText(getIntent().getStringExtra("Transaction key"));
        binding.tvTransactionResultAmount.setText(String.valueOf(getIntent().getIntExtra("Transaction amount", 0)));
        binding.tvTransactionResultTime.setText(getIntent().getStringExtra("Transaction time"));
        handleButton();

    }

    private void handleButton() {

        binding.btnCfTransactionResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionResultActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}