package com.example.e_wallet_fptu.Activity.Authentication;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.databinding.ActivitySetupPasswordBinding;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SetupPasswordActivity extends BaseActivity {
    ActivitySetupPasswordBinding binding;
    private boolean isCondition1Valid = false;
    private boolean isCondition2Valid = false;
    private boolean isPasswordChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnChangePassword.setEnabled(false);

        setVariable();
    }

    private void setVariable() {
        SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
        String currentPassword = preferences.getString("student_password", "");
        binding.edtOldPassword.setText(currentPassword);

        binding.edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newPassword = editable.toString().trim();
                if (newPassword.isEmpty()) {
                    isCondition1Valid = false;
                    isCondition2Valid = false;
                    binding.tvCondition1.setTextColor(Color.RED);
                    binding.tvCondition2.setTextColor(Color.RED);
                } else {
                    if (newPassword.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{8}$")) {
                        isCondition1Valid = true;
                        isCondition2Valid = true;
                        binding.tvCondition1.setTextColor(Color.GREEN);
                        binding.tvCondition2.setTextColor(Color.GREEN);
                    } else if (newPassword.matches("^(?=.*\\d).+$")) {
                        isCondition1Valid = false;
                        isCondition2Valid = true;
                        binding.tvCondition1.setTextColor(Color.RED);
                        binding.tvCondition2.setTextColor(Color.GREEN);
                    } else {
                        isCondition1Valid = false;
                        isCondition2Valid = false;
                        binding.tvCondition1.setTextColor(Color.RED);
                        binding.tvCondition2.setTextColor(Color.RED);
                    }
                }
                updateButtonState();
            }
        });

        binding.btnChangePassword.setOnClickListener(v -> {
            if (isCondition1Valid && isCondition2Valid && !isPasswordChanged) {
                DatabaseReference reference = database.getReference("Student");
                Query query = reference.orderByChild("student_roll_number")
                        .equalTo(preferences
                                .getString("student_roll_number", ""));

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            DataSnapshot studentSnapshot = snapshot.getChildren().iterator().next();
                            studentSnapshot.getRef().child("student_password").setValue(dataEncode.hashData(binding.edtNewPassword.getText().toString().trim()));
                            studentSnapshot.getRef().child("status").setValue(true);

                            Toast.makeText(SetupPasswordActivity.this, "Đổi mật khẩu thành công, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();

                            SharedPreferences preferences1 = getSharedPreferences("currentStudent", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences1.edit();
                            editor.clear(); // Xóa tất cả các giá trị
                            editor.apply();

                            isPasswordChanged = true;
                            updateButtonState();
                            Intent intent = new Intent(SetupPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void updateButtonState() {
        binding.btnChangePassword.setEnabled(isCondition1Valid && isCondition2Valid && !isPasswordChanged);
    }
}