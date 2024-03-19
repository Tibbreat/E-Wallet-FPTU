package com.example.e_wallet_fptu.Activity.Transaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Activity.Base.PINIntroActivity;
import com.example.e_wallet_fptu.Activity.Payment.ListPaymentMethodActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
import com.example.e_wallet_fptu.R;
import com.example.e_wallet_fptu.databinding.ActivityTopUpBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TopUpActivity extends BaseActivity {
    private ActivityTopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        selectAmount();
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(TopUpActivity.this, MainActivity.class));
            finish();
        });

        binding.btnPay.setEnabled(false);
        binding.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                binding.textView100.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String amountString = charSequence.toString().trim();
                if (!amountString.isEmpty()) {
                    int amount = Integer.parseInt(amountString);
                    if (amount >= 50000000) {
                        binding.textView100.setVisibility(View.VISIBLE);
                        binding.textView100.setText("Số tiền giao dịch không vượt quá 50000000");
                    } else {
                        binding.textView100.setVisibility(View.GONE);
                    }
                } else {
                    binding.textView100.setVisibility(View.GONE);
                }
                binding.btnPay.setEnabled(!amountString.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.btnPay.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
            String student_roll_number = preferences.getString("student_roll_number", "");
            String studentPIN = preferences.getString("student_PIN", "");

            if (studentPIN.isEmpty()) {
                startActivity(new Intent(TopUpActivity.this, PINIntroActivity.class));
            } else {
                String amountText = binding.edtAmount.getText().toString().trim();

                if (!amountText.isEmpty()) {
                    int amount_top_up = Integer.parseInt(amountText);

                    // Calculate total transaction amount today
                    DatabaseReference reference = database.getReference("Transaction").child(student_roll_number);

                    Query query = reference.orderByChild("time").startAt(dataEncode.getTodayDateString());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int totalTransactionAmountToday = 0;

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String from = dataSnapshot.child("from").getValue(String.class);
                                String to = dataSnapshot.child("to").getValue(String.class);
                                String category = dataSnapshot.child("category").getValue(String.class);
                                int transactionAmount = dataSnapshot.child("amount").getValue(Integer.class);

                                // Check if the transaction is related to the current student
                                if (from.equals(student_roll_number) || to.equals(student_roll_number) && !category.equals("Nhận tiền từ ví khác")) {
                                    totalTransactionAmountToday += transactionAmount;
                                }
                            }
                            // Check transaction limit
                            if (totalTransactionAmountToday + amount_top_up <= 100000000) {
                                // Proceed with the transaction
                                if (amount_top_up <= 50000000) {
                                    Intent intent = new Intent(TopUpActivity.this, PINActivity.class);
                                    intent.putExtra("transaction_amount", amount_top_up);
                                    intent.putExtra("transaction_type", 1); // type 1: Nạp tiền vào ví
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(TopUpActivity.this, "Số tiền top-up không được vượt quá 50.000.000 VNĐ trên 1 giao dịch", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(TopUpActivity.this, "Hạn mức giao dịch ngày hôm nay đã vượt quá 100.000.000 VNĐ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled event if needed
                        }
                    });
                } else {
                    Toast.makeText(TopUpActivity.this, "Vui lòng nhập số tiền top-up", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.btnPaymentMethod.setOnClickListener(v -> startActivity(new Intent(TopUpActivity.this, ListPaymentMethodActivity.class)));
    }


    private void selectAmount() {
        final TextView[] amountTextViews = {binding.tv10K, binding.tv20K, binding.tv50K,
                binding.tv100K, binding.tv200K, binding.tv500K};

        for (final TextView amountTextView : amountTextViews) {
            amountTextView.setOnClickListener(v -> {
                for (TextView tv : amountTextViews) {
                    tv.setBackgroundResource(R.drawable.edittext_background);
                }
                String amountValue = amountTextView.getText().toString().replace("_", "").replace(".", "");
                binding.edtAmount.setText(amountValue);
                binding.tvAmountTopUp.setText(dataEncode.formatMoney(Integer.parseInt(amountValue)));
                amountTextView.setBackgroundResource(R.drawable.edittext_background_red);
            });
        }
    }
}
