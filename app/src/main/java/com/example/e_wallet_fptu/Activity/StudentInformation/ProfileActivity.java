package com.example.e_wallet_fptu.Activity.StudentInformation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;

import com.example.e_wallet_fptu.R;
import com.example.e_wallet_fptu.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
        String currentStudentRollNumber = currentStudent.getString("student_roll_number", "");
        DatabaseReference reference = database.getReference("Student");
        Query query = reference.orderByChild("student_roll_number")
                .equalTo(currentStudentRollNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy dữ liệu từ snapshot
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String studentName = dataSnapshot.child("student_name").getValue(String.class);
                        String studentPhone = dataSnapshot.child("student_phone").getValue(String.class);
                        String studentEmail = dataSnapshot.child("student_email").getValue(String.class);

                        // Hiển thị thông tin lên giao diện
                        TextView tvProfileActivityStudentName = findViewById(R.id.tv_profile_activity_student_name);
                        TextView tvProfileActivityStudentPhone = findViewById(R.id.tv_profile_activity_student_phone);
                        TextView tvProfileActivityStudentEmail = findViewById(R.id.tv_profile_activity_student_email);

                        tvProfileActivityStudentName.setText(studentName);
                        tvProfileActivityStudentPhone.setText(studentPhone);
                        tvProfileActivityStudentEmail.setText(studentEmail);
                    }
                } else {
                    // Xử lý trường hợp không tìm thấy sinh viên
                    Toast.makeText(getApplicationContext(), "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}