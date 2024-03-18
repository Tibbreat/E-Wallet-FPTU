package com.example.e_wallet_fptu.Activity.Transaction.Paying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
import com.example.e_wallet_fptu.Activity.Transaction.TopUpActivity;
import com.example.e_wallet_fptu.databinding.ActivitySemesterFeeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
                DatabaseReference reference = database.getReference("Student");
                Query query = reference.orderByChild("student_roll_number")
                        .equalTo(pre.getString("student_roll_number", ""));
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            DataSnapshot studentDataSnapshot = snapshot.getChildren().iterator().next();
                            if (studentDataSnapshot.hasChild("student_amount")) {
                                long currentAmount = studentDataSnapshot.child("student_amount").getValue(Long.class);
                                binding.btnPayFee.setOnClickListener(v -> {
                                    if (currentAmount < pre.getInt("semester_fee", 0)) {
                                        Toast.makeText(SemesterFeeActivity.this, "Số tiền không đủ để thực hiện giao dịch", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(SemesterFeeActivity.this, PINActivity.class);
                                        intent.putExtra("transaction_amount", pre.getInt("semester_fee", 0));
                                        intent.putExtra("transaction_type", 2); // type 2: Thanh toán học phí
                                        startActivity(intent);
                                    }
                                });

                            } else {
                                // Handle case where student_amount doesn't exist
                                Toast.makeText(SemesterFeeActivity.this, "Không có dữ liệu về số tiền của sinh viên", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle case where snapshot doesn't exist
                            Toast.makeText(SemesterFeeActivity.this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled event if needed
                    }
                });
                break;
            case "additional_dormitory_fee":
                binding.tvFeeTitle.setText("Đăng ký ký túc xá");
                binding.tvFeeAmount.setText(dataEncode.formatMoney(pre.getInt("additional_dormitory_fee", 0)));
                binding.tvFee.setText("Phí ký túc xá");
                binding.tvContentFee.setText(pre.getString("student_roll_number", "") + " thanh toán phí ký túc xá.");
                break;
        }
    }

}