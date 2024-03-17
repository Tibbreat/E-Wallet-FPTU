package com.example.e_wallet_fptu.Activity.Transaction.Paying;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.databinding.ActivitySemesterFeeBinding;

public class SemesterFeeActivity extends BaseActivity {
    private ActivitySemesterFeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySemesterFeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
        handleButton();
    }

    private void handleButton() {
        binding.btnBackToFee.setOnClickListener(v -> finish());
        binding.btnPayFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setVariable() {
        SharedPreferences pre = getSharedPreferences("currentStudent", MODE_PRIVATE);
        String type = getIntent().getStringExtra("fee_type");
        switch (type) {
            case "semester_fee":
                binding.tvFeeTitle.setText("Thanh toán học phí");
                binding.tvFeeAmount.setText(dataEncode.formatMoney(pre.getInt("semester_fee", 0)));
                binding.tvFee.setText("Học phí");
                binding.tvContentFee.setText(pre.getString("student_roll_number", "") + " thanh toán học phí.");
                break;
            case "additional_dormitory_fee":
                binding.tvFeeTitle.setText("Đăng ký ký túc xá");
                binding.tvFeeAmount.setText(dataEncode.formatMoney(pre.getInt("semester_fee", 0)));
                binding.tvFee.setText("Phí ký túc xá");
                binding.tvContentFee.setText(pre.getString("student_roll_number", "") + " thanh toán phí ký túc xá.");
                break;
        }
    }
}