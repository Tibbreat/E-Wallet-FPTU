package com.example.e_wallet_fptu.Activity.Transaction.Paying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
import com.example.e_wallet_fptu.Activity.Transaction.TopUpActivity;
import com.example.e_wallet_fptu.databinding.ActivitySemesterFeeBinding;

public class SemesterFeeActivity extends BaseActivity {
    private ActivitySemesterFeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySemesterFeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
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
                binding.btnPayFee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SemesterFeeActivity.this, PINActivity.class);
                        intent.putExtra("transaction_amount", pre.getInt("semester_fee", 0));
                        intent.putExtra("transaction_type", 3); // type 2: Thanh toán học phí
                        startActivity(intent);
                    }
                });
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