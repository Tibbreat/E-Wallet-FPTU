package com.example.e_wallet_fptu.Activity.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Security.EnterCodeActivity;
import com.example.e_wallet_fptu.Entity.Student;
import com.example.e_wallet_fptu.Helper.DataEncode;
import com.example.e_wallet_fptu.Helper.SendOTP;
import com.example.e_wallet_fptu.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends BaseActivity {
    private ActivityForgotPasswordBinding binding;
    private boolean isEmailValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSendMail.setEnabled(false);
        setVariable();
    }

    private void setVariable() {
        Button btnSend = binding.btnSendMail;

        // Default to disable the button
        btnSend.setEnabled(false);

        // Handle "Gửi mã xác minh" button
        binding.btnSendMail.setOnClickListener(v -> sendVerificationCode());

        binding.edtEmailForgot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed here
            }

            @Override
            public void afterTextChanged(Editable s) {
                isEmailValid = validateEmail(s.toString().trim());
                updateButtonState();
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void sendVerificationCode() {
        String email = binding.edtEmailForgot.getText().toString().trim();
        binding.btnSendMail.setEnabled(false);

        // Show progress dialog
        ProgressDialog progressDialog = ProgressDialog.show(this, "", "Sending verification code...", true);

        DatabaseReference reference = database.getReference("Student");
        Query query = reference.orderByChild("student_email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss(); // Dismiss the progress dialog

                if (snapshot.exists()) {
                    DataSnapshot studentSnapshot = snapshot.getChildren().iterator().next();
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student != null) {
                        String verificationCode = new DataEncode().generateRandomCode();

                        new SendMailTask().execute(email, "Xác minh là sinh viên đang đổi mật khẩu ví",
                                        "Mã xác minh quên mật khẩu FPTU Wallet của bạn là: " + verificationCode);

                        Intent intent_1 = new Intent(ForgotPasswordActivity.this, EnterCodeActivity.class);
                        intent_1.putExtra("email", email);
                        intent_1.putExtra("code", verificationCode);
                        intent_1.putExtra("type", "forgot password");
                        startActivity(intent_1);

                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss(); // Dismiss the progress dialog

                // Handle onCancelled if needed
                Toast.makeText(ForgotPasswordActivity.this, "Database query cancelled or failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateEmail(String email) {
        return !email.isEmpty() && email.matches("^[a-zA-Z]+[0-9]{6}@fpt\\.edu\\.vn$");
    }

    private class SendMailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String receiveEmail = params[0];
            String emailSubject = params[1];
            String text = params[2];

            SendOTP sendMail = new SendOTP();
            sendMail.sendMail(receiveEmail, emailSubject, text);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Handle onPostExecute if needed
        }
    }

    private void updateButtonState() {
        binding.btnSendMail.setEnabled(isEmailValid);
    }
}
