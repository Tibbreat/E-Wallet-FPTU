package com.example.e_wallet_fptu.Activity.StudentInformation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;

import com.example.e_wallet_fptu.Entity.Student;
import com.example.e_wallet_fptu.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        String student_roll_number = currentStudent.getString("student_roll_number", "");
        Query query = database.getReference("Student").orderByChild("student_roll_number").equalTo(student_roll_number);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot studentSnapshot : snapshot.getChildren()) {
                        Student student = studentSnapshot.getValue(Student.class);
                        if (student != null) {
                            binding.tvProfileActivityStudentEmail.setText(student.getStudent_email());
                            binding.tvProfileActivityStudentName.setText(student.getStudent_name());
                            binding.tvProfileActivityStudentPhone.setText(student.getStudent_phone());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}