package com.example.e_wallet_fptu.Activity.Transaction.Paying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
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
        getStudentFee();
        handleButton();
    }

    private void handleButton() {
       binding.btnPayingBack.setOnClickListener(v -> finish());
        binding.btnPayingTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
                int tuition = preferences.getInt("semester_fee", 0);
                if (tuition != 0){
                    Intent intent = new Intent(PayingActivity.this, SemesterFeeActivity.class);
                    intent.putExtra("fee_type", "semester_fee");
                    startActivity(intent);
                }else{
                    Toast.makeText(PayingActivity.this, "Bạn không nợ học phí kỳ này.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnPayingDomFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
                Long tuition = preferences.getLong("additional_dormitory_f`ee", 0);
                if (tuition != 0) {
                    Intent intent = new Intent(PayingActivity.this, SemesterFeeActivity.class);
                    intent.putExtra("fee_type", "additional_dormitory_fee");
                    startActivity(intent);
                } else {
                    Toast.makeText(PayingActivity.this, "Bạn không nợ học phí kỳ này.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    //Get fee value
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