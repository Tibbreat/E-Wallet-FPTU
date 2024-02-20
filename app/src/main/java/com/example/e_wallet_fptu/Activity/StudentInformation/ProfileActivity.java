package com.example.e_wallet_fptu.Activity.StudentInformation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;

import com.example.e_wallet_fptu.databinding.ActivityProfileBinding;

public class ProfileActivity extends BaseActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayData();
        handleOnClick();
    }

    private void handleOnClick() {
        binding.btnProfileActivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnProfileActivityChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayData() {
        SharedPreferences currentStudent = getSharedPreferences("currentStudent", MODE_PRIVATE);
        binding.tvProfileActivityStudentEmail.setText(currentStudent.getString("student_email", ""));
        binding.tvProfileActivityStudentName.setText(currentStudent.getString("student_name", ""));
        binding.tvProfileActivityStudentPhone.setText(currentStudent.getString("student_phone", ""));
    }

}