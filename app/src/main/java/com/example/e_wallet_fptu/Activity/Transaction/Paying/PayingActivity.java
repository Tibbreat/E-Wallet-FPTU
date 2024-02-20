package com.example.e_wallet_fptu.Activity.Transaction.Paying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.databinding.ActivityPayingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PayingActivity extends BaseActivity {
    ActivityPayingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleButton();
        getStudentFee();
    }

    private void handleButton() {
        binding.btnPayingBack.setOnClickListener(v -> startActivity(new Intent(PayingActivity.this, MainActivity.class)));
//        binding.btnPayingTuition.setOnClickListener(v -> startActivity(new Intent(PayingActivity.this, MainActivity.class)));
//        binding.btnPayingDomFee.setOnClickListener(v -> startActivity(new Intent(PayingActivity.this, MainActivity.class)));
//        binding.btnPayingMore.setOnClickListener(v -> startActivity(new Intent(PayingActivity.this, MainActivity.class)));
    }

    private void getStudentFee() {
        SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
        String student_roll_number = preferences.getString("student_roll_number", "");
        DatabaseReference reference = database.getReference("Fee").child(student_roll_number);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    Long additionalDormitoryFee = snapshot.child("additional_dormitory_fee").getValue(Long.class);
                    Long dormitoryFee = snapshot.child("dormitory_fee").getValue(Long.class);
                    Long libraryFines = snapshot.child("library_fines").getValue(Long.class);
                    Long reStudyFee = snapshot.child("re_study_fee").getValue(Long.class);
                    Long scholarshipPenaltyFee = snapshot.child("scholarship_penalty_fee").getValue(Long.class);
                    Long semesterFee = snapshot.child("semester_fee").getValue(Long.class);

                    // Lưu các giá trị vào SharedPreferences
                    editor.putInt("additional_dormitory_fee", additionalDormitoryFee != null ? additionalDormitoryFee.intValue() : 0);
                    editor.putInt("dormitory_fee", dormitoryFee != null ? dormitoryFee.intValue() : 0);
                    editor.putInt("library_fines", libraryFines != null ? libraryFines.intValue() : 0);
                    editor.putInt("re_study_fee", reStudyFee != null ? reStudyFee.intValue() : 0);
                    editor.putInt("scholarship_penalty_fee", scholarshipPenaltyFee != null ? scholarshipPenaltyFee.intValue() : 0);
                    editor.putInt("semester_fee", semesterFee != null ? semesterFee.intValue() : 0);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}