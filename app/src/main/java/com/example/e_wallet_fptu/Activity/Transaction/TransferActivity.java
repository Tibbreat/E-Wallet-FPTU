package com.example.e_wallet_fptu.Activity.Transaction;


import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Activity.Security.PINActivity;
import com.example.e_wallet_fptu.databinding.ActivityTransferBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TransferActivity extends BaseActivity {
    ActivityTransferBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvTransferErrAmount.setVisibility(View.GONE);
        binding.tvTransferErrMssv.setVisibility(View.GONE);
        binding.btnTransferContinue.setEnabled(false);
        getBalance();
        handleError();
        handleButton();
    }

    private void handleButton() {
        binding.btnTransferBack.setOnClickListener(v -> {
            startActivity(new Intent(TransferActivity.this, MainActivity.class));
            finish();
        });

        binding.btnTransferContinue.setOnClickListener(v -> {
            String transferTo = binding.edtTransferTo.getText().toString().trim();
            String transferAmountStr = binding.edtTransferAmount.getText().toString().trim();
            if (!TextUtils.isEmpty(transferAmountStr)) {
                long transferAmount = Long.parseLong(transferAmountStr);
                long currentBalance = Long.parseLong(binding.tvTransferCurrentBalance.getText().toString().replaceAll("[^0-9]", ""));
                if (transferAmount <= currentBalance) {
                    progressDialog = new ProgressDialog(TransferActivity.this);
                    progressDialog.setMessage("Đang kiểm tra MSSV...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    checkRollNumberExist(transferTo);
                } else {
                    Toast.makeText(TransferActivity.this, "Số tiền chuyển đi không thể vượt quá số dư hiện tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleError() {
        binding.edtTransferTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                enableButton();
                String rollNumber = s.toString().trim();
                binding.tvTransferErrMssv.setVisibility(!isValidRollNumber(rollNumber) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        binding.edtTransferAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                enableButton();
                if (!TextUtils.isEmpty(s.toString())) {
                    long transferAmount = Long.parseLong(s.toString());
                    long currentBalance = Long.parseLong(binding.tvTransferCurrentBalance.getText().toString().replaceAll("[^0-9]", ""));
                    binding.tvTransferErrAmount.setVisibility(transferAmount > currentBalance ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private boolean isValidRollNumber(String rollNumber) {
        // Kiểm tra xem MSSV có đúng định dạng không
        return rollNumber.matches("^[A-Za-z]{2}\\d{6}$") && !rollNumber.equals(getSharedPreferences("currentStudent", MODE_PRIVATE).getString("student_roll_number", ""));
    }

    private void getBalance() {
        DatabaseReference reference = database.getReference("Student");
        Query query = reference.orderByChild("student_roll_number").equalTo(getSharedPreferences("currentStudent", MODE_PRIVATE).getString("student_roll_number", ""));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot studentDataSnapshot = snapshot.getChildren().iterator().next();
                    long currentAmount = studentDataSnapshot.child("student_amount").getValue(Long.class);

                    binding.tvTransferCurrentBalance.setText(dataEncode.formatMoney(currentAmount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TransferActivity.this, "Đã xảy ra lỗi khi lấy số dư", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkRollNumberExist(String rollNumber) {
        DatabaseReference reference = database.getReference("Student");
        Query query = reference.orderByChild("student_roll_number").equalTo(rollNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if (snapshot.exists()) {
                    int transferAmount = Integer.parseInt(binding.edtTransferAmount.getText().toString().trim());
                    Intent intent = new Intent(TransferActivity.this, PINActivity.class);
                    intent.putExtra("transaction_amount", transferAmount);
                    intent.putExtra("transfer_to", rollNumber);
                    intent.putExtra("transaction_type", 2); // Loại giao dịch 2: Chuyển tiền đến ví khác
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TransferActivity.this, "Mã số sinh viên không tồn tại. Xin vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(TransferActivity.this, "Đã xảy ra lỗi khi kiểm tra MSSV", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableButton() {
        String rollNumber = binding.edtTransferTo.getText().toString().trim();
        String amount = binding.edtTransferAmount.getText().toString().trim();
        binding.btnTransferContinue.setEnabled(!TextUtils.isEmpty(rollNumber) && !TextUtils.isEmpty(amount) && isValidRollNumber(rollNumber));
    }
}
