package com.example.e_wallet_fptu.Activity.Transaction.Paying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
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
        binding.btnBackToFee.setOnClickListener(v -> finish());
    }

    private void setVariable() {
        SharedPreferences pre = getSharedPreferences("currentStudent", MODE_PRIVATE);
        String student_roll_number = pre.getString("student_roll_number", "");

        String type = getIntent().getStringExtra("fee_type");
        if (type != null) {
            switch (type) {
                case "semester_fee":
                    binding.tvFeeTitle.setText("Thanh toán học phí");
                    binding.tvFeeAmount.setText(dataEncode.formatMoney(pre.getInt("semester_fee", 0)));
                    binding.tvFee.setText("Học phí");
                    binding.tvContentFee.setText(student_roll_number + " thanh toán học phí.");
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
                                            //Calculate total transaction amount today
                                            DatabaseReference transactionRef = database.getReference("Transaction").child(student_roll_number);
                                            Query query = transactionRef.orderByChild("time").startAt(dataEncode.getTodayDateString());
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    int totalTransactionAmountToday = 0;
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                        String from = dataSnapshot.child("from").getValue(String.class);
                                                        String to = dataSnapshot.child("to").getValue(String.class);
                                                        int transactionAmount = dataSnapshot.child("amount").getValue(Integer.class);

                                                        //Check if the transaction is related to the current student
                                                        if (from.equals(student_roll_number) || to.equals(student_roll_number)) {
                                                            totalTransactionAmountToday += transactionAmount;
                                                        }
                                                    }
                                                    //Check transaction limit
                                                    if (totalTransactionAmountToday + pre.getInt("semester_fee", 0) <= 100000000) {
                                                        Intent intent = new Intent(SemesterFeeActivity.this, PINActivity.class);
                                                        intent.putExtra("transaction_amount", pre.getInt("semester_fee", 0));
                                                        intent.putExtra("transaction_type", 3); // type 2: Thanh toán học phí
                                                        startActivity(intent);
                                                    }else{
                                                        Toast.makeText(SemesterFeeActivity.this, "Giao dịch vượt quá hạn mức hôm nay", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    });
                                } else {
                                    Toast.makeText(SemesterFeeActivity.this, "Không có dữ liệu về số tiền của sinh viên", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SemesterFeeActivity.this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
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

}