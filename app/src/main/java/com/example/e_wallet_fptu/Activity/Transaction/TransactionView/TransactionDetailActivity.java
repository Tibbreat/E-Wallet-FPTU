package com.example.e_wallet_fptu.Activity.Transaction.TransactionView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Entity.Transaction;
import com.example.e_wallet_fptu.Helper.DataEncode;
import com.example.e_wallet_fptu.databinding.ActivityTransactionDetailBinding;

public class TransactionDetailActivity extends BaseActivity {
    ActivityTransactionDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
        handleButton();
    }

    private void setVariable() {
        Transaction object = (Transaction) getIntent().getSerializableExtra("object");
        int amount = object.getAmount();
        binding.tvTransactionDetailAmount.setText(dataEncode.formatMoney(amount));
        binding.tvTransactionDetailAmount2.setText(dataEncode.formatMoney(amount));
        binding.tvTransactionDetailFrom.setText(object.getTo());
        binding.tvTransactionDetailTime.setText(object.getTime());
        binding.tvTransactionDetailKey.setText(object.getTransactionKey());
    }

    private void handleButton() {
        binding.btnTransactionDetailBack.setOnClickListener(v -> finish());
        binding.btnTransactionDetailCf.setOnClickListener(v -> finish());
        binding.btnTransactionDetailCopy.setOnClickListener(v -> {
            String textToCopy = binding.tvTransactionDetailKey.getText().toString();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", textToCopy);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(TransactionDetailActivity.this, "Đã sao chép", Toast.LENGTH_SHORT).show();
        });
    }
}