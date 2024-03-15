package com.example.e_wallet_fptu.Activity.StudentInformation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Authentication.LoginActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Activity.Report.ReportActivity;
import com.example.e_wallet_fptu.databinding.ActivityUserBinding;

public class UserActivity extends BaseActivity {
    ActivityUserBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleButton();
        SharedPreferences student = getSharedPreferences("currentStudent", MODE_PRIVATE);
        binding.tvFullName.setText(student.getString("student_name",""));
    }

    private void handleButton() {
        //Handle "Hồ sơ"
        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, ProfileActivity.class));
            finish();
        });

        //Handle "Cài đặt"
        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, SettingActivity.class));
            finish();
        });

        //Handle "Đăng xuất"
        binding.btnLogout.setOnClickListener(v -> {
            //Xóa thông tin người dùng đang đăng nhập trong preferences
            SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            //Chuyển đến màn hình đăng nhập
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        binding.btnUserHome.setOnClickListener(v -> startActivity(new Intent(UserActivity.this, MainActivity.class)));
        binding.btnUserReport.setOnClickListener(v -> startActivity(new Intent(UserActivity.this, ReportActivity.class)));
    }
}