package com.example.e_wallet_fptu.Activity.Transaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Activity.Base.PINIntroActivity;
import com.example.e_wallet_fptu.Activity.Payment.ListPaymentMethodActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
import com.example.e_wallet_fptu.R;
import com.example.e_wallet_fptu.databinding.ActivityTopUpBinding;

public class TopUpActivity extends BaseActivity {
    private ActivityTopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        selectAmount();
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(TopUpActivity.this, MainActivity.class));
            finish();
        });

        binding.btnPay.setEnabled(false);
        binding.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                binding.textView100.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String amountString = charSequence.toString().trim();
                if (!amountString.isEmpty()) {
                    int amount = Integer.parseInt(amountString);
                    if (amount >= 50000000) {
                        binding.textView100.setVisibility(View.VISIBLE);
                        binding.textView100.setText("Số tiền giao dịch không vượt quá 50000000");
                    } else {
                        binding.textView100.setVisibility(View.GONE);
                    }
                }else {
                    binding.textView100.setVisibility(View.GONE);
                }
                binding.btnPay.setEnabled(!amountString.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        binding.btnPay.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
            String studentPIN = preferences.getString("student_PIN", "");
            if (studentPIN.isEmpty()) {
                startActivity(new Intent(TopUpActivity.this, PINIntroActivity.class));
            } else {
                int amount_top_up = Integer.parseInt(binding.edtAmount.getText().toString().trim());
                if (amount_top_up <= 50000000) { // Sửa thành <= để bao gồm trường hợp số tiền top-up là 50 triệu VNĐ
                    Intent intent = new Intent(TopUpActivity.this, PINActivity.class);
                    intent.putExtra("transaction_amount", amount_top_up);
                    intent.putExtra("transaction_type", 1); // type 1: Nạp tiền vào ví
                    startActivity(intent);
                } else {
                    Toast.makeText(TopUpActivity.this, "Số tiền top-up không được vượt quá 50.000.000VNĐ trên 1 giao dịch", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnPaymentMethod.setOnClickListener(v -> {
            startActivity(new Intent(TopUpActivity.this, ListPaymentMethodActivity.class));
        });

    }

    private void selectAmount() {
        final TextView[] amountTextViews = {binding.tv10K, binding.tv20K, binding.tv50K,
                binding.tv100K, binding.tv200K, binding.tv500K};

        for (final TextView amountTextView : amountTextViews) {
            amountTextView.setOnClickListener(v -> {
                for (TextView tv : amountTextViews) {
                    tv.setBackgroundResource(R.drawable.edittext_background);
                }
                String amountValue = amountTextView.getText().toString().replace("_", "").replace(".", "");
                binding.edtAmount.setText(amountValue);
                binding.tvAmountTopUp.setText(dataEncode.formatMoney(Integer.parseInt(amountValue)));
                amountTextView.setBackgroundResource(R.drawable.edittext_background_red);
            });
        }
    }
}
