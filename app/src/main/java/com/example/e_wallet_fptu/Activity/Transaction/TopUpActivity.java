package com.example.e_wallet_fptu.Activity.Transaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Activity.Base.PINIntroActivity;
import com.example.e_wallet_fptu.Activity.Payment.ListPaymentMethodActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
import com.example.e_wallet_fptu.Activity.Security.SetUpPINCodeActivity;
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
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                binding.btnPay.setEnabled(!charSequence.toString().trim().isEmpty());
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
                Intent intent = new Intent(TopUpActivity.this, PINActivity.class);
                intent.putExtra("transaction_amount", amount_top_up);
                intent.putExtra("transaction_type", 1); // type 1: Nạp tiền vào ví
                startActivity(intent);
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
