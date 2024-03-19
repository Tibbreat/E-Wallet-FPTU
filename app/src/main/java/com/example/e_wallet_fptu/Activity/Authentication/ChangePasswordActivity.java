package com.example.e_wallet_fptu.Activity.Authentication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.databinding.ActivityChangePasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends BaseActivity {
    private ActivityChangePasswordBinding binding;
    private boolean isCondition1Valid = false;
    private boolean isCondition2Valid = false;
    private boolean isPasswordChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnChangePass.setEnabled(false);
        setVariable();

    }

    private void setVariable() {
        binding.edtNewPasswordChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newPassword = s.toString().trim();
                if (newPassword.isEmpty()) {
                    isCondition1Valid = false;
                    isCondition2Valid = false;
                    binding.tvChangePassCondition1.setTextColor(Color.RED);
                    binding.tvChangePassCondition2.setTextColor(Color.RED);
                } else {
                    if (newPassword.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{8}$")) {
                        isCondition1Valid = true;
                        isCondition2Valid = true;
                        binding.tvChangePassCondition1.setTextColor(Color.GREEN);
                        binding.tvChangePassCondition2.setTextColor(Color.GREEN);
                    } else if (newPassword.matches("^(?=.*\\d).+$")) {
                        isCondition1Valid = false;
                        isCondition2Valid = true;
                        binding.tvChangePassCondition1.setTextColor(Color.RED);
                        binding.tvChangePassCondition2.setTextColor(Color.GREEN);
                    } else {
                        isCondition1Valid = false;
                        isCondition2Valid = false;
                        binding.tvChangePassCondition1.setTextColor(Color.RED);
                        binding.tvChangePassCondition2.setTextColor(Color.RED);
                    }
                }
                updateButtonState();
            }
        });

        binding.edtReNewPasswordChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newPassword = s.toString().trim();
                String originalPassword = binding.edtNewPasswordChange.getText().toString().trim();
                if (newPassword.equals(originalPassword)) {
                    isPasswordChanged = true;
                    binding.tvNotMatch.setVisibility(View.GONE);
                } else {
                    isPasswordChanged = false;
                    binding.tvNotMatch.setVisibility(View.VISIBLE);
                }
                updateButtonState();
            }
        });
        binding.btnChangePass.setOnClickListener(v -> {
            String email = getIntent().getStringExtra("email");
            if (isCondition1Valid && isCondition2Valid && isPasswordChanged) {
                DatabaseReference reference = database.getReference("Student");
                Query query = reference.orderByChild("student_email").equalTo(email);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Log.d("ChangePasswordActivity", "User exists in the database");

                            DataSnapshot studentDataSnapshot = snapshot.getChildren().iterator().next();
                            String newPassword = binding.edtNewPasswordChange.getText().toString().trim();
                            String hashedPassword = dataEncode.hashData(newPassword);

                            studentDataSnapshot.getRef().child("student_password").setValue(hashedPassword);
                            Toast.makeText(ChangePasswordActivity.this,
                                    "Đổi mật khẩu thành công, vui lòng đăng nhập lại",
                                    Toast.LENGTH_SHORT).show();
                            isPasswordChanged = true;
                            updateButtonState();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("ChangePasswordActivity", "Database query cancelled: " + error.getMessage());
                        Toast.makeText(ChangePasswordActivity.this,
                                "Có lỗi xảy ra khi truy vấn cơ sở dữ liệu",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Log.d("ChangePasswordActivity", "Conditions are not met to change password");
                Toast.makeText(ChangePasswordActivity.this,
                        "Vui lòng kiểm tra lại mật khẩu mới",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateButtonState() {
        binding.btnChangePass.setEnabled(isCondition1Valid
                && isCondition2Valid && isPasswordChanged);
    }
}
