package com.example.e_wallet_fptu.Activity.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Entity.Student;
import com.example.e_wallet_fptu.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvEnroll.setVisibility(View.GONE);
        binding.btnLogin.setEnabled(false);
        setVariable();
    }

    private void setVariable() {
        binding.edtMssv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String rollNumber = editable.toString().trim();
                boolean check = rollNumber.matches("^[A-Za-z]{2}\\d{6}$");
                binding.tvEnroll.setVisibility(!check ? View.VISIBLE : View.GONE);
                binding.btnLogin.setEnabled(check);
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            // Disable button khi bắt đầu đăng nhập
            attemptLogin();
            binding.btnLogin.setEnabled(false);
//            finish();
        });

        binding.btnForgot.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void attemptLogin() {
        String rollNumber = binding.edtMssv.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(rollNumber)) {
            // Enable lại button khi xử lý xong
            binding.btnLogin.setEnabled(true);
            return;
        }
        checkStudent(rollNumber, password);
    }

    private void getStudentData(Student student) {
        SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("student_roll_number", student.getStudent_roll_number());
        editor.putString("student_name", student.getStudent_name());
        editor.putString("student_email", student.getStudent_email());
        editor.putString("student_phone", student.getStudent_phone());
        editor.putString("student_PIN", student.getStudent_PIN());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void checkStudent(String student_roll_number, String student_password) {
        DatabaseReference reference = database.getReference("Student");
        Query query = reference.orderByChild("student_roll_number").equalTo(student_roll_number);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Enable lại button khi xử lý xong
                binding.btnLogin.setEnabled(true);

                // Thực hiện xử lý đăng nhập
                if (snapshot.exists()) {
                    for (DataSnapshot studentSnapshot : snapshot.getChildren()) {
                        Student student = studentSnapshot.getValue(Student.class);
                        if (student != null) {
                            boolean status = student.getStatus();
                            if (!status) {
                                SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("student_roll_number", student.getStudent_roll_number());
                                editor.putString("student_password", student.getStudent_password());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, SetupPasswordActivity.class);
                                startActivity(intent);
                            } else {
                                if (TextUtils.isEmpty(student_password)) {
                                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                                } else {
                                    String password = student.getStudent_password();
                                    if (dataEncode.verifyHash(student_password, password)) {
                                        getStudentData(student);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Lỗi đọc dữ liệu từ Firebase: " + error.getMessage());

                // Enable lại button khi có lỗi
                binding.btnLogin.setEnabled(true);
            }
        });
    }


}
